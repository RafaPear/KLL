package pt.rafap.rules

import pt.rafap.token.Token

data class SyntaxResult(val error: Boolean, val causedBy: Token) {
    companion object {
                val NoError = SyntaxResult(false, Token.NULL)
    }
}