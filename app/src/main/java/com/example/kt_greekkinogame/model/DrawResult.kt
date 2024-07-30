package com.example.kt_greekkinogame.model

data class DrawResult(
    val gameId: Long,
    val drawId: Long,
    val drawTime: Long,
    val status: String,
    val drawBreak: Long,
    val visualDraw: Long,
    val winningNumbers: WinningNumbers,

)

data class WinningNumbers(
    val list: List<Int>,
    val bonus: List<Int>
)

