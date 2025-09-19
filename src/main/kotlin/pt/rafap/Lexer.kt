package pt.rafap

import ch.qos.logback.classic.Level
import pt.rafap.components.Minifier
import pt.rafap.components.PreSyntaxValidator
import pt.rafap.components.Tokenizer
import pt.rafap.logging.WithLogger
import pt.rafap.minify.MinifierMatcher
import pt.rafap.rules.SyntaxRule
import pt.rafap.token.Token
import pt.rafap.token.TokenMatcher
import kotlin.system.exitProcess

/**
 * High-level façade to configure and run the lexing pipeline.
 *
 * The pipeline consists of three stages:
 * 1) Tokenization via a set of TokenMatcher instances
 * 2) Optional minimization (minifier) to remove or transform non-essential tokens
 * 3) Optional pre-syntax validation to verify simple adjacency rules between tokens
 *
 * Use addMatchers, addMinifiers, and addRules to configure the pipeline, then call lexFile to produce tokens.
 * Logging verbosity can be adjusted using the convenience methods below.
 */
class Lexer : WithLogger() {
    private val tokenizer = Tokenizer()
    private val preSyntaxValidator = PreSyntaxValidator()
    private val minifier = Minifier()

    override fun setLogLevel(level: Level) {
        super.setLogLevel(level)
        tokenizer.setLogLevel(level)
        preSyntaxValidator.setLogLevel(level)
        minifier.setLogLevel(level)
    }

    /**
     * Disables all logging produced by the Lexer and its internal components.
     * Equivalent to setting the log level to [Level.OFF].
     */
    fun shutTheFuckUp() {
        setLogLevel(Level.OFF)
    }

    /**
     * Enables informative logging for the Lexer and its components.
     * Equivalent to setting the log level to [Level.INFO].
     */
    fun speakTheFuckUp() {
        setLogLevel(Level.INFO)
    }

    /**
     * Enables verbose debugging logs for the Lexer and its components.
     * Equivalent to setting the log level to [Level.DEBUG].
     */
    fun speakLouder() {
        setLogLevel(Level.DEBUG)
    }

    /**
     * Runs the full lexing pipeline on a file path.
     *
     * Steps:
     * - Tokenize the file using the configured [TokenMatcher]s
     * - Optionally apply minifiers via [MinifierMatcher]s
     * - Optionally validate simple adjacency rules via [SyntaxRule]s
     *
     * @param filePath Absolute or relative path to the source file.
     * @param verify If true, verifies whether the produced tokens can reconstruct the original content.
     * @return The list of tokens representing the file content.
     * @throws Exception If tokenization fails, file I/O fails, or pre-syntax validation fails.
     */
    fun lexFile(filePath: String, verify: Boolean = false): List<Token> {
        logger.info("Starting lexing process for file: {}", filePath)

        val tempTokens = try {
            tokenizer.tokenizeFile(filePath, verify)
        } catch (e: Exception) {
            logger.error("Error during tokenization: ${e.message}")
            exitProcess(1)
        }

        val minifiedTokens = if (minifier.isEmpty()) {
            logger.warn("Minimizer has no matchers. Skipping minimization.")
            tempTokens
        } else {
            minifier.minify(tempTokens)
        }

        if (preSyntaxValidator.isEmpty()) {
            logger.warn("Pre-syntax validator has no rules. Skipping pre-syntax validation.")
            return minifiedTokens
        }

        val valid = preSyntaxValidator.analyse(minifiedTokens)
        if (!valid) {
            throw Exception("Pre-syntax validation failed.")
        }
        return minifiedTokens
    }

    /**
     * Adds one or more [TokenMatcher]s used by the tokenizer stage.
     * Matchers are evaluated in insertion order until one matches.
     */
    fun addMatchers(vararg matchers: TokenMatcher) {
        tokenizer.addMatchers(*matchers)
    }

    /**
     * Adds one or more [SyntaxRule]s used by the pre-syntax validation stage.
     * If no rules are added, the validation stage is skipped.
     */
    fun addRules(vararg rules: SyntaxRule) {
        preSyntaxValidator.addRules(*rules)
    }

    /**
     * Adds one or more [MinifierMatcher]s used to remove or transform tokens before validation.
     * If no minifiers are added, the minification stage is skipped.
     */
    fun addMinifiers(vararg minifiers: MinifierMatcher) {
        minifier.addMatcher(*minifiers)
    }

    /**
     * Utility to print a list of tokens to a file, using the tokenizer's built-in formatter.
     * @param tokens The list of tokens to print.
     * @param outputPath The file path where the tokens should be written.
     */
    fun printTokensAsFile(tokens: List<Token>, outputPath: String) {
        tokenizer.printTokensAsFile(tokens, outputPath)
    }
}