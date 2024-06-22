package com.coroutines.thisdayinhistory.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.coroutines.data.models.CountryCodeMapping
import com.coroutines.data.models.HistoricalEvent
import com.coroutines.thisdayinhistory.ui.components.ImageLoadingAnimation
import com.coroutines.thisdayinhistory.ui.constants.HISTORY_LIST_ITEM_ROW_TAG
import com.coroutines.thisdayinhistory.ui.constants.HISTORY_LIST_ITEM_TAG
import com.coroutines.thisdayinhistory.ui.utils.ResourceLookUpByReflection.getResourceId


@Composable
fun HistoryListItemX(
    historyEvent: HistoricalEvent,
    onClick: (String) -> Unit,
    onShare: (HistoricalEvent) -> Unit,
) {
    val keyLine1 = 16.dp

    Row(
        Modifier
            .testTag(HISTORY_LIST_ITEM_ROW_TAG)
            .fillMaxSize()
            .padding(start = keyLine1, top = keyLine1 + 17.dp)
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .memoryCacheKey(historyEvent.imageUrl)
                .diskCacheKey(historyEvent.imageUrl)
                .data(historyEvent.imageUrl)
                .crossfade(true)
                .build(),

            contentDescription = "image",
            alignment = Alignment.TopCenter,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(62.dp)
                .fillMaxSize()
                .clip(MaterialTheme.shapes.small)
                .clickable {
                    onShare(historyEvent)
                }

        ) {
            if (painter.state is AsyncImagePainter.State.Loading) {
                ImageLoadingAnimation()
            } else {
                SubcomposeAsyncImageContent()
            }
        }
        Column(Modifier.padding(start = keyLine1, end = keyLine1)) {
            Row ( Modifier
                .fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .testTag(HISTORY_LIST_ITEM_TAG)
                        .height(21.dp),
                    text = historyEvent.year.toString(),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleLarge
                )
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(end = keyLine1 + 5.dp, top = keyLine1 - 11.dp),
                    horizontalArrangement = Arrangement.End
                ) {

                    historyEvent.countryCodeMap?.forEach {
                        var imageUrl = it.url

                        if (historyEvent.year?.toInt() in 1861..1946) {
                            if (it.alpha2 == "IT") {
                                imageUrl = "italy_1861_1946"
                            }
                            if (historyEvent.year?.toInt() in 1933..1945) {
                                if (it.alpha2 == "DE") {
                                    imageUrl = "nazi_germany"
                                }
                            }
                        }
                        Image(
                            painter = rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current)
                                //.placeholder(R.drawable.country_at)
                                .data(
                                    data = when (imageUrl) {
                                        null -> getResourceId("country_${it.alpha2?.lowercase()}")
                                        else -> if (imageUrl.startsWith("http")) imageUrl else getResourceId(
                                            imageUrl.lowercase()
                                        )
                                    }
                                ).apply(block = fun ImageRequest.Builder.() {
                                    crossfade(true)
                                }).build()
                            ),

                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .width(26.dp)
                                .height(18.dp)
                        )
                    }

                }
            }

            Text(
                text = historyEvent.description,
                maxLines = 15,
                lineHeight = 20.sp,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(top = 16.dp, end = keyLine1.minus(4.dp))
                    .clickable(enabled = true) {
                        onClick("S")
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryListItemPreview() {
    Column(Modifier.fillMaxSize()) {
        val historicalEvent = HistoricalEvent(
            description = "this is an Italy sample",
            extract = "this is a test",
            shortTitle = "this is short title",
            countryCodeMappings = buildList(1) {
                add(CountryCodeMapping("Italy"))
            },
            year = "1865"
        )
        val historicalEvent2 = HistoricalEvent(
            description = "this is a Germany sample",
            extract = "this is a test",
            shortTitle = "this is short title",
            countryCodeMappings = buildList(1) {
                add(CountryCodeMapping("Germany"))
            },
            year = "1940"
        )

        HistoryListItemX(
            historyEvent = historicalEvent,
            onClick = { },
            onShare = {}
        )
        HistoryListItemX(
            historyEvent = historicalEvent2,
            onClick = { },
            onShare = {}
        )
    }
}