package com.crypto.currency.presentation.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.crypto.currency.navigation.NavGraph
import com.crypto.currency.presentation.ui.theme.CryptoCurrencyRatesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoCurrencyRatesTheme {
                NavGraph()
            }
        }
    }
}
