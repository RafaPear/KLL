package pt.rafap.components

import pt.rafap.std.types.whitespace.EndOfFileSymbol
import pt.rafap.logging.WithLogger
import pt.rafap.rules.SyntaxRule
import pt.rafap.token.Token
import pt.rafap.token.TokenType
import pt.rafap.token.error.UnexpectedToken

internal class PreSyntaxValidator : WithLogger() {
    private val rules = mutableListOf<SyntaxRule>()

    fun isEmpty() = rules.isEmpty()

    fun addRules(vararg rules: SyntaxRule) {
        this.rules.addAll(rules)
    }

    fun analyse(tokens: List<Token>): Boolean {
        if (isEmpty()) {
            logger.error("Were are the rules, man? Is this an anarchy?")
            throw Exception("Pre-syntax validator has no rules. Cannot proceed.")
        }

        for ((i, token) in tokens.withIndex()) {
            val nextToken =
                if (i + 1 < tokens.size) tokens[i + 1]
                else {
                    return if (token.type != EndOfFileSymbol) {
                        logger.warn(UnexpectedToken(tokens, i).message)
                        false
                    } else true
                }

            if (!analyseSingle(token, nextToken)) {
                logger.warn(UnexpectedToken(tokens, i).message)
            }
        }
        return true
    }

    private fun analyseSingle(token: Token, next: Token): Boolean {
        // -- Flags --
        var matched = false
        var existsRule = false

        val expects = mutableListOf<TokenType>()

        for (rule in rules) {
            expects += rule.expects
            if (rule.appliesTo != token.type) continue
            existsRule = true

            val result = rule(token, next)

            if (result != null && !result.error) {
                matched = true
                break
            }
        }

        if (!existsRule) matched = true

        return matched
    }
}
