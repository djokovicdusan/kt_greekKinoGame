package com.example.kt_greekkinogame.model

data class Draw(
    val gameId: Long,
    val drawId: Long,
    val drawTime: Long,
    val status: String,
    val drawBreak: Long,
    val visualDraw: Long
)
