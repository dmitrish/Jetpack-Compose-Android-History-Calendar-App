package com.coroutines.thisdayinhistory.ui.modifiers

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Modifier.pagerOffsetAnimation(
    pagerState: PagerState,
    page: Int,
): Modifier {
    val columnModifier = this.then(Modifier.graphicsLayer {
        val pageOffset =
            (
                    (pagerState.currentPage - page) + pagerState
                        .currentPageOffsetFraction
                    ).absoluteValue
        lerp(
            start = 0.85f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        ).also { scale ->
            scaleX = scale
            scaleY = scale
        }
        alpha = lerp(
            start = 0.5f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        )
    })
    return columnModifier
}