package com.example.cryptocrazycompose.repository

import com.example.cryptocrazycompose.model.Crypto
import com.example.cryptocrazycompose.model.CryptoList
import com.example.cryptocrazycompose.service.CryptoAPI
import com.example.cryptocrazycompose.util.Status
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class CryptoRepository @Inject constructor(
    private val api: CryptoAPI
) {
    suspend fun getCryptoList(): Status<CryptoList> {
        val response = try {
            api.getCryptoList()
        } catch (e: Exception) {
            return Status.Error("Error")
        }
        return Status.Success(response)
    }

    suspend fun getCrypto(): Status<Crypto> {
        val response = try {
            api.getCrypto()
        } catch (e: Exception) {
            return Status.Error("Error")
        }
        return Status.Success(response)
    }
}