package com.crypto.currency.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.crypto.currency.presentation.viewmodel.CryptoDetailViewModel
import com.crypto.currency.utils.Resource

@Composable
fun CryptoDetailScreen(
    navController: NavController,
    cryptoId: String,
    viewModel: CryptoDetailViewModel = hiltViewModel()
) {
    val cryptoDetailState by viewModel.cryptoDetailState.collectAsState()

    LaunchedEffect(cryptoId) {
        viewModel.fetchCryptoDetail(cryptoId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (cryptoDetailState) {
            is Resource.Loading -> CircularProgressIndicator(
                modifier = Modifier.align(
                    Alignment.Center
                )
            )

            is Resource.Error -> Text(
                text = cryptoDetailState.message ?: "Unknown Error",
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )

            is Resource.Success -> {
                val crypto = cryptoDetailState.data!!
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)) {
                    Text(text = crypto.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(text = "$${crypto.currentPrice}", fontSize = 32.sp, color = Color.Green)

                    AsyncImage(
                        model = crypto.image,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Text(text = "Market Cap: $${crypto.marketCap}", fontSize = 18.sp)
                    Text(text = "High 24h: $${crypto.high24h}", fontSize = 18.sp)
                    Text(text = "Low 24h: $${crypto.low24h}", fontSize = 18.sp)

                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Back")
                    }
                }
            }
        }
    }
}

