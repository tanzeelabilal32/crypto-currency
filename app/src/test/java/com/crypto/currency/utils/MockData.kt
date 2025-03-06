package com.crypto.currency.utils

import com.crypto.currency.domain.model.CryptoDomain

object MockData {
    val cryptoDetail = CryptoDomain(
        id = "bitcoin",
        name = "Bitcoin",
        symbol = "BTC",
        currentPrice = 45000.0,
        marketCap = 850000000000,
        high24h = 46000.0,
        low24h = 44000.0,
        image = "https://example.com/bitcoin.png",
        marketCapRank = 1,
        totalVolume = 2L,
        priceChange24h = 25.0,
        priceChangePercentage24h = 21.0

    )

    val cryptoDetail2 = CryptoDomain(
        id = "ethereum",
        name = "Ethereum",
        symbol = "ETH",
        image = "https://example.com/eth.png",
        currentPrice = 3200.0,
        marketCap = 450000000000,
        marketCapRank = 2,
        totalVolume = 25000000000,
        high24h = 3400.0,
        low24h = 3100.0,
        priceChange24h = -150.0,
        priceChangePercentage24h = -3.8

    )
    val cryptoList = listOf(
        CryptoDomain(
            id = "bitcoin",
            name = "Bitcoin",
            symbol = "BTC",
            image = "https://example.com/btc.png",
            currentPrice = 48000.0,
            marketCap = 900000000000,
            marketCapRank = 1,
            totalVolume = 45000000000,
            high24h = 49500.0,
            low24h = 47000.0,
            priceChange24h = -2000.0,
            priceChangePercentage24h = -4.0
        ),
        CryptoDomain(
            id = "ethereum",
            name = "Ethereum",
            symbol = "ETH",
            image = "https://example.com/eth.png",
            currentPrice = 3200.0,
            marketCap = 450000000000,
            marketCapRank = 2,
            totalVolume = 25000000000,
            high24h = 3400.0,
            low24h = 3100.0,
            priceChange24h = -150.0,
            priceChangePercentage24h = -3.8
        ),
        CryptoDomain(
            id = "ripple",
            name = "XRP",
            symbol = "XRP",
            image = "https://example.com/xrp.png",
            currentPrice = 1.20,
            marketCap = 50000000000,
            marketCapRank = 5,
            totalVolume = 8000000000,
            high24h = 1.35,
            low24h = 1.10,
            priceChange24h = 0.05,
            priceChangePercentage24h = 4.2
        ),
        CryptoDomain(
            id = "cardano",
            name = "Cardano",
            symbol = "ADA",
            image = "https://example.com/ada.png",
            currentPrice = 2.40,
            marketCap = 75000000000,
            marketCapRank = 4,
            totalVolume = 9000000000,
            high24h = 2.50,
            low24h = 2.30,
            priceChange24h = -0.10,
            priceChangePercentage24h = -2.8
        ),
        CryptoDomain(
            id = "dogecoin",
            name = "Dogecoin",
            symbol = "DOGE",
            image = "https://example.com/doge.png",
            currentPrice = 0.35,
            marketCap = 45000000000,
            marketCapRank = 7,
            totalVolume = 6000000000,
            high24h = 0.38,
            low24h = 0.32,
            priceChange24h = 0.02,
            priceChangePercentage24h = 6.1
        )
    )

}
