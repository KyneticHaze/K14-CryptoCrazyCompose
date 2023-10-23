package com.example.cryptocrazycompose.model

import com.google.gson.annotations.SerializedName

data class CryptoItem(
    val id: String,
    @SerializedName("logo_url")
    val logoUrl: String,
    val name: String
)