package com.example.cryptocrazycompose.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocrazycompose.model.CryptoListItem
import com.example.cryptocrazycompose.repository.CryptoRepository
import com.example.cryptocrazycompose.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val repository: CryptoRepository
): ViewModel() {

    var cryptoList = mutableStateOf<List<CryptoListItem>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var initialCryptoList = listOf<CryptoListItem>() // geçici bir liste bu
    private var isSearchStarting = true

    init {
        loadCryptos()
    }

    fun searchCryptoList(query: String) {
        val listToSearch = if (isSearchStarting) {
            // arama başladıysa çekilen verinin değerini ver
            cryptoList.value
        } else {
            // arama başlamadıysa geçici listeyi yapıştır
            initialCryptoList
        }

        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                // arama yapılıp silinmişsse veya arama yapılmamışsa boş kalmışsa crypto listesine geçici crypto listesini atarız
                cryptoList.value = initialCryptoList
                isSearchStarting = true
                return@launch
            }

            // sonuçları aramanın başlayıp başlamadığına göre bir filtreye sokuyoruz.
            // Arama başladıysa ve yazılan değerin harfi currency'de BARINIYORSA
            val results = listToSearch.filter { cryptoListItem ->
                cryptoListItem.currency.contains(query.trim(), true)
                // ignore case -> büyük küçüğe duyarlı olmadan arama yapar
                // trim ile boşlukları kaldırır
            }

            if (isSearchStarting) {
                // arama başladıysa geçici listeye çektiğimiz veri listesinin değerini ata
                initialCryptoList = cryptoList.value
                // arama bitti
                isSearchStarting = false
            }
            // çekilen veri listesine sonuçları yapıştır.
            cryptoList.value = results
        }
    }


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

                else -> {}
            }
        }
    }
}