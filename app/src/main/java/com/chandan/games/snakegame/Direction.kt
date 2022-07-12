package com.chandan.games.snakegame

enum class Direction(val xIncrement: Int, val yIncrement: Int) {
    RIGHT(1, 0),
    DOWN(0, -1),
    LEFT(-1, 0),
    UP(0, 1);
}