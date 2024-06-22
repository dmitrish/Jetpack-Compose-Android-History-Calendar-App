package com.coroutines.thisdayinhistory.ui.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

import androidx.annotation.Size
import kotlin.math.max
import kotlin.math.min
typealias Color = Int
typealias AndroidColor = android.graphics.Color



val Color.isDark: Boolean
    get() {
        // Counting the perceptive luminance - human eye favors green color...
        val a = (1 - (0.299f * this.red + 0.587f * this.green + 0.114f * this.blue) / 255f).toDouble()
        // High brightness
        return a >= 0.5
    }

val Color.isLight: Boolean
    get() {
        return !this.isDark
    }


val Color.isAlmostWhite: Boolean
    get() {
        // Counting the perceptive luminance - human eye favors green color...
        val a = (1 - (0.299f * this.red + 0.587f * this.green + 0.114f * this.blue) / 255f).toDouble()
        // High brightness
        return a < 0.1
    }

/**
 * Get complementary color
 */


/**
 * Get color hex string
 */


/**
 * HSL color
 */
val Color.hsl: FloatArray
    get() {
        val r = this.red / 255f
        val g = this.green / 255f
        val b = this.blue / 255f

        val hsl = FloatArray(3)

        val max = max(r, max(g, b))
        val min = min(r, min(g, b))
        hsl[2] = (max + min) / 2

        if (max == min) {
            hsl[1] = 0f
            hsl[0] = hsl[1]

        } else {
            val d = max - min

            hsl[1] = if (hsl[2] > 0.5f) d / (2f - max - min) else d / (max + min)
            when (max) {
                r -> hsl[0] = (g - b) / d + (if (g < b) 6 else 0)
                g -> hsl[0] = (b - r) / d + 2
                b -> hsl[0] = (r - g) / d + 4
            }
            hsl[0] /= 6f
        }
        return hsl
    }

/**
 * Get lighter color
 */

fun Color.lighter(factor: Float = 1f) =
    Color(ColorUtils.blendARGB(this.toArgb(), Color.White.toArgb(), factor))

fun Color.darker(factor: Float = 1f) =
    Color(ColorUtils.blendARGB(this.toArgb(), Color.Black.toArgb(), factor))