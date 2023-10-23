package com.example.cryptocrazycompose.util

object Routes {

    // Routes
    const val cryptoListScreenId = "crypto_list_screen"
    const val cryptoDetailsScreenId = "crypto_details_screen/{${Arguments.cryptoId}}/{${Arguments.cryptoPrice}}"

    fun cryptoNavigate(currency: String, price: String): String {
        return "crypto_details_screen/${currency}/${price}"
    }
}