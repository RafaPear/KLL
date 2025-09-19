package pt.rafap.token

import pt.rafap.std.types.symbols.other.NullType

/**
 * Represents a single lexical unit (token) emitted by the tokenizer.
 *
 * @property type The conceptual category of the token (e.g., identifier, number, symbol).
 * @property value The literal text matched for this token.
 * @property position The 1-based (line, col) indicating where the token starts.
 */
data class Token(
    val type: TokenType,
    val value: String,
    val position: TokenPos
) {
    override fun toString(): String {
        return "Token(type=${type.name}, value='$value', position=$position)"
    }

    /**
     * Common constants and factory helpers for [Token].
     */
    companion object {
        /** A sentinel token used to represent the absence of a real token. */
        val NULL = Token(NullType, "null", TokenPos(-1, -1))
    }
}