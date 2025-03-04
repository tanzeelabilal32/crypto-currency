package com.crypto.currency.data.datasource

import com.crypto.currency.data.db.CryptoDao
import com.crypto.currency.domain.model.CryptoDomain
import javax.inject.Inject
import com.crypto.currency.data.mapper.toEntity
import com.crypto.currency.data.mapper.toDomain

class CryptoLocalDataSource @Inject constructor(private val cryptoDao: CryptoDao) {

    suspend fun saveCryptos(cryptos: List<CryptoDomain>) {
        cryptoDao.insertAll(cryptos.map { it.toEntity() })
    }

    suspend fun getCachedCryptos(): List<CryptoDomain> {
        return cryptoDao.getTopCryptos().map { it.toDomain() }
    }
}
