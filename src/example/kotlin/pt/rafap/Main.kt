package pt.rafap
import pt.rafap.std.matchers.*
import pt.rafap.std.minifier.DefaultMinifiers.ALL_MINIFIERS
import pt.rafap.std.rules.DefaultRules.ALL_RULES
import pt.rafap.std.types.DefaultSymbols.SYMBOL_MATCHERS
import pt.rafap.std.types.ReservedType

fun main() {
    val lexer = Lexer()

    lexer.speakTheFuckUp()

    // lexer.setLogLevel(Level.DEBUG)

    lexer.addMatchers(
        WhitespaceMatcher, // deve ser o primeiro para ignorar espaços
        KeyWordMatcher(
            listOf(
                "add", "addc", "sub", "subc", "subc", "inc", "dec",
                "lsl", "lsr", "asl", "not", "and", "or", "ldr", "str", "b.", "b"
            ), ReservedType
        ),
        StringLiteralMatcher(listOf('"')),
        *SYMBOL_MATCHERS,
        NumberMatcher,
        IdentifierMatcher,
    )

    lexer.addMinifiers(*ALL_MINIFIERS)

    lexer.addRules(*ALL_RULES)

    val tokens = lexer.lexFile("main.s", true)

    lexer.printTokensAsFile(tokens, "out.s")
}
