package com.coroutines.thisdayinhistory.ui.components

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.coroutines.thisdayinhistory.components.MAX_COUNT_CHARS_BEFORE_PAD_UP
import com.coroutines.thisdayinhistory.components.MIN_COUNT_CHARS_BEFORE_PAD_UP
import com.coroutines.thisdayinhistory.ui.configurations.StyleConfiguration
import com.coroutines.thisdayinhistory.ui.constants.DETAIL_BODY_TEXT_TAG
import com.coroutines.thisdayinhistory.ui.constants.DETAIL_HEADER_TEXT_TAG
import com.coroutines.thisdayinhistory.ui.utils.breakUpWithNewLines
import com.coroutines.thisdayinhistory.ui.utils.darker
import com.coroutines.thisdayinhistory.ui.utils.lighter
import com.coroutines.thisdayinhistory.ui.utils.padUp
import com.coroutines.thisdayinhistory.ui.utils.stripHtml

@OptIn(ExperimentalFoundationApi::class)
@Suppress("MagicNumber")
@Composable
fun CollapsingEffectScreen(
    modifier: Modifier,
    styleConfiguration: StyleConfiguration,
    adjustedColor: Color,
    pagerState: PagerState,
    imageUrl :String,
    loadingUrl: String,
    title: String,
    bodyText: String,
) {
    val lazyListState = rememberLazyListState()
    var scrolledY = 0f
    var previousOffset = 0
    LazyColumn(
        Modifier.fillMaxSize(),
        lazyListState,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            val context = LocalContext.current
            var isLoading by remember { mutableStateOf(true) }
            Image(
                rememberAsyncImagePainter(
                    remember(imageUrl) {
                        ImageRequest.Builder(context)
                            .data(imageUrl)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .memoryCacheKey(imageUrl)
                            .build()
                    },
                    onLoading = {
                        println("imageUrl onLoading: $imageUrl")
                    },
                    onSuccess = {
                        println("imageUrl onSuccess: $imageUrl")
                        isLoading = false
                    }
                ),
                contentDescription = "loaded",
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(1.dp)
                    .fillMaxWidth()
                    .clip(CircleShape)
            )

            Crossfade(targetState = isLoading, label = "") {
                when (it) {
                    true -> {
                        Image(
                            rememberAsyncImagePainter(
                                remember(loadingUrl) {
                                    ImageRequest.Builder(context)
                                        .data(loadingUrl)
                                        .memoryCachePolicy(CachePolicy.ENABLED)
                                        .memoryCacheKey(loadingUrl)
                                        .build()
                                }
                            ),
                            contentDescription = "loaded",
                            alignment = Alignment.TopCenter,
                            contentScale = ContentScale.Crop,
                            modifier = modifier
                                .graphicsLayer {
                                    scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                                    translationY = scrolledY * 0.5f
                                    scaleX = 1 / ((scrolledY * 0.01f) + 1f)
                                    scaleY = 1 / ((scrolledY * 0.01f) + 1f)
                                    previousOffset = lazyListState.firstVisibleItemScrollOffset
                                }
                                .size(DETAIL_IMAGE_SIZE.dp)
                                .clip(CircleShape)
                                .blur(
                                    radiusX = 4.dp,
                                    radiusY = 4.dp,
                                    edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(2.dp))
                                )
                        )
                    }

                    false -> {
                        Image(
                            rememberAsyncImagePainter(
                                remember(imageUrl) {
                                    ImageRequest.Builder(context)
                                        .memoryCachePolicy(CachePolicy.ENABLED)
                                        .data(imageUrl)
                                        .memoryCacheKey(imageUrl)
                                        .build()
                                },
                            ),
                            contentDescription = "loaded",
                            alignment = Alignment.TopCenter,
                            contentScale = ContentScale.Crop,
                            modifier = modifier
                                .graphicsLayer {
                                    scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                                    translationY = scrolledY * 0.5f
                                    scaleX = 1 / ((scrolledY * 0.01f) + 1f)
                                    scaleY = 1 / ((scrolledY * 0.01f) + 1f)
                                    previousOffset = lazyListState.firstVisibleItemScrollOffset
                                }
                                .size(DETAIL_IMAGE_SIZE.dp)
                                .clip(CircleShape)
                        )
                    }
                }
            }
        }

        if (pagerState.pageCount > 1) {
            item {
                Row(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(bottom = 8.dp, top = 30.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val color =
                            if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(8.dp)
                        )
                    }
                }
            }
        }
        stickyHeader {
            Text(
                text = title.stripHtml(),
                Modifier
                    .testTag(DETAIL_HEADER_TEXT_TAG)
                    .background(adjustedColor)
                    .defaultMinSize(minHeight = 70.dp)
                    .fillMaxWidth()
                    .padding(20.dp),
                maxLines = 4,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        }

        items(1)

        {
            Text(
                text = bodyText
                    .stripHtml()
                    .breakUpWithNewLines()
                    .padUp(),
                Modifier
                    .testTag(DETAIL_BODY_TEXT_TAG)
                    .fillMaxWidth()
                    .padding(35.dp),
                color = if (styleConfiguration.isDark) adjustedColor.lighter(LIGHTER_OR_DARKER_TEXT_FACTOR)
                else adjustedColor.darker(
                    LIGHTER_OR_DARKER_TEXT_FACTOR
                ),
                lineHeight = styleConfiguration.lineHeight,
                style = styleConfiguration.bodyTypography

            )
            Log.i("detail", bodyText.stripHtml())
            if (bodyText.length in MIN_COUNT_CHARS_BEFORE_PAD_UP..MAX_COUNT_CHARS_BEFORE_PAD_UP) {
                val configuration = LocalConfiguration.current
                val screenHeight = configuration.screenHeightDp.dp
                Log.d("detail screen height", screenHeight.value.toString())
                Spacer(modifier = Modifier.height((screenHeight / 4 - 75.dp)))
            }
        }
    }
}

private const val DETAIL_IMAGE_SIZE = 275
private const val LIGHTER_OR_DARKER_TEXT_FACTOR = 0.9f
