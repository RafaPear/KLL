package token


fun interface TokenMatcher {
    fun match(input: CharSequence, line: Int, col: Int, peek: (Int) -> Char?): TokenMatchResult?
}