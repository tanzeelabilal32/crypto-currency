package com.crypto.currency.data.datasource

import com.crypto.currency.data.api.CryptoApi
import com.crypto.currency.data.mapper.toDomain
import com.crypto.currency.data.mapper.toEntity
import com.crypto.currency.domain.model.CryptoDomain
import com.crypto.currency.domain.model.response.CryptoResponse
import javax.inject.Inject

class CryptoRemoteDataSource @Inject constructor(
    private val cryptoApi: CryptoApi
) {
    suspend fun fetchCryptos(page:Int, pageSize:Int): List<CryptoDomain> {
        val response = cryptoApi.getTopCryptos(page = page, perPage = pageSize)
        if (response.isSuccessful && response.body() != null) {
            val cryptos: List<CryptoDomain> = response.body()!!.map {
                it.toDomain()
            }

            return cryptos
        }
        else{
            return emptyList()
        }
    }
}
