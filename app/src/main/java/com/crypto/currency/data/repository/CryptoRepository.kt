package com.crypto.currency.data.repository

import com.crypto.currency.data.api.CryptoApi
import com.crypto.currency.data.db.CryptoDao
import com.crypto.currency.data.mapper.toDomain
import com.crypto.currency.data.mapper.toEntity
import com.crypto.currency.domain.model.CryptoDomain
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CryptoRepository @Inject constructor(
    private val api: CryptoApi,
    private val dao: CryptoDao
) {
    suspend fun getTopCryptos(): List<CryptoDomain> = withContext(Dispatchers.IO) {
        try {
            val response = api.getTopCryptos()
            if (response.isSuccessful && response.body() != null) {
                val cryptos: List<CryptoDomain> = response.body()!!.map {
                    it.toDomain()
                }

                // Convert Domain Model to Database Model before inserting
                dao.insertAll(cryptos.map { it.toEntity() })

                return@withContext cryptos
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //  Convert Database Model to Domain Model before returning
        return@withContext dao.getTopCryptos().map { it.toDomain() }
    }
}
