package com.chandan.games.snakegame

data class Node (
    var isON: Boolean = false,
    var index: Int = 0,
    var next: Node? = null,
    var prev: Node? = null,
    var direction: Direction? = null
) {
    fun nextIndex(): Int {
        val currX = (index + 1) % NO_OF_COLUMNS
        val currY = (index + 1) / NO_OF_COLUMNS + if(currX > 0) 1 else 0
        val finalX = currX + (direction?.xIncrement ?: 0)
        var finalY = currY + (direction?.yIncrement ?: 0)
        if(finalY == 0) { finalY = NO_OF_ROWS }
        else if(finalY > NO_OF_ROWS) { finalY %= NO_OF_ROWS }
        return ((finalY - 1) * NO_OF_COLUMNS + finalX) - 1
    }
}