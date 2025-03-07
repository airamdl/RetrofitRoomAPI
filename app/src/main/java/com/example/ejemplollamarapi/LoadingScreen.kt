package com.example.ejemplollamarapi

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest


@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 40.dp),
            text = "Cargadno...",
            fontSize = TextUnit(8f, TextUnitType.Em),
            fontWeight = FontWeight.Bold
        )
            LoadingGif()

    }
}


@Composable
fun LoadingGif() {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components{
            if (Build.VERSION.SDK_INT >= 36) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest
                .Builder(context)
                .data(data = R.drawable.loading)
                .apply(
                    block = {
                        size(400)
                    }
                ).build(),
                    imageLoader = imageLoader
        ),
        contentDescription = null,
        modifier = Modifier.fillMaxWidth()

    )
}