package com.crypto.currency.domain.usecase

import com.crypto.currency.data.repository.CryptoDetailRepository
import com.crypto.currency.domain.model.CryptoDomain
import com.crypto.currency.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCryptoDetailUseCase @Inject constructor(
    private val repository: CryptoDetailRepository
) {
    operator fun invoke(cryptoId: String): Flow<Resource<CryptoDomain>> {
        return repository.getCryptoById(cryptoId)
    }
}
