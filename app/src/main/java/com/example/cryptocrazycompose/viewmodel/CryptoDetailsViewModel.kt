package com.example.cryptocrazycompose.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cryptocrazycompose.model.Crypto
import com.example.cryptocrazycompose.repository.CryptoRepository
import com.example.cryptocrazycompose.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoDetailsViewModel @Inject constructor(
    private val repository: CryptoRepository
): ViewModel() {

    suspend fun getCrypto(): Status<Crypto> {
        return repository.getCrypto()
    }
}