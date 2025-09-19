package pt.rafap.minify

import pt.rafap.token.Token

data class MinifierResult(val error: Boolean, val causedBy: Token) {
    companion object {
                val NoError = MinifierResult(false, Token.NULL)
    }
}