package com.example.kt_greekkinogame.repository

import android.util.Log
import com.example.kt_greekkinogame.model.Draw
import com.example.kt_greekkinogame.network.ApiService

class DrawRepository(private val apiService: ApiService) {
    suspend fun getUpcomingDraws(gameId: Int): List<Draw> {
        return try {
            val draws = apiService.getUpcomingDraws(gameId)
            draws
        } catch (e: Exception) {
            emptyList()
        }
    }
}
