package com.coroutines.thisdayinhistory.ui.screens.main

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.coroutines.data.models.CountryCodeMapping
import com.coroutines.data.models.HistoricalEvent
import com.coroutines.thisdayinhistory.components.FLAG_COUNTRY_RESOURCE_HTTP_PREFIX
import com.coroutines.thisdayinhistory.components.FLAG_COUNTRY_RESOURCE_NAME_PREFIX
import com.coroutines.thisdayinhistory.components.FLAG_GERMANY_1933_1945
import com.coroutines.thisdayinhistory.components.FLAG_ITALY_1861_1946
import com.coroutines.thisdayinhistory.ui.utils.getResourceId


@Composable
fun CountryFlagImage(
    modifier: Modifier,
    it: CountryCodeMapping,
    historyEvent: HistoricalEvent,
    context: Context,
) {

    val imageUrl = adjustFlagForYears(historyEvent, it) ?: it.url

    Image(
        rememberAsyncImagePainter(
            remember(imageUrl) {
                ImageRequest.Builder(context)
                    .data(
                        data = when (imageUrl) {
                            null -> getResourceId("$FLAG_COUNTRY_RESOURCE_NAME_PREFIX${it.alpha2?.lowercase()}")
                            else -> if (imageUrl.startsWith(FLAG_COUNTRY_RESOURCE_HTTP_PREFIX)) imageUrl else getResourceId(
                                imageUrl.lowercase()
                            )
                        }
                    )
                    .diskCacheKey(imageUrl)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .memoryCacheKey(imageUrl)
                    .build()
            },
            onLoading = {
                println("imageUrl onLoading: $imageUrl")
            },
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier

    )
}

private fun adjustFlagForYears(
    historyEvent: HistoricalEvent,
    it: CountryCodeMapping
): String? {
    val year = historyEvent.year?.toInt()
    return if (year in 1861..1946 && it.alpha2 == "IT") FLAG_ITALY_1861_1946
    else if (year in 1933..1945 && it.alpha2 == "DE") FLAG_GERMANY_1933_1945
    else null
}