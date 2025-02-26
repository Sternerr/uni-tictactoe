package com.noah.tictactoe.data.model

data class Game (
    val board: List<Int> = List(9) { 2 },
    val players: List<String> = listOf(),
    val turn: Int = 0,
    val gameState: String = "",
)
