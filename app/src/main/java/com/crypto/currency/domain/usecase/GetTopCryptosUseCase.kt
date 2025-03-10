package com.crypto.currency.domain.usecase

import com.crypto.currency.domain.model.CryptoDomain
import com.crypto.currency.data.repository.CryptoRepository
import com.crypto.currency.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopCryptosUseCase @Inject constructor(
    private val repository: CryptoRepository
) {
    suspend operator fun invoke(page: Int, pageSize: Int): Flow<Resource<List<CryptoDomain>>> {
        return repository.getTopCryptos(page, pageSize)
    }
}
