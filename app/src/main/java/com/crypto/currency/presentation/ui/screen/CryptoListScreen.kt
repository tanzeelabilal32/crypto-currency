package com.crypto.currency.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.crypto.currency.domain.model.CryptoDomain
import com.crypto.currency.presentation.viewmodel.CryptoViewModel
import com.crypto.currency.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun CryptoListScreen(navController: NavController, viewModel: CryptoViewModel = hiltViewModel()) {
    val cryptoState by viewModel.cryptoState.collectAsState()
    val coroutineScope = rememberCoroutineScope() // Needed for smooth scrolling
    val scrollState = rememberLazyListState()

    val accountBalance = remember { mutableStateOf("$11,542.21") } // Dummy value for now

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Top 5 Crypto Currencies",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Account Value",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = accountBalance.value,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Buttons (Send & Receive)
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { /* Handle Send */ },
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "↑ Send", color = Color.White)
            }
            Button(
                onClick = { /* Handle Receive */ },
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "↓ Receive", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxSize()) {
            val cryptos = cryptoState.data ?: emptyList()
            when (cryptoState) {
                is Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is Resource.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 60.dp) // Prevent overlap with the button
                    ) {
                        items(cryptos) { crypto ->
                            CryptoItem(crypto){
                                navController.navigate("crypto_detail/${crypto.id}")
                            }
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

            Button(
                onClick = {
                    viewModel.loadNextPage()
                    //TODO: Fix the scroll
                    coroutineScope.launch {
                        delay(300) // Allow time for new items to be added
                        scrollState.animateScrollToItem(cryptos.size.minus(1)) // Scroll to last item
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Load More")
            }
        }
    }
}


@Composable
fun CryptoItem(crypto: CryptoDomain,onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
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
