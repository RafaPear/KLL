package std.matchers

import token.TokenMatchResult
import token.TokenMatcher
import token.TokenType

class SymbolMatcher(val symbol: Char, val tokenType: TokenType) : TokenMatcher {
    override fun match(input: CharSequence, line: Int, col: Int, peek: (Int) -> Char?): TokenMatchResult? {
        return if (peek(col) == symbol) {
            TokenMatchResult(1, tokenType(symbol.toString(), col to line))
        } else null
    }

    override fun toString(): String {
        return "SymbolMatcher(symbol=$symbol, tokenType=${tokenType.name})"
    }
}
