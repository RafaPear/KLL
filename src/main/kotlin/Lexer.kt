import token.Token


class Lexer {
    val tokenizer = Tokenizer()
    val preSyntaxValidator = PreSyntaxValidator()
    val minimizer = Minimizer()

    private var printDebug = false

    fun shutTheFuckUp()  {
        this.printDebug = false
        tokenizer.shutTheFuckUp()
        preSyntaxValidator.shutTheFuckUp()
        minimizer.shutTheFuckUp()
    }

    fun speakTheFuckUp() {
        this.printDebug = true
        tokenizer.speakTheFuckUp()
        preSyntaxValidator.speakTheFuckUp()
        minimizer.speakTheFuckUp()
    }

    fun lexFile(filePath: String, verify: Boolean = false): List<Token> {
        if (tokenizer.isEmpty())
            throw Exception("Tokenizer has no matchers. Cannot proceed.")

        val tempTokens = tokenizer.tokenizeFile(filePath, verify = false)

        val tokens = if (minimizer.isEmpty()){
            if (printDebug) println("Minimizer has no matchers. Skipping minimization.")
            tempTokens
        }
        else {
            minimizer.minimize(tempTokens)
        }

        if (preSyntaxValidator.isEmpty()) {
            if (printDebug) println("Pre-syntax validator has no rules. Skipping pre-syntax validation.")
            return tokens
        }

        val valid = preSyntaxValidator.analyse(tokens)
        if (!valid) {
            throw Exception("Pre-syntax validation failed.")
        }
        return tokens
    }
}