package com.example.kt_greekkinogame.model

import java.io.Serializable

data class Draw(
    val gameId: Long,
    val drawId: Long,
    val drawTime: Long,
    val status: String,
    val drawBreak: Long,
    val visualDraw: Long,
    val pricePoints: PricePoints?,
    val prizeCategories: List<PrizeCategory>?,
    val wagerStatistics: WagerStatistics?
) : Serializable

data class PricePoints(
    val addOn: List<AddOn>?,
    val amount: Double?
) : Serializable

data class AddOn(
    val amount: Double,
    val gameType: String
) : Serializable

data class PrizeCategory(
    val id: Long,
    val divident: Long,
    val winners: Long,
    val distributed: Long,
    val jackpot: Long,
    val fixed: Double,
    val categoryType: Long,
    val gameType: String
) : Serializable

data class WagerStatistics(
    val columns: Long,
    val wagers: Long,
    val addOn: List<Any>?
) : Serializable
