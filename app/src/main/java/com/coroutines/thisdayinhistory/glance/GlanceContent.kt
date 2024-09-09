package com.coroutines.thisdayinhistory.glance

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.Button
import androidx.glance.ButtonColors
import androidx.glance.ButtonDefaults
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.action.clickable
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
import androidx.glance.layout.wrapContentSize
import androidx.glance.layout.wrapContentWidth
import androidx.glance.text.FontStyle
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.coroutines.data.models.HistoricalEvent
import com.coroutines.thisdayinhistory.MainActivity

@Composable
fun GlanceContent (modifier: GlanceModifier = GlanceModifier,
                   context: Context,
                   data: List<HistoricalEvent>,
                   header: String) {

    Scaffold (
        titleBar =  {
            Text(
                text = "Today in History: $header",
                modifier = GlanceModifier.padding(20.dp)
            )
        }
    ){
        Box(
            modifier = GlanceModifier
                .background(Color.White)
                .padding(16.dp)
        ){
            LazyColumn () {
                items(items = data) { item ->
                    Row (GlanceModifier.wrapContentSize().padding(bottom = 15.dp).clickable {
                        actionStartActivity(
                            Intent(context.applicationContext, MainActivity::class.java)
                                .setAction(Intent.ACTION_VIEW)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }) {
                        CoinImage(coinImage = item.bitMap!!)
                        androidx.glance.layout.Spacer( modifier = GlanceModifier.size(16.dp))

                        // Text(item.description)
                        Button(
                            text = "${item.year}: ${item.description}",
                            maxLines = 3,
                            style = TextStyle(
                                textAlign = TextAlign.Start,
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Normal),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = ColorProvider(Color.Transparent),
                                ColorProvider(Color.Black)
                            ),
                            onClick = actionStartActivity(
                            Intent(context.applicationContext, MainActivity::class.java)
                                .setAction(Intent.ACTION_VIEW)
                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)),
                            modifier = modifier.fillMaxSize()
                        )
                    }
                    androidx.glance.layout.Spacer( modifier = GlanceModifier.size(12.dp))
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