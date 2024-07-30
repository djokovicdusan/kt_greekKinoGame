package com.example.kt_greekkinogame.repository

import android.util.Log
import com.example.kt_greekkinogame.model.Draw
import com.example.kt_greekkinogame.model.DrawResult
import com.example.kt_greekkinogame.network.ApiService

class DrawRepository(private val apiService: ApiService) {

    suspend fun getUpcomingDraws(gameId: Int): List<Draw> {
        return try {
            apiService.getUpcomingDraws(gameId)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getDrawResults(gameId: Int, fromDate: String, toDate: String): List<DrawResult> {
        return try {
            val response = apiService.getDrawResults(gameId, fromDate, toDate)
            response.content
        } catch (e: Exception) {
            emptyList()
        }
    }
}
