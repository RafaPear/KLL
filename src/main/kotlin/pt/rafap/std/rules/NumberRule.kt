package pt.rafap.std.rules

import pt.rafap.rules.SyntaxRule
import pt.rafap.std.types.NumberType

object NumberRule : SyntaxRule {
    override val appliesTo = NumberType
    override val expects = listOf(
        NumberType,
    )
}