package pt.rafap.std.rules

import pt.rafap.rules.SyntaxRule

object DefaultRules {
    val ALL_RULES: Array<SyntaxRule> = arrayOf(
            EqualsRule,
            ExclamationRule,
            DataTypeRule,
            ReservedTypeRule,
            SlashRule,
            NumberRule,
        )
}