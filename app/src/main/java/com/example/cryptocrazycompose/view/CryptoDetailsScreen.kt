package com.example.cryptocrazycompose.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cryptocrazycompose.model.Crypto
import com.example.cryptocrazycompose.ui.theme.Alabaster
import com.example.cryptocrazycompose.ui.theme.BlueMunsell
import com.example.cryptocrazycompose.ui.theme.RichBlack
import com.example.cryptocrazycompose.util.Status
import com.example.cryptocrazycompose.viewmodel.CryptoDetailsViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CryptoDetailsScreen(
    id: String,
    price: String,
    navController: NavController,
    viewModel: CryptoDetailsViewModel = hiltViewModel()
) {

    /*

    Step 1 -> Okay

    var cryptoItem2 by remember {
        mutableStateOf<Status<Crypto>>(Status.Loading())
    }

    LaunchedEffect(key1 = Unit) {
        cryptoItem2 = viewModel.getCrypto()
    }
    */

    //  Step 2 -> Better

    val cryptoItem = produceState<Status<Crypto>>(initialValue = Status.Loading()) {
        value = viewModel.getCrypto()
    }.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Alabaster),
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            when(cryptoItem) {
                is Status.Success -> {

                    val selectedCrypto = cryptoItem.data!![0]

                    Text(
                        text = selectedCrypto.name,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        color = BlueMunsell,
                        textAlign = TextAlign.Center
                    )

                    AsyncImage(
                        model = selectedCrypto.logoUrl,
                        contentDescription = selectedCrypto.name,
                        modifier = Modifier
                            .size(200.dp, 200.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                    )

                    Text(
                        text = price,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        color = RichBlack,
                        textAlign = TextAlign.Center
                    )
                }
                is Status.Error -> {
                    Text(
                        text = cryptoItem.message!!,
                        textAlign = TextAlign.Center,
                    )
                }

                is Status.Loading -> {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}