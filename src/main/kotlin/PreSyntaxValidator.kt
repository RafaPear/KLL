
import rules.SyntaxRule
import std.types.*
import token.Token
import token.TokenType

class PreSyntaxValidator: LexerComponent {
    private val rules = mutableListOf<SyntaxRule>()
    private var printDebug = false

    fun isEmpty() = rules.isEmpty()

    fun addRules(vararg rules: SyntaxRule) {
        this.rules.addAll(rules)
    }

    override fun shutTheFuckUp() {
        this.printDebug = false
    }

    override fun speakTheFuckUp() {
        this.printDebug = true
    }

    fun analyse(tokens: List<Token>): Boolean {
        for ((i, token) in tokens.withIndex()) {
            val nextToken =
                if (i + 1 < tokens.size) tokens[i+1]
                else {
                    return if (printDebug && token.type != EndOfFileSymbol) {
                        printAnalyseError(tokens, i)
                        false
                    } else true
                }

            if (!analyseSingle(token, nextToken)) {
                if (printDebug)
                    printAnalyseError(tokens, i)
            }
        }
        return true
    }
    
    private fun analyseSingle(token: Token, next: Token): Boolean {
        // -- Flags --
        var matched = false
        var existsRule = false

        val expects = mutableListOf<TokenType>()

        for (rule in rules) {
            expects += rule.expects
            if (rule.appliesTo != token.type) continue
            existsRule = true

            val result = rule(token, next)

            if (result != null && !result.error) {
                matched = true
                break
            }
        }

        if (!existsRule) matched = true

        return matched
    }

    fun printAnalyseError(tokens: List<Token>, index: Int) {
        val line = getLine(tokens, index)
        val actual = tokens[index]
        val next = tokens.getOrNull(index + 1)!!

        val (linePos, colPos) = actual.position.line to actual.position.col

        val actualValue = if (actual.value == "\n") "[EOL]" else actual.value
        val nextValue = if (next.value == "\n") "[EOL]" else next.value

        val lineBeforeToken = line.substringBefore(next.value)
        val pointerOffset = lineBeforeToken.length

        val pointerLine = " ".repeat(pointerOffset) + "^" + "_".repeat(maxOf(0, next.value.length - 1))
        val indent = "\t"

        val message = buildString {
            appendLine("[PSV] Unexpected token ${next.type.name}('${nextValue}'), after ${actual.type.name}('${actualValue}'), at line ${next.position.line}, col ${next.position.col}.")
            appendLine("${linePos}-$indent$line")
            appendLine("$indent$pointerLine")
        }

        println(message)
    }

    private fun getLine(tokens: List<Token>, index: Int): String {
        if (tokens.isEmpty()) return ""

        var start = index
        while (start > 0 && tokens[start - 1].type != EndOfLineSymbol) {
            start--
        }

        var end = index
        while (end < tokens.size && tokens[end].type != EndOfLineSymbol) {
            end++
        }

        val tokenTypesWithSpace = listOf(
            ReservedType,
            DataType,
            NumberType,
        )

        return tokens.subList(start, end)
            .joinToString("") {
                if (it.type in tokenTypesWithSpace) "${it.value} "
                else it.value
            }
    }


}
