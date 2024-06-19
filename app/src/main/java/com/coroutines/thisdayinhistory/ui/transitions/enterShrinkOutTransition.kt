package com.coroutines.thisdayinhistory.ui.transitions



import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.shrinkOut
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.IntSize

@Suppress("MagicNumber")
fun exitShrinkOutTransition(): ExitTransition {
    return  shrinkOut(
        tween(100, easing = FastOutSlowInEasing),
        // Overwrites the area of the content that the shrink animation will end on. The
        // following parameters will shrink the content's clip bounds from the full size of the
        // content to 1/10 of the width and 1/5 of the height. The shrinking clip bounds will
        // always be aligned to the CenterStart of the full-content bounds.
        shrinkTowards = Alignment.CenterStart
    ){ fullSize ->
        IntSize(fullSize.width / 10, fullSize.height / 5)
    }
}