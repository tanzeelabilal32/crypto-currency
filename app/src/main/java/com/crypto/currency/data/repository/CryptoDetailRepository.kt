package com.crypto.currency.data.repository

import com.crypto.currency.data.datasource.CryptoLocalDataSource
import com.crypto.currency.domain.model.CryptoDomain
import com.crypto.currency.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CryptoDetailRepository @Inject constructor(
    private val localDataSource: CryptoLocalDataSource
) {
    fun getCryptoById(cryptoId: String): Flow<Resource<CryptoDomain>> = flow {
        emit(Resource.Loading()) // Show loading state
        try {
            val localCrypto = localDataSource.getCryptoById(cryptoId)

            if (localCrypto != null) {
                emit(Resource.Success(localCrypto))
            }

        } catch (e: HttpException) {
            when (e.code()) {
                429 -> emit(Resource.Error("Rate limit exceeded. Try again later.")) // API limit exceeded
                500 -> emit(Resource.Error("Server error. Please try again.")) // Server issue
                else -> emit(Resource.Error("Unknown error: ${e.message()}")) // Other HTTP errors
            }
        } catch (e: IOException) {
            emit(Resource.Error("No internet connection. Please check your network."))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to load data. Please try again later."))
        }
    }
}
