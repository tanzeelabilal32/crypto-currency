package com.crypto.currency.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.crypto.currency.domain.model.CryptoDomain
import com.crypto.currency.presentation.viewmodel.CryptoDetailViewModel
import com.crypto.currency.utils.Resource
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color.Companion.LightGray
import com.crypto.currency.presentation.ui.component.CryptoPriceChart
import com.crypto.currency.presentation.ui.theme.CryptoTypography


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
                cryptoDetailState.data?.let { crypto ->
                    CryptoDetailContent(navController, crypto)
                }
            }
        }
    }
}


@Composable
fun CryptoDetailContent(navController: NavController, crypto: CryptoDomain) {
    val priceHistory = listOf(
        44000f, 44800f, 44300f, 45500f, 45200f, 46100f, 45600f, 47200f, 48500f,
        47800f, 47300f, 46900f, 48000f, 47200f, 46500f, 47600f, 48200f, 46800f,
        46500f, 46900f, 46200f
    )
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {

            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                Text(
                    text = crypto.name,
                    style = CryptoTypography.displayLarge
                )
                AsyncImage(
                    model = crypto.image,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Current Price & Change
            Text(
                text = "$${crypto.currentPrice}",
                style = CryptoTypography.displayLarge
            )
            Text(
                text = "${crypto.priceChangePercentage24h}% ${crypto.priceChange24h}",
                style = CryptoTypography.bodySmall,
                color = if (crypto.priceChange24h > 0) Color.Green else Color.Red
            )

            Spacer(modifier = Modifier.height(16.dp))

            //dummy Graph
            CryptoPriceChart(
                priceData = priceHistory,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Account Balance Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Account Value",
                        style = CryptoTypography.bodySmall,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${crypto.currentPrice} ${crypto.symbol.uppercase()}",
                        style = CryptoTypography.displayLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$${crypto.symbol}",
                        style = CryptoTypography.bodySmall,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Recent Transactions List
            Text(text = "Latest Activities", fontSize = 18.sp, fontWeight = FontWeight.Bold, style = CryptoTypography.displayMedium)

            Column {
                listOf(
                    Triple("Sent", "0.017 BTC", "$725.00"),
                    Triple("Sent", "0.028 BTC", "$125.00"),
                    Triple("Received", "0.027 BTC", "$510.00")
                ).forEach { (type, amount, value) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = type, fontSize = 16.sp, fontWeight = FontWeight.Medium, style = CryptoTypography.displayMedium)
                            Text(text = "Jan 4, 2024", fontSize = 14.sp, color = Color.Gray, style = CryptoTypography.bodySmall)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = amount, fontSize = 16.sp, fontWeight = FontWeight.Medium, style = CryptoTypography.displayMedium)
                            Text(text = value, fontSize = 14.sp, color = Color.Gray, style = CryptoTypography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}
