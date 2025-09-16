
import std.matchers.*
import std.minimizer.defaultMinimizers
import std.rules.defaultRules
import std.types.ReservedType
import std.types.defaultSymbolMatchers

fun main() {
    val lexer = Lexer()

    lexer.shutTheFuckUp()

    lexer.tokenizer.addMatchers(
        WhitespaceMatcher, // deve ser o primeiro para ignorar espaços
        KeyWordMatcher(
            listOf(
                "add",
                "addc",
                "sub",
                "subc",
                "subc",
                "inc",
                "dec",
                "lsl",
                "lsr",
                "asl",
                "not",
                "and",
                "or",
                "ldr",
                "str",
            ), ReservedType
        ),
        StringLiteralMatcher(listOf('"')),
        *defaultSymbolMatchers(),
        NumberMatcher,
        IdentifierMatcher,
    )

    lexer.minimizer.addMatcher(*defaultMinimizers)

    lexer.preSyntaxValidator.addRules(*defaultRules(),)

    var tokens = lexer.lexFile("main.s")
    lexer.tokenizer.printTokensAsFile(tokens, "result.s")
}
