package pt.rafap.std.rules

import pt.rafap.std.types.symbols.operators.SlashSymbol
import pt.rafap.rules.SyntaxRule
import pt.rafap.std.types.DataType
import pt.rafap.std.types.DefaultSymbols.WHITESPACE_SYMBOL_GROUP
import pt.rafap.std.types.IdentifierType
import pt.rafap.std.types.symbols.other.SemicolonSymbol

object SemicolonRule : SyntaxRule {
    override val appliesTo = SemicolonSymbol
    override val expects = listOf(
        IdentifierType,
        DataType,
        SlashSymbol,
        *WHITESPACE_SYMBOL_GROUP
    )
}
