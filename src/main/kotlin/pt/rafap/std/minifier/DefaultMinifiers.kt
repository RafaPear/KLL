package pt.rafap.std.minifier

import pt.rafap.std.types.whitespace.EndOfLineSymbol
import pt.rafap.std.types.symbols.operators.SlashSymbol
import pt.rafap.std.types.symbols.operators.StarSymbol

object DefaultMinifiers {
    val ALL_MINIFIERS = arrayOf(
        BlockMinify(
            startSequence = listOf(SlashSymbol, StarSymbol),
            endSequence = listOf(StarSymbol, SlashSymbol)
        ),
        BlockMinify(
            startSequence = listOf(SlashSymbol, SlashSymbol),
            endSequence = listOf(EndOfLineSymbol)
        ),
        EndOfLineKiller(),
        WhiteSpaceKiller()
    )
}