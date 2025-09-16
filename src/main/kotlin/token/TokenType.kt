package token

interface TokenType {
    val name: String

    operator fun invoke(value: String, position: TokenPos): Token {
        return Token(this, value, position)
    }

    operator fun invoke(value: String, position: Pair<Int, Int>): Token {
        return Token(this, value, TokenPos(position))
    }
}
