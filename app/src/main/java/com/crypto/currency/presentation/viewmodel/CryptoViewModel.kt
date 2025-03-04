package com.crypto.currency.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import com.crypto.currency.domain.usecase.GetTopCryptosUseCase
import javax.inject.Inject
import kotlinx.coroutines.launch
import com.crypto.currency.domain.model.CryptoDomain


@HiltViewModel
class CryptoViewModel @Inject constructor(private val getTopCryptosUseCase: GetTopCryptosUseCase) : ViewModel() {
    private val _cryptos = MutableLiveData<List<CryptoDomain>>()
    val cryptos: LiveData<List<CryptoDomain>> get() = _cryptos

    init{
        fetchTopCryptos()
    }

    private fun fetchTopCryptos() {
        viewModelScope.launch {
            _cryptos.value = getTopCryptosUseCase.invoke()
        }
    }
}