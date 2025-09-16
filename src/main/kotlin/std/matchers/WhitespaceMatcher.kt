package std.matchers

import std.types.WhitespaceSymbol
import token.TokenMatchResult
import token.TokenMatcher

object WhitespaceMatcher : TokenMatcher {
    override fun match(input: CharSequence, line: Int, col: Int, peek: (Int) -> Char?): TokenMatchResult? {
        var i = col
        val start = i

        // Nao contar com caracteres de escape
        var current = peek(i)
        while (current?.isWhitespace() == true) {
            if (current == '\n' || current == '\r') break
            i++
            current = peek(i)
        }

        return if (i > start) {
            TokenMatchResult(
                length = i - start,
                token = WhitespaceSymbol(input.subSequence(start, i).toString(), start to line)
            )
        } else null
    }

    override fun toString(): String {
        return "WhitespaceMatcher"
    }
}
