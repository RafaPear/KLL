package pt.rafap.std.rules

import pt.rafap.std.types.symbols.operators.SlashSymbol
import pt.rafap.rules.SyntaxRule
import pt.rafap.std.types.whitespace.WhitespaceSymbol

object SlashRule : SyntaxRule {
    override val appliesTo = SlashSymbol
    override val expects = listOf(
        SlashSymbol,
        WhitespaceSymbol
    )
}
