package minimize

import token.Token

interface MinimizerMatcher{
    fun match(input: MutableList<Token>): MinimizerResult?
}