# Lexer (Kotlin)

A lightweight, extensible lexical analysis (lexer/tokenizer) toolkit written in Kotlin.
It lets you describe how to recognize tokens, optionally minify token streams, and run simple pre-syntax validations before parsing.

## Features
- Pluggable TokenMatcher implementations to recognize identifiers, numbers, strings, symbols, keywords, etc.
- Optional token stream minimization (minifier) via MinifierMatcher (e.g., remove whitespace, end-of-line tokens, comments/blocks).
- Pre-syntax validation via declarative SyntaxRule definitions for basic adjacency checks.
- Simple logging controls for verbosity across the pipeline.
- Comes with a std package of useful matchers, token types, rules, and minifiers.

## Requirements
- Kotlin 2.2.x
- Gradle (wrapper included)

## Getting started

### 1) Build
On Windows PowerShell/CMD:

```
./gradlew.bat build
```

On macOS/Linux:

```
./gradlew build
```

Artifacts (jars) will be in `build/libs`.

### 2) Run the included example
This project ships with a small example source set. It demonstrates configuring a Lexer and running it over a sample file.

```
./gradlew.bat runExample   # Windows
# or
./gradlew runExample       # macOS/Linux
```

The example entry point is at `src/example/kotlin/pt/rafap/Main.kt` and reads `main.s`, producing `out.s`.

### 3) Quick start (programmatic)
A minimal setup using the standard library components shipped in this repo:

```kotlin
fun demo() {
    val lexer = pt.rafap.Lexer()

    // Logging helpers:
    // lexer.shutTheFuckUp()   // Level.OFF
    lexer.speakTheFuckUp()     // Level.INFO
    // lexer.speakLouder()     // Level.DEBUG

    lexer.addMatchers(
        pt.rafap.std.matchers.WhitespaceMatcher, // typically first to handle spaces
        pt.rafap.std.matchers.KeyWordMatcher(
            listOf("add", "sub", "and", "or", "b", "b."), pt.rafap.std.types.ReservedType
        ),
        pt.rafap.std.matchers.StringLiteralMatcher(listOf('"')),
        *pt.rafap.std.types.DefaultSymbols.SYMBOL_MATCHERS,
        pt.rafap.std.matchers.NumberMatcher,
        pt.rafap.std.matchers.IdentifierMatcher,
    )

    lexer.addMinifiers(*pt.rafap.std.minifier.DefaultMinifiers.ALL_MINIFIERS)
    lexer.addRules(*pt.rafap.std.rules.DefaultRules.ALL_RULES)

    val tokens = lexer.lexFile("path/to/file.s", verify = true)
    lexer.printTokensAsFile(tokens, "out.s")
}
```

## Concepts and packages
- pt.rafap: Public façade (Lexer) for configuring and running the pipeline.
- pt.rafap.components: Internal pipeline building blocks (Tokenizer, Minifier, PreSyntaxValidator).
- pt.rafap.token: Core token model and matching primitives (Token, TokenMatcher, TokenType, etc.).
- pt.rafap.minify: Minifier extension point and results.
- pt.rafap.rules: Pre-syntax rule interfaces and results.
- pt.rafap.std: A set of standard token types, matchers, and utilities (symbols, numbers, strings, whitespace, etc.).

Helpful ready-made groups:
- DefaultSymbols.SYMBOL_MATCHERS – prebuilt SymbolMatcher array for common punctuation and operators.
- DefaultRules.ALL_RULES – a sensible baseline of pre-syntax rules.
- DefaultMinifiers.ALL_MINIFIERS – common minifiers (whitespace/EOL/comment killers, etc.).

## Run tests
```
./gradlew.bat test   # Windows
# or
./gradlew test       # macOS/Linux
```

## Generate API docs (Dokka)
```
./gradlew.bat dokkaJavadoc   # Windows
./gradlew.bat dokkaHtml
# or
./gradlew dokkaJavadoc       # macOS/Linux
./gradlew dokkaHtml
```

Output (default):
- build/dokka/javadoc
- build/dokka/html

## Project layout
- src/main/... – library sources
- src/test/... – tests (JUnit 5)
- src/example/... – example app and resources

Gradle tasks of note:
- build – compiles, runs tests, generates Javadoc-style docs
- runExample – compiles and runs the example application

## License
MIT (update if project-specific).
