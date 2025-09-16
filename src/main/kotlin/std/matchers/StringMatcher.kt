package std.matchers

import token.TokenMatchResult
import token.TokenMatcher
import token.TokenType

class StringMatcher(val symbol: String, val tokenType: TokenType) : TokenMatcher {
    override fun match(input: CharSequence, line: Int, col: Int, peek: (Int) -> Char?): TokenMatchResult? {
        var i = col
        var temp = ""
        var peekedChar = peek(i) ?: return null
        var found = false

        while (!peekedChar.isWhitespace()) {
            temp += peek(i)
            if (temp == symbol && peek(i + 1)?.isLetterOrDigit() != true) {
                found = true
                break
            }
            i++
            peekedChar = peek(i) ?: break
        }
        if (!found) return null

        return TokenMatchResult(
            length = i + 1 - col,
            token = tokenType(temp, col to line)
        )
    }

    override fun toString(): String {
        return "StringMatcher(symbol='$symbol', tokenType=${tokenType.name})"
    }
}