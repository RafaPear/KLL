package pt.rafap.token

/**
 * 1-based source position of a token (line and column).
 *
 * Input constructors accept zero-based values which are converted to 1-based for display and reporting.
 */
class TokenPos {
    /** 1-based line number. */
    val line: Int
    /** 1-based column number. */
    val col: Int

    /** Creates a position from zero-based [line] and [col] values. */
    constructor(line: Int, col: Int) {
        this.line = line + 1
        this.col = col + 1
    }

    /** Creates a position from a zero-based (line, col) pair. */
    constructor(pos: Pair<Int, Int>) {
        val (line, col) = pos
        this.line = line + 1
        this.col = col + 1
    }

    override fun toString(): String {
        return "($line, $col)"
    }
}
