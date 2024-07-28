package com.example.kt_greekkinogame.network

import com.example.kt_greekkinogame.model.Draw
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("draws/v3.0/{gameId}/upcoming/20")
    suspend fun getUpcomingDraws(@Path("gameId") gameId: Int): List<Draw>
}
