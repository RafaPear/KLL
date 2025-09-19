package pt.rafap.components

import pt.rafap.logging.WithLogger
import pt.rafap.minify.MinifierMatcher
import pt.rafap.minify.MinifierResult
import pt.rafap.token.Token

internal class Minifier : WithLogger() {
    private val matchers = mutableListOf<MinifierMatcher>()

    fun isEmpty() = matchers.isEmpty()

    fun addMatcher(vararg matchers: MinifierMatcher) {
        this.matchers.addAll(matchers)
    }

    fun minify(tokens: List<Token>): List<Token> {
        if (isEmpty()) {
            logger.error("What are you trying to do? Minifier has no matchers.")
            throw Exception("Minifier has no matchers. Cannot proceed.")
        }

        val badTokens = tokens.toMutableList()
        val finalTokens = mutableListOf<Token>()

        while (badTokens.isNotEmpty()) {
            val token = badTokens[0]
            var didMatch = false

            for (matcher in matchers) {
                val result = matcher.match(badTokens)
                if (result != null) {
                    if (result != MinifierResult.NoError)
                        finalTokens.add(result.causedBy)
                    badTokens.remove(token)
                    didMatch = true
                    break
                }
            }
            if (!didMatch) {
                finalTokens.add(token)
                badTokens.remove(token)
            }
        }

        return finalTokens
    }
}
