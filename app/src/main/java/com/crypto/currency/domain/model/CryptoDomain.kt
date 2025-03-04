package com.crypto.currency.domain.model

data class CryptoDomain(
    val id: String,
    val symbol: String,
    val name: String,
    val price: Double,
    val marketCap: Long,
    val priceChange: Double
)
