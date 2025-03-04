package com.crypto.currency.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.crypto.currency.domain.model.CryptoDomain

@Entity(tableName = "cryptos")
data class CryptoEntity(
    @PrimaryKey val id: String,
    val name: String,
    val symbol: String,
    val image: String,
    val currentPrice: Double,
    val marketCap: Long,
    val marketCapRank: Int,
    val totalVolume: Long,
    val high24h: Double,
    val low24h: Double,
    val priceChange24h: Double,
    val priceChangePercentage24h: Double
)
