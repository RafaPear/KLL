package pt.rafap.token.error

import pt.rafap.token.Token

class UnexpectedToken(tokens: List<Token>, index: Int) : Throwable() {

    override val message: String = printAnalyseError(tokens, index)

    private fun printAnalyseError(tokens: List<Token>, index: Int): String {
        val actual = tokens[index]
        val next = tokens.getOrNull(index + 1) ?: return "Unexpected end of input after ${actual.value}"

        val actualLine = getLine(tokens, index)
        val nextLine = getLine(tokens, index + 1)

        val linePos = actual.position.line

        fun formatValue(token: Token) = if (token.value == "\n") "[EOL]" else token.value
        val actualValue = formatValue(actual)
        val nextValue = formatValue(next)

        val blue = "\u001B[34m"
        val red = "\u001B[31m"
        val reset = "\u001B[0m"

        val pointerOffset = nextLine.indexOf(next.value).coerceAtLeast(0)
        val pointerLine = " ".repeat(pointerOffset + linePos.toString().length) + "^" + "_".repeat(maxOf(0, next.value.length - 1))

        var errorLine = actualLine.replaceFirst("$actualValue$nextValue", "$red$actualValue$nextValue$reset")
        if (errorLine == actualLine) errorLine = actualLine.replaceFirst(actualValue, "$red$actualValue$reset")
        val errorLineAlt = nextLine.replaceFirst(nextValue, "$red$nextValue$reset")

        return buildString {
            appendLine("Unexpected token ${next.type.name}('$nextValue'), after ${actual.type.name}('$actualValue'), at line ${next.position.line}, col ${next.position.col}.")
            appendLine("$blue$linePos$reset- $errorLine")
            if (nextLine != actualLine) appendLine("$blue${linePos + 1}$reset- $errorLineAlt")
            appendLine("  $pointerLine")
        }
    }


    private fun getLine(tokens: List<Token>, index: Int): String {
        if (tokens.isEmpty()) return ""

        val line = tokens[index].position.line

        return tokens
            .withIndex()
            .filter { it.value.position.line == line }.joinToString("") { it.value.value }
    }
}