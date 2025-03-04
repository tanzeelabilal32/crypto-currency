package com.crypto.currency.data.repository

import com.crypto.currency.data.api.CryptoApi
import com.crypto.currency.data.datasource.CryptoLocalDataSource
import com.crypto.currency.data.datasource.CryptoRemoteDataSource
import com.crypto.currency.data.db.CryptoDao
import com.crypto.currency.data.mapper.toDomain
import com.crypto.currency.data.mapper.toEntity
import com.crypto.currency.domain.model.CryptoDomain
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CryptoRepository @Inject constructor(
    private val remoteDataSource: CryptoRemoteDataSource,
    private val localDataSource: CryptoLocalDataSource
) {
    suspend fun getTopCryptos(): List<CryptoDomain>? = withContext(Dispatchers.IO) {
        val cryptos = remoteDataSource.fetchCryptos()

        if (!cryptos.isNullOrEmpty()) {
            localDataSource.saveCryptos(cryptos)

            return@withContext cryptos
        } else {

            return@withContext localDataSource.getCachedCryptos()
        }
    }
}
