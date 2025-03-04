package com.crypto.currency.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.crypto.currency.domain.model.CryptoDomain

@Entity(tableName = "cryptos")
data class CryptoEntity(
    @PrimaryKey val id: String,
    val symbol: String,
    val name: String,
    val price: Double,
    val marketCap: Long,
    val priceChange: Double
)

// ✅ Convert from CryptoEntity to Domain Model
fun CryptoEntity.toDomain(): CryptoDomain {
    return CryptoDomain(id, symbol, name, price, marketCap, priceChange)
}

// ✅ Convert from Domain Model to CryptoEntity for database storage
fun CryptoDomain.toEntity(): CryptoEntity {
    return CryptoEntity(id, symbol, name, price, marketCap, priceChange)
}
