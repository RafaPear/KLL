# Lexer Kotlin Library

A lightweight, extensible lexical analysis (lexer/tokenizer) toolkit written in Kotlin. It provides:

- Tokenization using pluggable TokenMatcher implementations
- Optional token stream minimization (minifier) via MinifierMatcher
- Pre-syntax validation via declarative SyntaxRule definitions
- Simple logging controls for verbosity during lexing workflows

## Modules and Packages

- pt.rafap: Public façade (Lexer) for configuring and running the pipeline.
- pt.rafap.components: Internal pipeline building blocks (Tokenizer, Minifier, PreSyntaxValidator).
- pt.rafap.token: Core token model and matching primitives.
- pt.rafap.minify: Minifier extension point and results.
- pt.rafap.rules: Pre-syntax rule interfaces and results.
- pt.rafap.std: A set of standard token types, matchers, and utilities.

## Typical Flow

1. Configure the Lexer with TokenMatcher instances to recognize tokens.
2. Optionally add MinifierMatcher instances to drop or transform non-essential tokens.
3. Optionally add SyntaxRule instances to validate token adjacency before deeper parsing.
4. Run lexFile or tokenize raw text and process the resulting tokens.

## Generating Documentation

This project uses Dokka to generate Javadoc-style documentation.

- HTML Javadoc-style docs: `./gradlew dokkaJavadoc`
- HTML rich docs: `./gradlew dokkaHtml`
- Convenience alias (if configured): `./gradlew javadoc`

Output directories (default):
- build/dokka/javadoc
- build/dokka/html

## Versioning and Compatibility

- Kotlin: 2.2.x
- Dokka: 1.9.x

## License

MIT (or project-specific, update as needed).
