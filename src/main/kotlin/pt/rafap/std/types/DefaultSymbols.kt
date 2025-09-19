package pt.rafap.std.types

import pt.rafap.std.types.symbols.other.BackslashSymbol
import pt.rafap.std.types.symbols.canonical.brace.ClosedBraceSymbol
import pt.rafap.std.types.symbols.canonical.bracket.ClosedBracketSymbol
import pt.rafap.std.types.symbols.canonical.parenthesis.ClosedParenthesisSymbol
import pt.rafap.std.types.symbols.other.ColonSymbol
import pt.rafap.std.types.symbols.other.CommaSymbol
import pt.rafap.std.types.symbols.other.DollarSymbol
import pt.rafap.std.types.symbols.other.DotSymbol
import pt.rafap.std.types.symbols.other.DoubleQuoteSymbol
import pt.rafap.std.types.whitespace.EndOfFileSymbol
import pt.rafap.std.types.whitespace.EndOfLineSymbol
import pt.rafap.std.types.symbols.operators.EqualsSymbol
import pt.rafap.std.types.symbols.operators.ExclamationSymbol
import pt.rafap.std.types.symbols.operators.GreaterThanSymbol
import pt.rafap.std.types.symbols.other.HashSymbol
import pt.rafap.std.types.symbols.operators.LessThanSymbol
import pt.rafap.std.types.symbols.canonical.brace.OpenBraceSymbol
import pt.rafap.std.types.symbols.canonical.bracket.OpenBracketSymbol
import pt.rafap.std.types.symbols.canonical.parenthesis.OpenParenthesisSymbol
import pt.rafap.std.types.symbols.other.QuestionSymbol
import pt.rafap.std.types.symbols.other.SemicolonSymbol
import pt.rafap.std.types.symbols.other.SingleQuoteSymbol
import pt.rafap.std.types.symbols.operators.SlashSymbol
import pt.rafap.std.types.symbols.operators.StarSymbol
import pt.rafap.std.types.symbols.operators.VerticalBarSymbol
import pt.rafap.std.types.whitespace.WhitespaceSymbol
import pt.rafap.std.matchers.SymbolMatcher
import pt.rafap.std.types.symbols.operators.AndSymbol
import pt.rafap.std.types.symbols.operators.DashSymbol
import pt.rafap.std.types.symbols.operators.PercentSymbol
import pt.rafap.std.types.symbols.operators.PlusSymbol
import pt.rafap.token.TokenType

object DefaultSymbols {
    val SYMBOL_MATCHERS: Array<SymbolMatcher> = arrayOf(
        SymbolMatcher('[', OpenBracketSymbol),
        SymbolMatcher(']', ClosedBracketSymbol),
        SymbolMatcher('(', OpenParenthesisSymbol),
        SymbolMatcher(')', ClosedParenthesisSymbol),
        SymbolMatcher('{', OpenBraceSymbol),
        SymbolMatcher('}', ClosedBraceSymbol),
        SymbolMatcher('=', EqualsSymbol),
        SymbolMatcher('+', PlusSymbol),
        SymbolMatcher(',', CommaSymbol),
        SymbolMatcher(':', ColonSymbol),
        SymbolMatcher(';', SemicolonSymbol),
        SymbolMatcher('.', DotSymbol),
        SymbolMatcher('-', DashSymbol),
        SymbolMatcher('*', StarSymbol),
        SymbolMatcher('/', SlashSymbol),
        SymbolMatcher('\\', BackslashSymbol),
        SymbolMatcher('%', PercentSymbol),
        SymbolMatcher('!', ExclamationSymbol),
        SymbolMatcher('?', QuestionSymbol),
        SymbolMatcher('>', GreaterThanSymbol),
        SymbolMatcher('<', LessThanSymbol),
        SymbolMatcher('"', DoubleQuoteSymbol),
        SymbolMatcher('\'', SingleQuoteSymbol),
        SymbolMatcher('&', AndSymbol),
        SymbolMatcher('|', VerticalBarSymbol),
        SymbolMatcher('$', DollarSymbol),
        SymbolMatcher('#', HashSymbol),
    )

    val ALL_SYMBOLS: Array<TokenType> = SYMBOL_MATCHERS.map { it.tokenType }.toTypedArray()

    val WHITESPACE_SYMBOL_GROUP = arrayOf(
        WhitespaceSymbol,
        EndOfLineSymbol,
        EndOfFileSymbol,
    )

    val BRACKET_SYMBOL_GROUP = arrayOf(
        OpenBracketSymbol,
        ClosedBracketSymbol,
    )

    val PARENTHESIS_SYMBOL_GROUP = arrayOf(
        OpenParenthesisSymbol,
        ClosedParenthesisSymbol,
    )

    val BRACE_SYMBOL_GROUP = arrayOf(
        OpenBraceSymbol,
        ClosedBraceSymbol,
    )

    val CANONICAL_SYMBOL_GROUP = BRACE_SYMBOL_GROUP + BRACKET_SYMBOL_GROUP + PARENTHESIS_SYMBOL_GROUP

    val LOGICAL_SYMBOL_GROUP = arrayOf(
        AndSymbol,
        VerticalBarSymbol,
        ExclamationSymbol,
        EqualsSymbol,
        GreaterThanSymbol,
        LessThanSymbol,
    )

    val MATH_SYMBOL_GROUP = arrayOf(
        PlusSymbol,
        DashSymbol,
        StarSymbol,
        SlashSymbol,
        PercentSymbol,
    )

    val BITWISE_SYMBOL_GROUP = arrayOf(
        AndSymbol,
        VerticalBarSymbol,
        DashSymbol,
        LessThanSymbol,
        GreaterThanSymbol,
    )
}