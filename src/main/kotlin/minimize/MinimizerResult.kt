package minimize

import token.Token

data class MinimizerResult(val error: Boolean, val causedBy: Token){
    companion object {
        val NoError = MinimizerResult(false, Token.NULL)
    }
}