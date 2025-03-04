package com.crypto.currency.domain.model.response

import com.squareup.moshi.Json

data class CryptoResponse(
    @Json(name = "id") val id: String,
    @Json(name = "symbol") val symbol: String,
    @Json(name = "name") val name: String,
    @Json(name = "image") val image: String,
    @Json(name = "current_price") val currentPrice: Double,
    @Json(name = "market_cap") val marketCap: Long,
    @Json(name = "market_cap_rank") val marketCapRank: Int,
    @Json(name = "total_volume") val totalVolume: Long,
    @Json(name = "high_24h") val high24h: Double,
    @Json(name = "low_24h") val low24h: Double,
    @Json(name = "price_change_24h") val priceChange24h: Double,
    @Json(name = "price_change_percentage_24h") val priceChangePercentage24h: Double
)