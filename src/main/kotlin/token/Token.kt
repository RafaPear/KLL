package token

import std.types.NullType

data class Token(
    val type: TokenType,
    val value: String,
    val position: TokenPos
) {
    override fun toString(): String {
        return "Token(type=${type.name}, value='$value', position=$position)"
    }

    companion object {
        val NULL = Token(NullType, "null", TokenPos(-1, -1))
    }
}