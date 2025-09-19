package pt.rafap.token


/**
 * Strategy for recognizing a token at a given (line, col) within a single line of text.
 *
 * Implementations must return a [TokenMatchResult] when a token is matched starting at [col],
 * or null if they cannot match at that position. The tokenizer will try matchers in order.
 *
 * @param input The line content being scanned.
 * @param line Zero-based line index.
 * @param col Zero-based column index where matching should start.
 * @param peek Function to safely peek a character at a given index in the current line.
 */
fun interface TokenMatcher {
    /**
     * Strategy for recognizing a token at a given (line, col) within a single line of text.
     *
     * Implementations must return a [TokenMatchResult] when a token is matched starting at [col],
     * or null if they cannot match at that position. The tokenizer will try matchers in order.
     *
     * @param input The line content being scanned.
     * @param line Zero-based line index.
     * @param col Zero-based column index where matching should start.
     * @param peek Function to safely peek a character at a given index in the current line.
     */
    fun match(input: CharSequence, line: Int, col: Int, peek: (Int) -> Char?): TokenMatchResult?
}