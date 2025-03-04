package com.crypto.currency.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.crypto.currency.domain.model.CryptoDomain
import com.crypto.currency.presentation.viewmodel.CryptoViewModel
import com.crypto.currency.utils.Resource


@Composable
fun CryptoListScreen(viewModel: CryptoViewModel = hiltViewModel()) {
    val cryptoState by viewModel.cryptoState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Top 5 Crypto Currencies",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        when (cryptoState) {
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is Resource.Success -> {
                val cryptos = cryptoState.data ?: emptyList()
                LazyColumn {
                    items(cryptos) { crypto ->
                        CryptoItem(crypto)
                    }
                }
            }
            is Resource.Error -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = cryptoState.message ?: "Unknown error",
                        color = Color.Red,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp)
                    )
                    Button(onClick = { viewModel.fetchTopCryptos() }) {
                        Text(text = "Retry")
                    }
                }
            }
        }
    }
}


@Composable
fun CryptoItem(crypto: CryptoDomain) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Crypto Icon
        Image(
            painter = rememberAsyncImagePainter(crypto.image),
            contentDescription = crypto.name,
            modifier = Modifier
                .size(40.dp)
                .clip(MaterialTheme.shapes.small)
        )
        Spacer(modifier = Modifier.width(8.dp))

        // Name and price details
        Column(modifier = Modifier.weight(1f)) {
            Text(text = crypto.name, fontWeight = FontWeight.Bold)
            Text(text = "$${crypto.currentPrice}", fontSize = 12.sp, color = Color.Gray)
        }

        // Amount
        Text(
            text = "${crypto.currentPrice} ${crypto.symbol.uppercase()}",
            fontSize = 14.sp,
            textAlign = TextAlign.End
        )
    }
}
