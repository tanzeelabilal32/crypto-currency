package com.crypto.currency.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crypto.currency.domain.model.CryptoDomain
import com.crypto.currency.domain.usecase.GetTopCryptosUseCase
import com.crypto.currency.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(
    private val getTopCryptosUseCase: GetTopCryptosUseCase
) : ViewModel() {

    private val _cryptoState = MutableStateFlow<Resource<List<CryptoDomain>>>(Resource.Loading())
    val cryptoState: StateFlow<Resource<List<CryptoDomain>>> get() = _cryptoState

    init {
        fetchTopCryptos()
    }

    fun fetchTopCryptos() {
        viewModelScope.launch {
           var t =  getTopCryptosUseCase.invoke().collectLatest { result ->
                _cryptoState.value = result
            }
        }
    }
}
