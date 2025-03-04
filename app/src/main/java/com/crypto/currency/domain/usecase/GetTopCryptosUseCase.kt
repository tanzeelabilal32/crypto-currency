package com.crypto.currency.domain.usecase

import com.crypto.currency.data.repository.CryptoRepository
import com.crypto.currency.domain.model.CryptoDomain
import javax.inject.Inject

class GetTopCryptosUseCase @Inject constructor(private val repository: CryptoRepository) {
    suspend operator fun invoke(): List<CryptoDomain> = repository.getTopCryptos()
}