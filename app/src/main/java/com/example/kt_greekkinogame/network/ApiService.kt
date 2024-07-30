package com.example.kt_greekkinogame.network

import com.example.kt_greekkinogame.model.Draw
import com.example.kt_greekkinogame.model.DrawResult
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("draws/v3.0/{gameId}/upcoming/20")
    suspend fun getUpcomingDraws(@Path("gameId") gameId: Int): List<Draw>

    @GET("draws/v3.0/{gameId}/draw-date/{fromDate}/{toDate}")
    suspend fun getResults(
        @Path("gameId") gameId: Int,
        @Path("fromDate") fromDate: String,
        @Path("toDate") toDate: String
    ): List<DrawResult>
}
