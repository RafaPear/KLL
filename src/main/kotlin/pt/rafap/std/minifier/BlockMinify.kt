package pt.rafap.std.minifier

import pt.rafap.minify.MinifierMatcher
import pt.rafap.minify.MinifierResult
import pt.rafap.token.Token
import pt.rafap.token.TokenType

class BlockMinify(
    val startSequence: List<TokenType>,
    val endSequence: List<TokenType>
) : MinifierMatcher {

    override fun match(input: MutableList<Token>): MinifierResult? {
        var isInBlock = false

        // if this passes the input has the correct start sequence
        if (input.size < startSequence.size) return null
        val inputPrefixList = input.subList(0, startSequence.size).map { it.type }
        if (inputPrefixList != startSequence) return null

        // Find if the end sequence exists
        var i = startSequence.size - 1
        while (i < input.size) {
            if (i + endSequence.size > input.size) break
            val subList = input.subList(i, i + endSequence.size).map { it.type }
            if (subList == endSequence) {
                isInBlock = true
                break
            }
            i++
        }

        // If we are in a comment, remove the comment tokens from input
        if (isInBlock) {
            // Remove the comment tokens from input
            val toRemove = i + endSequence.size
            val removedTokens = input.subList(0, toRemove)
            input.removeAll(removedTokens)

            // Return a MinimizerResult indicating that a comment was removed
                        return MinifierResult.NoError
        }

        return null
    }
}