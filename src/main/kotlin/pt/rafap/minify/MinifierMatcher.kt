package pt.rafap.minify

import pt.rafap.token.Token

interface MinifierMatcher {
    fun match(input: MutableList<Token>): MinifierResult?
}