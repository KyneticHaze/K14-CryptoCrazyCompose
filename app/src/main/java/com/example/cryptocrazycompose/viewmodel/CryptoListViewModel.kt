package com.example.cryptocrazycompose.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocrazycompose.model.CryptoListItem
import com.example.cryptocrazycompose.repository.CryptoRepository
import com.example.cryptocrazycompose.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val repository: CryptoRepository
): ViewModel() {

    var cryptoList = mutableStateOf<List<CryptoListItem>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)


    fun loadCryptos() {

        viewModelScope.launch {
            // Yükleme çubuğunun görünüm değeri doğru
            isLoading.value = true
            // veri sonucu değişkeni oluşturuldu
            val result = repository.getCryptoList()

            when(result) {
                // sonuç başarılı
                is Status.Success -> {
                    // sonuçtan gelen veri liste şeklinde geliyor ve onu mapIndexed ile elemana çeviriyoruz.
                    val cryptoItems = result.data!!.mapIndexed { index, cryptoListItem ->
                        CryptoListItem(cryptoListItem.currency,cryptoListItem.price)
                    }
                    // hata mesajı boş
                    errorMessage.value = ""
                    // loading görünümü yok
                    isLoading.value = false
                    // crypto listesine ekleme yapıldı
                    cryptoList.value += cryptoItems
                }

                is Status.Error -> {
                    // hata mesajı repoda verildi
                    errorMessage.value = result.message!!
                    // loading görünümü yok
                    isLoading.value = false
                }
            }
        }
    }
}