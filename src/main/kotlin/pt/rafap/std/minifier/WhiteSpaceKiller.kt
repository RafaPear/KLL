package pt.rafap.std.minifier

import pt.rafap.std.types.whitespace.WhitespaceSymbol
import pt.rafap.minify.MinifierMatcher
import pt.rafap.minify.MinifierResult
import pt.rafap.token.Token

class WhiteSpaceKiller : MinifierMatcher {

    override fun match(input: MutableList<Token>): MinifierResult? {
        if (input.isEmpty()) return null
        val token = input[0]

        if (token.type != WhitespaceSymbol) return null

                return MinifierResult.NoError
    }
}