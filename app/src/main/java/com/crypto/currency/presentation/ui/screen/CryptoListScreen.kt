package com.crypto.currency.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.crypto.currency.presentation.viewmodel.CryptoViewModel

@Composable
fun CryptoListScreen(viewModel: CryptoViewModel = hiltViewModel()) {
    val cryptos by viewModel.cryptos.observeAsState(emptyList())

    LazyColumn {
        items(cryptos) { crypto ->
            Text(text = "${crypto.name}: ${crypto.currentPrice}")
        }
    }
}