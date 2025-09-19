package pt.rafap.token

/**
 * Marker interface for token categories.
 *
 * Implementations usually model concrete token kinds (e.g., identifiers, numbers, operators, punctuators).
 * The convenience invoke operators create [Token] instances of this type.
 */
interface TokenType {
    /** Human-readable name of the token type. */
    val name: String

    /** Builds a [Token] of this type at the given [position]. */
    operator fun invoke(value: String, position: TokenPos): Token {
        return Token(this, value, position)
    }

    /** Builds a [Token] of this type using a zero-based (line, col) pair. */
    operator fun invoke(value: String, position: Pair<Int, Int>): Token {
        return Token(this, value, TokenPos(position))
    }
}
