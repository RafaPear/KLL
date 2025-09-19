package pt.rafap.std.rules

import pt.rafap.std.types.whitespace.WhitespaceSymbol
import pt.rafap.rules.SyntaxRule
import pt.rafap.std.types.DefaultSymbols.CANONICAL_SYMBOL_GROUP
import pt.rafap.std.types.IdentifierType
import pt.rafap.std.types.NumberType
import pt.rafap.std.types.StringType
import pt.rafap.std.types.symbols.operators.EqualsSymbol

object EqualsRule : SyntaxRule {
    override val appliesTo = EqualsSymbol
    override val expects = listOf(
        IdentifierType,
        NumberType,
        *CANONICAL_SYMBOL_GROUP,
        StringType,
        EqualsSymbol,
        WhitespaceSymbol
    )
}
