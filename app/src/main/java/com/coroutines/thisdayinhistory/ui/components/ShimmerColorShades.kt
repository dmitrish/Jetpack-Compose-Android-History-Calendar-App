@file:Suppress("MagicNumber")
package com.coroutines.thisdayinhistory.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.luminance
import kotlin.math.max
import kotlin.math.min

const val MinContrastOfPrimaryVsSurface = 2f

val MetallicSilver = Color(0xFFA8A9AD)
val GrayishBlack = Color(0xFF555152)

val ShimmerColorShades = listOf(
    Color.LightGray.copy(0.0f),
    Color.LightGray.copy(0.33f),
    Color.LightGray.copy(0.0f)
)
fun Color.contrastAgainst(background: Color): Float {
    val fg = if (alpha < 1f) compositeOver(background) else this

    val fgLuminance = fg.luminance() + 0.05f
    val bgLuminance = background.luminance() + 0.05f

    return max(fgLuminance, bgLuminance) / min(fgLuminance, bgLuminance)
}
