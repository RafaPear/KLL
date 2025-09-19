package pt.rafap.token

/**
 * Result of a successful token match.
 *
 * @property length Number of characters consumed to form [token].
 * @property token The matched token instance.
 */
data class TokenMatchResult(
    val length: Int,
    val token: Token
)