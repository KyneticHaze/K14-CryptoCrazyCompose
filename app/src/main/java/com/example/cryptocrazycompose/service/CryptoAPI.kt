package com.example.cryptocrazycompose.service

import com.example.cryptocrazycompose.model.Crypto
import com.example.cryptocrazycompose.model.CryptoList
import retrofit2.http.GET

interface CryptoAPI {

    @GET("cryptolist.json")
    suspend fun getCryptoList(): CryptoList

    @GET("crypto.json")
    suspend fun getCrypto(): Crypto
}