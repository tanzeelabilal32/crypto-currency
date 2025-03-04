package com.crypto.currency.data.repository

import com.crypto.currency.data.datasource.CryptoLocalDataSource
import com.crypto.currency.data.datasource.CryptoRemoteDataSource
import com.crypto.currency.domain.model.CryptoDomain
import com.crypto.currency.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class CryptoRepository @Inject constructor(
    private val remoteDataSource: CryptoRemoteDataSource,
    private val localDataSource: CryptoLocalDataSource
) {
    fun getTopCryptos(page: Int, pageSize: Int): Flow<Resource<List<CryptoDomain>>> = flow {
        emit(Resource.Loading()) // Show loading state
        try {
            val apiCryptos = remoteDataSource.fetchCryptos(page, pageSize)
            if (!apiCryptos.isNullOrEmpty()) {
                localDataSource.saveCryptos(apiCryptos)  // Cache data
                emit(Resource.Success(apiCryptos)) // Emit success state
            } else {
                emit(Resource.Error("No data found"))
            }
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection. Please check your network."))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to load data. Please try again later."))
        }
    }
}
