package com.chandan.games.snakegame

data class Node (
    var isON: Boolean = false,
    var index: Int = 0,
    var next: Node? = null,
    var prev: Node? = null,
    var direction: Direction? = null,
    var viewHolder: GridViewAdapter.NodeItemViewHolder? = null
) {
    fun nextIndex(): Int {
        var currX = (index + 1) % NO_OF_COLUMNS
        var currY = (index + 1) / NO_OF_COLUMNS + if(currX > 0) 1 else 0
        if(currX == 0) currX = NO_OF_COLUMNS
        var finalX = currX + (direction?.xIncrement ?: 0)
        if(finalX > NO_OF_COLUMNS) finalX = 1
        else if(finalX == 0) finalX = NO_OF_COLUMNS
        var finalY = currY + (direction?.yIncrement ?: 0)
        if(finalY == 0) { finalY = NO_OF_ROWS }
        else if(finalY > NO_OF_ROWS) { finalY = 1}
        return ((finalY - 1) * NO_OF_COLUMNS + finalX) - 1
    }
}