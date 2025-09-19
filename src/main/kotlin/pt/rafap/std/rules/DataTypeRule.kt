package pt.rafap.std.rules

import pt.rafap.rules.SyntaxRule
import pt.rafap.std.types.DataType
import pt.rafap.std.types.IdentifierType
import pt.rafap.std.types.whitespace.WhitespaceSymbol

object DataTypeRule : SyntaxRule {
    override val appliesTo = DataType
    override val expects = listOf(
        IdentifierType,
        WhitespaceSymbol
    )
}
