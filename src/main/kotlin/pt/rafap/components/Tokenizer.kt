package pt.rafap.components

import pt.rafap.std.types.whitespace.EndOfFileSymbol
import pt.rafap.std.types.whitespace.EndOfLineSymbol
import pt.rafap.std.types.whitespace.WhitespaceSymbol
import pt.rafap.logging.WithLogger
import pt.rafap.std.types.StringType
import pt.rafap.token.Token
import pt.rafap.token.Token.Companion.NULL
import pt.rafap.token.TokenMatchResult
import pt.rafap.token.TokenMatcher
import pt.rafap.token.TokenPos
import java.io.File
import kotlin.system.exitProcess

/**
 * Converts raw text into a flat list of [pt.rafap.token.Token]s by applying a sequence of [TokenMatcher]s.
 *
 * This component is internal to the public [pt.rafap.Lexer] façade.
 */
internal class Tokenizer : WithLogger() {
    private val matchers = mutableListOf<TokenMatcher>()

    /** Returns true when no matchers are configured. */
    fun isEmpty() = matchers.isEmpty()

    /** Adds one or more [TokenMatcher]s to the end of the matching chain. */
    fun addMatchers(vararg matchers: TokenMatcher) {
        this.matchers.addAll(matchers)

        logger.debug("Matchers:")
        for (matcher in matchers)
            logger.debug("\t- {}", matcher)
    }

    fun printTokensAsFile(tokens: List<Token>, filePath: String) {
        val content = rebuildFileFromTokens(tokens)
        try {
            File(filePath).writeText(content)
            logger.info("Rebuilt file written to '$filePath'")
        } catch (e: Exception) {
            logger.error("Error writing rebuilt file to '$filePath': ${e.message}")
        }
    }

    /**
     * Tokenizes the content of a file.
     *
     * @param filePath Path to the file to be tokenized.
     * @param verify If true, checks whether tokens can reconstruct the original (ignoring whitespace differences).
     * @return The list of tokens.
     * @throws Exception When no matchers are configured or the verification fails.
     */
    fun tokenizeFile(filePath: String, verify: Boolean = false): List<Token> {
        if (isEmpty()) {
            logger.error("Were are the matchers, bro? Idk what to do without them.")
            throw Exception("Tokenizer has no matchers. Cannot proceed.")
        }

        val fileContent = try {
            File(filePath).readText()
        } catch (e: Exception) {
            logger.error("Error reading file '$filePath': ${e.message}")
            exitProcess(1)
        }
        val tokens = tokenize(fileContent)
        if (verify) {
            val canRebuild = canTokensRebuildFile(fileContent, tokens)
            if (!canRebuild) {
                throw Exception("Tokens cannot rebuild the file content")
            } else {
                logger.info("File '$filePath' tokenized and verified successfully")
            }
        } else {
            logger.info("File '$filePath' tokenized without verification")
        }
        return tokens
    }

    fun canTokensRebuildFile(fileContent: String, tokens: List<Token>): Boolean {
        val rebuilt = rebuildFileFromTokens(tokens)
        val original = fileContent.replace(Regex("\\s+"), "")
        val rebuiltNoSpaces = rebuilt.replace(Regex("\\s+"), "")
        return original == rebuiltNoSpaces
    }

    private fun rebuildFileFromTokens(tokens: List<Token>): String {
        val sb = StringBuilder()
        // use token line and column info to rebuild the file more accurately
        var lastPos = TokenPos(0, 0)
        var lastToken: Token = NULL
        for (t in tokens) {
            val pos = t.position
            if (lastPos.line < pos.line) {
                // new lines
                repeat(pos.line - lastPos.line) { sb.append("\n") }
                lastPos = TokenPos(pos.line, 0) // reset column on new line
            }
            if (lastPos.col < pos.col) {
                // spaces
                val total = if(lastPos.line == pos.line) pos.col - lastPos.col - (lastToken.value.length)
                else pos.col - 1
                if (total > 0)
                    sb.append(" ".repeat(total))
            }
            when (t.type) {
                EndOfFileSymbol -> {} // do nothing
                EndOfLineSymbol -> {} // do nothing, we already added new lines above
                WhitespaceSymbol -> {} // do nothing, we already added spaces above
                StringType      -> { sb.append("\"${t.value}\"") ; lastPos = pos ; lastToken = t } // keep string literals as is
                else            -> { sb.append(t.value) ; lastPos = pos ; lastToken = t } // add space after other tokens
            }
        }
        return sb.toString()
    }

    /**
     * Tokenizes a raw string into a list of tokens.
     * Lines are split on '\n' and [TokenMatcher]s are applied line-by-line.
     */
    fun tokenize(input: String): List<Token> {
        val inputLines = input.split("\n")
        val tokens = mutableListOf<Token>()

        for ((lineIndex, lineContent) in inputLines.withIndex()) {
            val tempTokenizedLine = tokenizeLine(lineContent, lineIndex, inputLines)
            tokens += tempTokenizedLine
            if (lineIndex < inputLines.size - 1) {
                val eol = EndOfLineSymbol("[EOL]", TokenPos(lineIndex, lineContent.length))
                tokens.add(eol)
                logger.debug("Matched {}", eol)
            }
        }

        tokens.add(EndOfFileSymbol("[EOF]", TokenPos(inputLines.size, 0)))

        return tokens
    }

    private fun tokenizeLine(lineContent: String, lineIndex: Int, lines: List<String>): List<Token> {
        val tokens = mutableListOf<Token>()
        var col = 0

        fun peek(i: Int) = lineContent.getOrNull(i)

        while (col < lineContent.length) {
            val result = matchAt(lineContent, lineIndex, col, ::peek)

            if (result != null) {
                tokens.add(result.token.copy(position = TokenPos(lineIndex, col)))
                logger.debug("Matched {}", result)
                col += result.length
            } else {
                if (col == lineContent.length - 1) break
                throwUnhandledCharError(lines, lineIndex, col)
            }
        }



        return tokens
    }

    private fun matchAt(line: String, index: Int, col: Int, peek: (Int) -> Char?): TokenMatchResult? {
        for (matcher in matchers) {
            val result = matcher.match(line, index, col, peek)
            if (result != null) return result
        }
        return null
    }

    private fun throwUnhandledCharError(lines: List<String>, line: Int, col: Int) {
        val lineContent = lines[line]
        val pointerLine = " ".repeat(col) + "^"
        val ident = "\t"
        val char = if (lineContent[col] in listOf('\n', '\r')) "[EOL]" else lineContent[col]

        var context = "[LEXER] Unhandled char '${char}' at line ${line + 1}, col ${col + 1}:\n"
        if (line > 1) context += "${line - 1}-$ident${lines[line - 2]}\n"
        if (line > 0) context += "${line}-$ident${lines[line - 1]}\n"
        context += "${line + 1}-$ident$lineContent\n"
        context += "$ident$pointerLine\n"

        print(context)
        exitProcess(1)
    }
}