package com.example.kt_greekkinogame.model

import com.google.gson.annotations.SerializedName

data class Draw(
    @SerializedName("gameId")
    val gameId: Long,

    @SerializedName("drawId")
    val drawId: Long,

    @SerializedName("drawTime")
    val drawTime: Long,

    @SerializedName("status")
    val status: String,

    @SerializedName("drawBreak")
    val drawBreak: Long,

    @SerializedName("visualDraw")
    val visualDraw: Long,

    @SerializedName("pricePoints")
    val pricePoints: PricePoints?,

    @SerializedName("prizeCategories")
    val prizeCategories: List<PrizeCategory>?,

    @SerializedName("wagerStatistics")
    val wagerStatistics: WagerStatistics?
)

data class PricePoints(
    @SerializedName("addOn")
    val addOn: List<AddOn>?,

    @SerializedName("amount")
    val amount: Double?
)

data class AddOn(
    @SerializedName("amount")
    val amount: Double,

    @SerializedName("gameType")
    val gameType: String
)

data class PrizeCategory(
    @SerializedName("id")
    val id: Long,

    @SerializedName("divident")
    val divident: Long,

    @SerializedName("winners")
    val winners: Long,

    @SerializedName("distributed")
    val distributed: Long,

    @SerializedName("jackpot")
    val jackpot: Long,

    @SerializedName("fixed")
    val fixed: Double,

    @SerializedName("categoryType")
    val categoryType: Long,

    @SerializedName("gameType")
    val gameType: String
)

data class WagerStatistics(
    @SerializedName("columns")
    val columns: Long,

    @SerializedName("wagers")
    val wagers: Long,

    @SerializedName("addOn")
    val addOn: List<Any?>
)
