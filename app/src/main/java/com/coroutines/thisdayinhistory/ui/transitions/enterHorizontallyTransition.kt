package com.coroutines.thisdayinhistory.ui.transitions


import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally

@Suppress("MagicNumber")
fun enterHorizontallyTransition() : EnterTransition {
    return slideInHorizontally(animationSpec = tween(durationMillis = 200)) { fullWidth ->
        // Offsets the content by 1/3 of its width to the left, and slide towards right
        // Overwrites the default animation with tween for this slide animation.
        -fullWidth / 3
    } + fadeIn(
        animationSpec = tween(durationMillis = 100))
}