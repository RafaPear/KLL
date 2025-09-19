package pt.rafap.std.rules

import pt.rafap.std.types.symbols.operators.EqualsSymbol
import pt.rafap.std.types.whitespace.WhitespaceSymbol
import pt.rafap.rules.SyntaxRule
import pt.rafap.std.types.DefaultSymbols.CANONICAL_SYMBOL_GROUP
import pt.rafap.std.types.IdentifierType
import pt.rafap.std.types.ReservedType

object ReservedTypeRule : SyntaxRule {
    override val appliesTo = ReservedType
    override val expects = listOf(
        IdentifierType,
        EqualsSymbol,
        WhitespaceSymbol,
        *CANONICAL_SYMBOL_GROUP
    )
}
