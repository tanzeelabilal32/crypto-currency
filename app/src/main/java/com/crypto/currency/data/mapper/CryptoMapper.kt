package com.crypto.currency.data.mapper

import com.crypto.currency.domain.model.CryptoDomain
import com.crypto.currency.data.db.CryptoEntity
import com.crypto.currency.domain.model.response.CryptoResponse

fun CryptoDomain.toEntity(): CryptoEntity {
    return CryptoEntity(
        id = this.id,
        name = this.name,
        symbol = this.symbol,
        image = this.image,
        currentPrice = this.currentPrice,
        marketCap = this.marketCap,
        marketCapRank = this.marketCapRank,
        totalVolume = this.totalVolume,
        high24h = this.high24h,
        low24h = this.low24h,
        priceChange24h = this.priceChange24h,
        priceChangePercentage24h = this.priceChangePercentage24h
    )
}

fun CryptoEntity.toDomain(): CryptoDomain {
    return CryptoDomain(
        id = this.id,
        name = this.name,
        symbol = this.symbol,
        image = this.image,
        currentPrice = this.currentPrice,
        marketCap = this.marketCap,
        marketCapRank = this.marketCapRank,
        totalVolume = this.totalVolume,
        high24h = this.high24h,
        low24h = this.low24h,
        priceChange24h = this.priceChange24h,
        priceChangePercentage24h = this.priceChangePercentage24h
    )
}

fun CryptoResponse.toDomain(): CryptoDomain {
    return CryptoDomain(
        id = id,
        name = name,
        symbol = symbol.uppercase(),
        image = image,
        currentPrice = currentPrice,
        marketCap = marketCap,
        marketCapRank = marketCapRank,
        totalVolume = totalVolume,
        high24h = high24h,
        low24h = low24h,
        priceChange24h = priceChange24h,
        priceChangePercentage24h = priceChangePercentage24h
    )
}

fun CryptoResponse.toEntity(): CryptoEntity {
    return CryptoEntity(
        id = id,
        name = name,
        symbol = symbol.uppercase(),
        image = image,
        currentPrice = currentPrice,
        marketCap = marketCap,
        marketCapRank = marketCapRank,
        totalVolume = totalVolume,
        high24h = high24h,
        low24h = low24h,
        priceChange24h = priceChange24h,
        priceChangePercentage24h = priceChangePercentage24h
    )
}