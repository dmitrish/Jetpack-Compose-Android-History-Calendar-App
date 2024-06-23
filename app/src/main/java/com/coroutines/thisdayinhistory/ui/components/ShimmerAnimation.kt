package com.coroutines.thisdayinhistory.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ShimmerAnimation() {
    /*
    Create InfiniteTransition
    which holds child animation like [Transition]
    animations start running as soon as they enter
    the composition and do not stop unless they are removed
     */
    val transition = rememberInfiniteTransition(label = "")
    val translateAnim by transition.animateFloat(
        /*
        Specify animation positions,
        initial Values 0F means it
        starts from 0 position
         */
        initialValue = 0f,
        // targetValue = 1000f,
        targetValue = 2000f,
        animationSpec = infiniteRepeatable(

            // Tween Animates between values over specified [durationMillis]
            tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = ""
    )

    /*
    Create a gradient using the list of colors
    Use Linear Gradient for animating in any direction according to requirement
    start=specifies the position to start with in cartesian like system Offset(10f,10f) means x(10,0) , y(0,10)
    end = Animate the end position to give the shimmer effect using the transition created above
     */
    val brush = Brush.linearGradient(
        colors = ShimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )

    ShimmerItem(brush = brush)
}

@Preview
@Composable
fun ShimmerAnimationPreview(){
    ShimmerAnimation()
}