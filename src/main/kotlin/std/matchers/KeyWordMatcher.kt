package std.matchers

import token.TokenMatchResult
import token.TokenMatcher
import token.TokenType

class KeyWordMatcher(val keyWords: List<String>, val tokenType: TokenType) : TokenMatcher {

    override fun match(input: CharSequence, line: Int, col: Int, peek: (Int) -> Char?): TokenMatchResult? {
        var result: TokenMatchResult? = null
        for (s in keyWords) {
            result = StringMatcher(s, tokenType).match(input, line, col, peek)
            if (result != null) break
        }
        return result
        /*var i = col
        var temp = ""
        var peekedChar = peek(i) ?: return null
        var found = false

        while (peekedChar.let { it.isLetterOrDigit() || it == '_' }) {
            temp += peek(i)
            if (keyWords.contains(temp)) {
                found = true
                break
            }
            i++
            peekedChar = peek(i) ?: break
        }
        if (i == col) return null
        if (!found) return null

        return TokenMatchResult(
            length = i + 1 - col,
            token = tokenType(temp, col to line)
        )*/
    }

    override fun toString(): String {
        return "KeyWordMatcher(keyWords=$keyWords, tokenType=${tokenType.name})"
    }
}
