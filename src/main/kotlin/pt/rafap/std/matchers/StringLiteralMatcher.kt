package pt.rafap.std.matchers

import pt.rafap.std.types.StringType
import pt.rafap.token.TokenMatchResult
import pt.rafap.token.TokenMatcher

class StringLiteralMatcher(val delimiters: List<Char>) : TokenMatcher {
    override fun match(input: CharSequence, line: Int, col: Int, peek: (Int) -> Char?): TokenMatchResult? {
        if (peek(col) !in delimiters) return null
        var length = 1
        while (true) {
            val currentChar = peek(col + length) ?: return null
            length++
            if (currentChar in delimiters) break
            if (currentChar in listOf('\n', '\r')) return null // String literals cannot span multiple lines
        }
        val value = input.subSequence(col + 1, col + length - 1).toString()
        return TokenMatchResult(length, StringType(value, col to line))
    }
}