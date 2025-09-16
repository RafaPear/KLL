package std.minimizer

import minimize.MinimizerMatcher
import minimize.MinimizerResult
import std.types.EndOfLineSymbol
import token.Token

class EmptyLinesKiller: MinimizerMatcher {

    override fun match(input: MutableList<Token>): MinimizerResult? {

        if (input.size < 2) return null
        val tokenA = input[0]
        val tokenB = input[1]

        if (tokenA.type == EndOfLineSymbol && tokenB.type == tokenA.type) {
            input.remove(tokenB)
            return MinimizerResult(false, tokenA)
        }

        return null
    }
}