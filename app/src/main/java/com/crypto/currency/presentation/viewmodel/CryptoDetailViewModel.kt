package com.crypto.currency.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crypto.currency.domain.model.CryptoDomain
import com.crypto.currency.domain.usecase.GetCryptoDetailUseCase
import com.crypto.currency.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val getCryptoDetailUseCase: GetCryptoDetailUseCase
) : ViewModel() {

    private val _cryptoDetailState = MutableStateFlow<Resource<CryptoDomain>>(Resource.Loading())
    val cryptoDetailState: StateFlow<Resource<CryptoDomain>> get() = _cryptoDetailState

    fun fetchCryptoDetail(cryptoId: String) {
        viewModelScope.launch {
            getCryptoDetailUseCase.invoke(cryptoId).collectLatest {
                _cryptoDetailState.value = it
            }
        }
    }
}


