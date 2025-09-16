package std.rules

import rules.SyntaxRule
import std.types.*


fun defaultRules(): Array<SyntaxRule> {
    return arrayOf(
        EqualsRule,
        ExclamationRule,
        DataTypeRule,
        SlashRule,
        NumberRule,
    )
}

object EqualsRule : SyntaxRule {
    override val appliesTo = EqualsSymbol
    override val expects = listOf(
        IdentifierType,
        NumberType,
        *CANONICAL_GROUP,
        StringType,
        EqualsSymbol,
        WhitespaceSymbol
    )
}

object ExclamationRule : SyntaxRule {
    override val appliesTo = ExclamationSymbol
    override val expects = listOf(
        IdentifierType,
        EqualsSymbol,
        *PARENTHESIS_GROUP
    )
}

object DataTypeRule : SyntaxRule {
    override val appliesTo = DataType
    override val expects = listOf(
        IdentifierType,
        WhitespaceSymbol
    )
}

object SemicolonRule : SyntaxRule {
    override val appliesTo = SemicolonSymbol
    override val expects = listOf(
        IdentifierType,
        DataType,
        SlashSymbol,
        *WHITESPACE_GROUP
    )
}

object SlashRule : SyntaxRule {
    override val appliesTo = SlashSymbol
    override val expects = listOf(
        SlashSymbol,
        WhitespaceSymbol
    )
}

object NumberRule : SyntaxRule {
    override val appliesTo = NumberType
    override val expects = listOf(
        *MATH_SYMBOLS,
        *LOGICAL_SYMBOLS,
        *BITWISE_SYMBOLS,
        *CANONICAL_GROUP,
        CommaSymbol,
        SemicolonSymbol,
        DotSymbol,
        WhitespaceSymbol,
        SemicolonSymbol
    )
}