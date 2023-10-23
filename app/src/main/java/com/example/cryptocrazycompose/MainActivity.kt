package com.example.cryptocrazycompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cryptocrazycompose.ui.theme.CryptoCrazyComposeTheme
import com.example.cryptocrazycompose.util.Arguments
import com.example.cryptocrazycompose.view.CryptoDetailsScreen
import com.example.cryptocrazycompose.view.CryptoListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoCrazyComposeTheme {

                val navController = rememberNavController()
                val cryptoListScreenId = "crypto_list_screen"
                val cryptoDetailsScreenId = "crypto_details_screen/{${Arguments.cryptoId}}/{${Arguments.cryptoPrice}}"

                NavHost(
                    navController = navController,
                    startDestination = cryptoListScreenId) {

                    composable(cryptoListScreenId) {
                        // CryptoListScreen
                        CryptoListScreen(navController = navController)
                    }

                    composable(cryptoDetailsScreenId, arguments = listOf(
                        navArgument(Arguments.cryptoId) {
                            NavType.StringType
                        },
                        navArgument(Arguments.cryptoPrice) {
                            NavType.StringType
                        }
                    )) {
                        // CryptoDetailsScreen

                        // Arguments Pull
                        val cryptoId = remember { it.arguments?.getString(Arguments.cryptoId) }
                        val cryptoPrice = remember { it.arguments?.getString(Arguments.cryptoPrice) }

                        CryptoDetailsScreen(id = cryptoId ?: "", price = cryptoPrice ?: "", navController = navController)

                    }
                }
            }
        }
    }
}