package com.crypto.currency.domain.model.response

import com.squareup.moshi.Json

data class CryptoResponse(
    @Json(name = "id") val id: String,
    @Json(name = "symbol") val symbol: String,
    @Json(name = "name") val name: String,
    @Json(name = "current_price") val price: Double,
    @Json(name = "market_cap") val marketCap: Long,
    @Json(name = "price_change_percentage_24h") val priceChange: Double
)