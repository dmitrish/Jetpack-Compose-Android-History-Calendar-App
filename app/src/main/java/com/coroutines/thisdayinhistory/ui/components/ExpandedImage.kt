package com.coroutines.thisdayinhistory.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.coroutines.data.models.HistoricalEvent
@Composable
@Suppress("MagicNumber", "LongMethod")
fun ExpandedImage(historicalEvent: HistoricalEvent) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(historicalEvent.originalImage?.source)
            .crossfade(false)
            .build(),
        contentDescription = "image",
        Modifier.fillMaxSize(),
        alignment = Alignment.Center,
        contentScale = if
                               (historicalEvent.originalImage?.height!! > historicalEvent.originalImage!!.width) {
            ContentScale.Crop
        } else
            ContentScale.Fit
    ) {
        if (painter.state is AsyncImagePainter.State.Loading || painter.state is AsyncImagePainter.State.Empty) {
            val animateShape = remember { Animatable(15f) }
            LaunchedEffect(animateShape) {
                animateShape.animateTo(
                    targetValue = 0f,
                    animationSpec = repeatable(
                        animation = tween(
                            durationMillis = 350,
                            easing = LinearEasing,
                            delayMillis = 0
                        ),
                        repeatMode = RepeatMode.Restart,
                        iterations = 1
                    )
                )
            }
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = historicalEvent.imageUrl).apply(block = fun ImageRequest.Builder.() {
                            crossfade(false)
                            memoryCachePolicy(CachePolicy.ENABLED)
                            memoryCacheKey(historicalEvent.imageUrl)
                        }).build()
                ),
                contentDescription = "image ${historicalEvent.shortTitle}",
                Modifier
                    .fillMaxSize()
                    .blur(animateShape.value.toInt().dp),
                alignment = Alignment.Center,
                contentScale = if
                                       (historicalEvent.originalImage!!.height > historicalEvent.originalImage!!.width) {
                    ContentScale.Crop
                } else
                    ContentScale.Fit
            )
        } else {
            val surfaceColor = MaterialTheme.colorScheme.surface
            val dominantColorState = rememberDominantColorState { color ->
                // We want a color which has sufficient contrast against the surface color
                color.contrastAgainst(surfaceColor) >= MinContrastOfPrimaryVsSurface
            }

            DynamicThemePrimaryColorsFromImage(dominantColorState) {
                SubcomposeAsyncImageContent(
                    alignment = Alignment.Center,
                    contentScale = if
                                           (historicalEvent.originalImage!!.height > historicalEvent.originalImage!!.width) {
                        ContentScale.Crop
                    } else
                        ContentScale.Fit
                )
            }
        }
    }
}
