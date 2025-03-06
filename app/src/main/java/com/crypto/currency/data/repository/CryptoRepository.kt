package com.crypto.currency.data.repository

import android.os.Build
import androidx.annotation.RequiresExtension
import com.crypto.currency.data.datasource.CryptoLocalDataSource
import com.crypto.currency.data.datasource.CryptoRemoteDataSource
import com.crypto.currency.domain.model.CryptoDomain
import com.crypto.currency.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CryptoRepository @Inject constructor(
    private val remoteDataSource: CryptoRemoteDataSource,
    private val localDataSource: CryptoLocalDataSource
) {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun getTopCryptos(page: Int, pageSize: Int): Flow<Resource<List<CryptoDomain>>> = flow {
        emit(Resource.Loading()) // Show loading state
        try {
            val apiCryptos = remoteDataSource.fetchCryptos(page, pageSize)
            if (!apiCryptos.isNullOrEmpty()) {
                localDataSource.saveCryptos(apiCryptos)  // Cache data
                emit(Resource.Success(apiCryptos)) // Emit success state
            }
        } catch (e: HttpException) {
            when (e.code()) {
                429 -> emit(Resource.Error("Rate limit exceeded. Try again later.")) // API limit exceeded
                500 -> emit(Resource.Error("Server error. Please try again.")) // Server issue
                else -> emit(Resource.Error("Unknown error: ${e.message()}")) // Other HTTP errors
            }
        }
        catch (e: IOException) {
            emit(Resource.Error("No internet connection. Please check your network."))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to load data. Please try again later."))
        }
    }
}
