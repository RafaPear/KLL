package pt.rafap.rules

import pt.rafap.token.Token
import pt.rafap.token.TokenType

interface SyntaxRule {
    val appliesTo: TokenType
    val expects: List<TokenType>

    operator fun invoke(current: Token, next: Token): SyntaxResult? {
        if (current.type != appliesTo) return null
        return validate(next)
    }

    fun validate(next: Token): SyntaxResult {
        val error = next.type !in expects
        return SyntaxResult(error, next)
    }
}