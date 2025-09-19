package pt.rafap.std.matchers

import pt.rafap.std.types.NumberType
import pt.rafap.token.TokenMatchResult
import pt.rafap.token.TokenMatcher

object NumberMatcher : TokenMatcher {
    override fun match(input: CharSequence, line: Int, col: Int, peek: (Int) -> Char?): TokenMatchResult? {
        var i = col
        val start = i
        while (peek(i)?.isDigit() == true) {
            i++
        }
        return if (i > start) {
            TokenMatchResult(
                length = i - start,
                token = NumberType(input.subSequence(start, i).toString(), start to line)
            )
        } else null
    }

    override fun toString(): String {
        return "NumberMatcher"
    }
}
