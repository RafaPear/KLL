package token

class TokenPos {
    val line: Int
    val col: Int

    constructor(line: Int, col: Int){
        this.line = line + 1
        this.col = col + 1
    }

    constructor(pos: Pair<Int, Int>){
        val (line, col) = pos
        this.line = line + 1
        this.col = col + 1
    }

    override fun toString(): String {
        return "($line, $col)"
    }
}
