package pt.rafap.std.matchers

import pt.rafap.std.types.IdentifierType
import pt.rafap.token.TokenMatchResult
import pt.rafap.token.TokenMatcher

object IdentifierMatcher : TokenMatcher {

    override fun match(input: CharSequence, line: Int, col: Int, peek: (Int) -> Char?): TokenMatchResult? {
        val first = peek(col) ?: return null
        if (!first.isLetter() && first != '_') return null

        var i = col + 1
        while (peek(i)?.let { it.isLetterOrDigit() || it == '_' } == true) {
            i++
        }

        val value = input.subSequence(col, i).toString()

        return TokenMatchResult(
            length = i - col,
            token = IdentifierType(value, col to line)
        )
    }

    override fun toString(): String {
        return "IdentifierMatcher"
    }
}
