package com.coroutines.thisdayinhistory.glance

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.StringRes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.Action
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionRunCallback
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
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.coroutines.data.models.HistoricalEvent
import com.coroutines.thisdayinhistory.MainActivity
import androidx.glance.layout.Spacer
import androidx.glance.text.FontFamily
import androidx.glance.text.FontWeight
import com.coroutines.thisdayinhistory.R
import com.coroutines.thisdayinhistory.ui.utils.darker
import com.coroutines.thisdayinhistory.ui.utils.lighter

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
            titleBar = { glanceHeader(header, accentColor)}
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
                                GlanceImage(coinImage = item.bitMap!!)
                                Spacer(modifier = GlanceModifier.size(16.dp))
                                GlanceText(item, contentColor, modifier, action)
                            }
                            Spacer(modifier = GlanceModifier.size(12.dp))
                        }
                    }
                }
            }
        }

    }

@Composable
private fun GlanceText(
    item: HistoricalEvent,
    contentColor: ColorProvider,
    modifier: GlanceModifier,
    action: Action
) {
    Text(
        text = "${item.year}: ${item.description}",
        style = TextStyle(
            fontFamily = FontFamily.Monospace,
            color = contentColor,
            fontSize = 14.sp
        ),
        maxLines = 4,
        modifier = modifier.clickable(action)
    )
}

@Composable
fun AppWidgetBox(
    modifier: GlanceModifier = GlanceModifier,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = GlanceModifier.appWidgetBackgroundModifier().then(modifier),
        contentAlignment = contentAlignment,
        content = content,
    )
}

@Composable
fun GlanceModifier.appWidgetBackgroundModifier(): GlanceModifier {
    return this.fillMaxSize()
        .padding(16.dp)
        .appWidgetBackground()
        .background(GlanceTheme.colors.background)
        .appWidgetBackgroundCornerRadius()
}

@Composable
private fun glanceHeader(header: String, accentColor: ColorProvider) {
    val context = LocalContext.current
    Row(GlanceModifier
        .fillMaxWidth()
        .background(GlanceTheme.colors.background.getColor(context).darker (0.3f))
        .padding(top = 12.dp, bottom = 10.dp, start = 16.dp)) {
        Image( modifier = GlanceModifier.size(36.dp).padding(top = 12.dp, start = 16.dp),
            provider = ImageProvider(resId = R.drawable.cat_logo_for_light_theme),
            contentDescription = "Icon" )
        Text(
           // text = "Today in History: $header",
            text = "$header",
            modifier = GlanceModifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 10.dp),
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = accentColor,
                fontSize = 15.sp
            )
        )
    }
}


@Composable
private fun GlanceImage(coinImage: Bitmap) {
    Image(
        provider = androidx.glance.ImageProvider(coinImage),
        contentDescription = "desc",
        contentScale = ContentScale.Crop,
        modifier = GlanceModifier.size(44.dp).cornerRadius(29.dp)
    )
}


fun GlanceModifier.appWidgetBackgroundCornerRadius(): GlanceModifier {
    if (Build.VERSION.SDK_INT >= 31) {
        cornerRadius(android.R.dimen.system_app_widget_background_radius)
    }
    return cornerRadius(16.dp)
}

fun GlanceModifier.appWidgetInnerCornerRadius(): GlanceModifier {
    if (Build.VERSION.SDK_INT >= 31) {
        return cornerRadius(android.R.dimen.system_app_widget_inner_radius)
    }
    return cornerRadius(8.dp)
}

@Composable
fun stringResource(@StringRes id: Int, vararg args: Any): String {
    return LocalContext.current.getString(id, args)
}
