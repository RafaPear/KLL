
import minimize.MinimizerMatcher
import minimize.MinimizerResult.Companion.NoError
import token.Token

class Minimizer: LexerComponent {
    private val matchers = mutableListOf<MinimizerMatcher>()
    private var printDebug = false

    fun isEmpty() = matchers.isEmpty()

    fun addMatcher(vararg matchers: MinimizerMatcher) {
        this.matchers.addAll(matchers)
    }

    override fun shutTheFuckUp() {
        this.printDebug = false
    }

    override fun speakTheFuckUp() {
        this.printDebug = true
    }

    fun minimize(tokens: List<Token>): List<Token> {
        val badTokens = tokens.toMutableList()
        val finalTokens = mutableListOf<Token>()

        while (badTokens.isNotEmpty()) {
            val token = badTokens[0]
            var didMatch = false

            for (matcher in matchers) {
                val result = matcher.match(badTokens)
                if (result != null) {
                    if (result != NoError)
                        finalTokens.add(result.causedBy)
                    if (printDebug) println("Minimized Token: $result")
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
