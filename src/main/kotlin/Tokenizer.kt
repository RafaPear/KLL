
import std.types.EndOfFileSymbol
import std.types.EndOfLineSymbol
import std.types.StringType
import std.types.WhitespaceSymbol
import token.Token
import token.TokenMatchResult
import token.TokenMatcher
import token.TokenPos
import java.io.File
import kotlin.system.exitProcess

class Tokenizer: LexerComponent {
    private val matchers = mutableListOf<TokenMatcher>()
    private var printDebug = false

    fun isEmpty() = matchers.isEmpty()

    fun addMatchers(vararg matchers: TokenMatcher) {
        this.matchers.addAll(matchers)

        if (printDebug) {
            println("Matchers:")
            for (matcher in matchers)
                println("\t- $matcher")
        }
    }

    override fun shutTheFuckUp() {
        this.printDebug = false
    }

    override fun speakTheFuckUp() {
        this.printDebug = true
    }

    fun printTokensAsFile(tokens: List<Token>, filePath: String) {
        val content = rebuildFileFromTokens(tokens)
        try {
            File(filePath).writeText(content)
            if (printDebug) println("Rebuilt file written to '$filePath'")
        } catch (e: Exception) {
            println("Error writing rebuilt file to '$filePath': ${e.message}")
        }
    }

    fun tokenizeFile(filePath: String, verify: Boolean = false): List<Token> {
        val fileContent = try {
            File(filePath).readText()
        } catch (e: Exception) {
            println("Error reading file '$filePath': ${e.message}")
            return emptyList()
        }
        val tokens = tokenize(fileContent)
        if (verify) {
            val canRebuild = canTokensRebuildFile(fileContent, tokens)
            if (!canRebuild) {
                throw Exception("Tokens cannot rebuild the file content")
            } else {
                println("File '$filePath' tokenized and verified successfully")
            }
        } else {
            if (printDebug) println("File '$filePath' tokenized without verification")
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
        for (t in tokens) {
            when(t.type) {
                EndOfLineSymbol -> sb.appendLine()
                EndOfFileSymbol -> {} // do nothing
                StringType -> sb.append("\"${t.value}\"") // keep string literals as is
                WhitespaceSymbol -> sb.append(t.value) // add space for whitespace tokens
                else -> sb.append(t.value) // add space after other tokens
            }
        }
        return sb.toString()
    }

    fun tokenize(input: String): List<Token> {
        val inputLines = input.split("\n")
        val tokens = mutableListOf<Token>()

        for ((lineIndex, lineContent) in inputLines.withIndex()) {
            val tempTokenizedLine = tokenizeLine(lineContent, lineIndex, inputLines)
            tokens += tempTokenizedLine
            if (lineIndex < inputLines.size - 1) {
                val eol = EndOfLineSymbol("[EOL]", TokenPos(lineIndex, lineContent.length))
                tokens.add(eol)
                if (printDebug) println("Matched $eol")
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
                if (printDebug) println("Matched $result")
                col += result.length
            } else {
                if (col == lineContent.length-1) break
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