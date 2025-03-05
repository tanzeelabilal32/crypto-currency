package com.crypto.currency.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.crypto.currency.presentation.ui.screen.CryptoDetailScreen
import com.crypto.currency.presentation.ui.screen.CryptoListScreen

sealed class Screen(val route: String) {
    object CryptoList : Screen("crypto_list")
    object CryptoDetail : Screen("crypto_detail/{cryptoId}") {
        fun createRoute(cryptoId: String) = "crypto_detail/$cryptoId"
    }
}

@Composable
fun NavGraph(startDestination: String = Screen.CryptoList.route) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        addCryptoListScreen(navController)
        addCryptoDetailScreen(navController)
    }
}

fun NavGraphBuilder.addCryptoListScreen(navController: NavController) {
    composable(Screen.CryptoList.route) {
        CryptoListScreen(navController)
    }
}

fun NavGraphBuilder.addCryptoDetailScreen(navController: NavController) {
    composable(Screen.CryptoDetail.route) { backStackEntry ->
        val cryptoId = backStackEntry.arguments?.getString("cryptoId") ?: return@composable
        CryptoDetailScreen(navController, cryptoId)
    }
}
