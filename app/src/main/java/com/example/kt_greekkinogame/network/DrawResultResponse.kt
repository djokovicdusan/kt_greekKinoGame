package com.example.kt_greekkinogame.network

import com.example.kt_greekkinogame.model.DrawResult
import java.io.Serializable

data class DrawResultResponse(
    val content: List<DrawResult>
) : Serializable
