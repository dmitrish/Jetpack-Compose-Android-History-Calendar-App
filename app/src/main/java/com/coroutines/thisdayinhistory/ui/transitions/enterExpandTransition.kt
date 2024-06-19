package com.coroutines.thisdayinhistory.ui.transitions
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.IntSize
import androidx.navigation.NavBackStackEntry

@Suppress("MagicNumber")
fun enterExpandTransition() : EnterTransition {
    return expandIn(
        animationSpec = tween(100, easing = LinearOutSlowInEasing),
        expandFrom = Alignment.CenterStart
    )
    {
        IntSize(50, 50)

    }   + fadeIn(
        animationSpec = tween(durationMillis = 500)
    )
}