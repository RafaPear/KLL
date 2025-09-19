package pt.rafap.std.rules

import pt.rafap.std.types.symbols.operators.ExclamationSymbol
import pt.rafap.rules.SyntaxRule
import pt.rafap.std.types.DefaultSymbols.PARENTHESIS_SYMBOL_GROUP
import pt.rafap.std.types.IdentifierType
import pt.rafap.std.types.symbols.operators.EqualsSymbol

object ExclamationRule : SyntaxRule {
    override val appliesTo = ExclamationSymbol
    override val expects = listOf(
        IdentifierType,
        EqualsSymbol,
        *PARENTHESIS_SYMBOL_GROUP
    )
}
