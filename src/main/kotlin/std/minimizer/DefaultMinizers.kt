package std.minimizer

import std.types.EndOfLineSymbol
import std.types.SlashSymbol
import std.types.StarSymbol

val defaultMinimizers = arrayOf(
    BlockMinimize(
        startSequence = listOf(SlashSymbol, StarSymbol), // //
        endSequence = listOf(StarSymbol, SlashSymbol)
    ),
    BlockMinimize(
        startSequence = listOf(SlashSymbol, SlashSymbol), // //
        endSequence = listOf(EndOfLineSymbol)
    ),
    EmptyLinesKiller()
)