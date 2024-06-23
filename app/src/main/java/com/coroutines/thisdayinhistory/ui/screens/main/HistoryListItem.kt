package com.coroutines.thisdayinhistory.ui.screens.main

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.imageLoader
import coil.memory.MemoryCache
import com.coroutines.data.models.CountryCodeMapping
import com.coroutines.data.models.HistoricalEvent
import com.coroutines.thisdayinhistory.ui.components.PopupShareMenu
import com.coroutines.thisdayinhistory.ui.constants.HISTORY_LIST_ITEM_ROW_TAG
import com.coroutines.thisdayinhistory.ui.constants.HISTORY_LIST_ITEM_TAG
import com.coroutines.thisdayinhistory.ui.utils.darker
import com.coroutines.thisdayinhistory.uimodels.ShareWith
import com.coroutines.thisdayinhistory.uimodels.ShareableHistoryEvent



@Composable
inline fun HistoryListItem(
    historyEvent: HistoricalEvent,
    windowSizeClass: WindowSizeClass,
    crossinline onClick: (HistoricalEvent) -> Unit,
    crossinline onImageClick: (HistoricalEvent) -> Unit,
    crossinline onShare: (ShareableHistoryEvent) -> Unit
) {
    val context = LocalContext.current
    val imageSize = 62.dp
    val keyLine1 = 16.dp

    ElevatedCard (
        Modifier
            .padding(start = keyLine1, end = keyLine1, top =  1.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.background.darker(0.02f),
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            disabledContentColor = MaterialTheme.colorScheme.onBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ))
    {
        Row(
            Modifier
                .testTag(HISTORY_LIST_ITEM_ROW_TAG)
                .padding(start = keyLine1, top = keyLine1 + 17.dp)
        ) {
            HistoryListImage(historyEvent, context, imageSize, onImageClick)

            Column(
                Modifier.padding(
                    start = keyLine1,
                    end = keyLine1
                )
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .testTag(HISTORY_LIST_ITEM_TAG)
                            .height(21.dp),
                        text = historyEvent.year.toString(),
                        textAlign = TextAlign.Start,
                        maxLines = 1,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Text(
                    text = historyEvent.description,
                    maxLines = 15,
                    lineHeight = 20.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(top = 16.dp, end = keyLine1.minus(12.dp))
                        .clickable(enabled = true) {
                            onClick(historyEvent)
                        }
                )

            }

        }
      /*  Row(
            Modifier
             //   .fillMaxSize()
                .padding(end =  1.dp, top = keyLine1 - 11.dp)
                .background(MaterialTheme.colorScheme.background.darker(0.21f)),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val countyFlagImageModifier = Modifier
                .padding(start = 10.dp)
                .width(26.dp)
                .height(18.dp)
            historyEvent.countryCodeMap?.forEach {
                CountryFlagImage(countyFlagImageModifier, it, historyEvent, context)
            }

            Box(
                Modifier.padding(start = 7.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(onClick = {
                    val imageLoader = context.imageLoader
                    val bitmap =
                        imageLoader.memoryCache?.get(MemoryCache.Key(historyEvent.imageUrl))?.bitmap
                    sharedHistoryEvent = ShareableHistoryEvent(
                        historyEvent.description,
                        bitmap,
                        historyEvent.shortTitle
                    )
                    showMenu = true
                }) {
                    Icon(Icons.Filled.Share, contentDescription = "Localized description")
                }
                PopupShareMenu(
                    onClickCallback = { index ->
                        when (index) {
                            0 -> onShare(sharedHistoryEvent.copy(shareWith = ShareWith.WHATSAPP))
                            1 -> onShare(sharedHistoryEvent.copy(shareWith = ShareWith.TWITTER))
                            else -> onShare(sharedHistoryEvent.copy(shareWith = ShareWith.OTHER))
                        }
                    },
                    showMenu = showMenu,
                    onDismiss = { showMenu = false })
            }
        }*/
    }

}

@Composable
private fun Sharer(
    context: Context,
    historyEvent: HistoricalEvent,
    sharedHistoryEvent: ShareableHistoryEvent,
    showMenu: Boolean,
    onShare: (ShareableHistoryEvent) -> Unit,
) {
    // var bitmap1 = bitmap
    var sharedHistoryEvent1 = sharedHistoryEvent
    var showMenu1 = showMenu
    Box {
        IconButton(onClick = {
            val imageLoader = context.imageLoader
            val bitmap1 =
                imageLoader.memoryCache?.get(MemoryCache.Key(historyEvent.imageUrl))?.bitmap
            sharedHistoryEvent1 = ShareableHistoryEvent(
                historyEvent.description,
                bitmap1,
                historyEvent.shortTitle
            )
            showMenu1 = true
        }) {
            Icon(Icons.Filled.Share, contentDescription = "Localized description")
        }
        PopupShareMenu(
            onClickCallback = { index ->
                when (index) {
                    0 -> onShare(sharedHistoryEvent1.copy(shareWith = ShareWith.WHATSAPP))
                    1 -> onShare(sharedHistoryEvent1.copy(shareWith = ShareWith.TWITTER))
                    else -> onShare(sharedHistoryEvent1.copy(shareWith = ShareWith.OTHER))
                }
            },
            showMenu = showMenu1,
            onDismiss = { showMenu1 = false })
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryListItemAltPreview() {
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



