package com.coroutines.thisdayinhistory.glance

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Paint
import android.text.TextPaint
import androidx.annotation.FontRes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.core.util.TypedValueCompat.spToPx
import androidx.glance.BitmapImageProvider
import androidx.glance.Button
import androidx.glance.ButtonColors
import androidx.glance.ButtonDefaults
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.ImageProvider
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.ContentScale
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.wrapContentSize
import androidx.glance.layout.wrapContentWidth
import androidx.glance.text.FontStyle
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.coroutines.data.models.HistoricalEvent
import com.coroutines.thisdayinhistory.MainActivity
import androidx.glance.layout.Spacer
import androidx.glance.text.FontFamily
import androidx.glance.text.FontWeight
import com.coroutines.thisdayinhistory.ui.theme.Montserrat

@Composable
fun GlanceContent (
    modifier: GlanceModifier = GlanceModifier,
    context: Context,
    data: List<HistoricalEvent>,
    header: String
) {
    val action = actionStartActivity(
        Intent(
            context.applicationContext,
            MainActivity::class.java
        )
            .setAction(Intent.ACTION_VIEW)
            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    )

    CompositionLocalProvider(
        LocalContentColor provides GlanceTheme.colors.onSurface,
        LocalAccentColor provides GlanceTheme.colors.primary,
    ) {
        val accentColor = LocalAccentColor.current
        val contentColor = LocalContentColor.current
       // Box(GlanceModifier.appWidgetBackground().cornerRadius(48.dp)) {
            Scaffold(
                titleBar = {
                    Text(
                        text = "Today in History: $header",
                        modifier = GlanceModifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 10.dp),
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = accentColor,
                            fontSize = 14.sp
                        )
                    )
                }
            ) {
                Box(
                    modifier = GlanceModifier
                        .cornerRadius(32.dp)
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .padding(16.dp)
                ) {
                    LazyColumn() {
                        items(items = data) { item ->

                            Row(GlanceModifier.padding(bottom = 15.dp)) {
                                CoinImage(coinImage = item.bitMap!!)
                                Spacer(modifier = GlanceModifier.size(16.dp))
                                Text(
                                    text = "${item.year}: ${item.description}",
                                    style = TextStyle(fontFamily = FontFamily.Monospace, color = contentColor),
                                    modifier = modifier.clickable(action)
                                )
                            }

                            Spacer(modifier = GlanceModifier.size(12.dp))
                        }
                    }
                }
            }
       }
    }


@Composable
private fun CoinImage(coinImage: Bitmap) {
    Image(
        provider = androidx.glance.ImageProvider(coinImage),
        contentDescription = "desc",
        contentScale = ContentScale.Crop,
        modifier = GlanceModifier.size(40.dp).cornerRadius(29.dp)
    )
}

/*@Composable
fun GlanceText(
    text: String,
    @FontRes font: Int,
    fontSize: TextUnit,
    modifier: GlanceModifier = GlanceModifier,
    color: Color = Color.Black,
    letterSpacing: TextUnit = 0.1.sp
) {
    Image(
        modifier = modifier,
        provider = BitmapImageProvider(
            LocalContext.current.textAsBitmap(
                text = text,
                fontSize = fontSize,
                color = color,
                font = font,
                letterSpacing = letterSpacing.value
            )
        ),
        contentDescription = null,
    )
}*/

fun Context.textAsBitmap(
    text: String,
    fontSize: TextUnit,
    color: Color = Color.Black,
    letterSpacing: Float = 0.1f,
    font: Int
): Bitmap {
    val paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
   // paint.textSize = spToPx(fontSize.value, this)
    paint.color = color.toArgb()
    paint.letterSpacing = letterSpacing
    paint.typeface = ResourcesCompat.getFont(this, font)

    val baseline = -paint.ascent()
    val width = (paint.measureText(text)).toInt()
    val height = (baseline + paint.descent()).toInt()
    val image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(image)
    canvas.drawText(text, 0f, baseline, paint)
    return image
}
