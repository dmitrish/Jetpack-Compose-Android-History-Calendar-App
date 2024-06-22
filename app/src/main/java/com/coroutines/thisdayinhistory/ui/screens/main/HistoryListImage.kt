package com.coroutines.thisdayinhistory.ui.screens.main

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Precision
import com.coroutines.data.models.HistoricalEvent

@Composable
inline fun HistoryListImage(
    historyEvent: HistoricalEvent,
    context: Context,
    imageSize: Dp,
    crossinline onImageClick: (HistoricalEvent) -> Unit,
) {
    Image(
        rememberAsyncImagePainter(
            remember(historyEvent.imageUrl) {
                ImageRequest.Builder(context)
                    .data(
                        data = historyEvent.imageUrl
                    )
                    .diskCacheKey(historyEvent.imageUrl)
                    .precision(Precision.EXACT)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .memoryCacheKey(historyEvent.imageUrl)
                    .build()
            }
        ),
        contentDescription = "image",
        alignment = Alignment.TopCenter,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(imageSize)
            .clip(MaterialTheme.shapes.small)
            .clickable {
                onImageClick(historyEvent)
            }
    )
}