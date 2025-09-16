package std.types

import std.matchers.SymbolMatcher
import token.TokenMatcher
import token.TokenType

fun defaultSymbolMatchers(): Array<TokenMatcher> {
    return arrayOf(
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
}

object EndOfLineSymbol : TokenType {
    override val name = "END_OF_LINE"
}

object WhitespaceSymbol : TokenType {
    override val name = "WHITESPACE"
}

object EndOfFileSymbol : TokenType {
    override val name = "END_OF_FILE"
}

val WHITESPACE_GROUP = arrayOf(
    WhitespaceSymbol,
    EndOfLineSymbol,
    EndOfFileSymbol,
)

object ClosedBracketSymbol : TokenType {
    override val name = "OPEN_BRACKET"
}

object OpenBracketSymbol : TokenType {
    override val name = "OPEN_BRACKET"
}

val BRACKET_GROUP = arrayOf(
    OpenBracketSymbol,
    ClosedBracketSymbol,
)

object ClosedParenthesisSymbol : TokenType {
    override val name = "CLOSED_PARENTHESIS"
}

object OpenParenthesisSymbol : TokenType {
    override val name = "OPEN_PARENTHESIS"
}

val PARENTHESIS_GROUP = arrayOf(
    OpenParenthesisSymbol,
    ClosedParenthesisSymbol,
)

object ClosedBraceSymbol : TokenType {
    override val name = "CLOSED_BRACE"
}

object OpenBraceSymbol : TokenType {
    override val name = "OPEN_BRACE"
}

val BRACE_GROUP = arrayOf(
    OpenBraceSymbol,
    ClosedBraceSymbol,
)

val CANONICAL_GROUP = BRACE_GROUP + BRACKET_GROUP + PARENTHESIS_GROUP

object EqualsSymbol : TokenType {
    override val name = "EQUALS"
}

object PlusSymbol : TokenType {
    override val name = "PLUS"
}

object CommaSymbol : TokenType {
    override val name = "COMMA"
}

object ColonSymbol : TokenType {
    override val name = "COLON"
}

object SemicolonSymbol : TokenType {
    override val name = "SEMICOLON"
}

object DotSymbol : TokenType {
    override val name = "DOT"
}

object DashSymbol : TokenType {
    override val name = "DASH"
}

object StarSymbol : TokenType {
    override val name = "ASTERISK"
}

object SlashSymbol : TokenType {
    override val name = "SLASH"
}

object BackslashSymbol : TokenType {
    override val name = "BACKSLASH"
}

object PercentSymbol : TokenType {
    override val name = "PERCENT"
}

object ExclamationSymbol : TokenType {
    override val name = "EXCLAMATION"
}

object QuestionSymbol : TokenType {
    override val name = "QUESTION"
}

object GreaterThanSymbol : TokenType {
    override val name = "GREATER_THAN"
}

object LessThanSymbol : TokenType {
    override val name = "LESS_THAN"
}

object DoubleQuoteSymbol : TokenType {
    override val name = "DOUBLE_QUOTE"
}

object SingleQuoteSymbol : TokenType {
    override val name = "SINGLE_QUOTE"
}

object AndSymbol : TokenType {
    override val name = "AND"
}

object VerticalBarSymbol : TokenType {
    override val name = "VERTICAL_BAR"
}

object NullType : TokenType {
    override val name = "NULL"
}

object DollarSymbol : TokenType {
    override val name = "DOLLAR"
}

object HashSymbol : TokenType {
    override val name = "HASH"
}

val SYMBOLS_GROUP = arrayOf(
    EqualsSymbol,
    PlusSymbol,
    CommaSymbol,
    ColonSymbol,
    SemicolonSymbol,
    DotSymbol,
    DashSymbol,
    StarSymbol,
    SlashSymbol,
    BackslashSymbol,
    PercentSymbol,
    ExclamationSymbol,
    QuestionSymbol,
    GreaterThanSymbol,
    LessThanSymbol,
    DoubleQuoteSymbol,
    SingleQuoteSymbol,
    AndSymbol,
    VerticalBarSymbol,
    DollarSymbol,
    HashSymbol,
) + CANONICAL_GROUP

val LOGICAL_SYMBOLS = arrayOf(
    AndSymbol,
    VerticalBarSymbol,
    ExclamationSymbol,
    EqualsSymbol,
    GreaterThanSymbol,
    LessThanSymbol,
)

val MATH_SYMBOLS = arrayOf(
    PlusSymbol,
    DashSymbol,
    StarSymbol,
    SlashSymbol,
    PercentSymbol,
)

val BITWISE_SYMBOLS = arrayOf(
    AndSymbol,
    VerticalBarSymbol,
    DashSymbol,
    LessThanSymbol,
    GreaterThanSymbol,
)
