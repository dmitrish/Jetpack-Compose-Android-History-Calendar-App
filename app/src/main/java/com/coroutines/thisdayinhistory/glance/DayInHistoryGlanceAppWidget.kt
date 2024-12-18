package com.coroutines.thisdayinhistory.glance

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent

import kotlinx.coroutines.CancellationException
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.background
import androidx.glance.color.ColorProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.ContentScale
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.wrapContentWidth
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.coroutines.thisdayinhistory.R
import com.coroutines.thisdayinhistory.ui.state.DataRequestState
import com.coroutines.thisdayinhistory.ui.state.RequestCategory
import kotlinx.coroutines.delay

/*


 */

class DayInHistoryGlanceAppWidget: GlanceAppWidget() {

    companion object {
        const val UNIQUE_WORK_TAG = "ThisDayInHistoryWidgetWork"
        const val DATA_CATEGORY = "selected"
    }


    override fun onCompositionError(
        context: Context,
        glanceId: GlanceId,
        appWidgetId: Int,
        throwable: Throwable
    ) {
        super.onCompositionError(context, glanceId, appWidgetId, throwable)
    }


    override suspend fun provideGlance(context: Context, id: GlanceId) {
          try {
              val widgetState = DayInHistoryGlanceServiceProvider.get(context).widgetStateHolder

              provideContent {
                  val dataState by widgetState.dataState.collectAsState()
                  LaunchedEffect(key1 = Unit) {
                      delay(250)
                      widgetState.start(context)
                  }

                  GlanceTheme {
                      val bg = GlanceTheme.colors.background
                      when (dataState.dataRequestState) {
                          DataRequestState.NotStarted -> {
                              AppWidgetPlaceholderBox {
                                  Image(
                                      provider = ImageProvider(R.drawable.cat),
                                      contentDescription = null,
                                      modifier = GlanceModifier.padding(start = 20.dp).size(65.dp),
                                      contentScale = ContentScale.Crop
                                  )
                              }
                          }
                          DataRequestState.Started -> {
                              AppWidgetPlaceholderBox {
                                  CircularProgressIndicator()
                              }
                          }
                          DataRequestState.CompletedSuccessfully(RequestCategory.Data) -> {
                              val header = "Today in History: ${widgetState.internationalHeader}"
                              dataState.data?.let {
                                  GlanceContent(
                                      context = context,
                                      data = dataState.data!!,
                                      header = header
                                  )
                              }
                          }
                         else -> {

                         }
                      }
                  }
              }
          } catch (e: CancellationException) {
              println(e)
          } catch (e: Exception) {
              println(e)
          }
    }



    @Composable
    @OptIn(ExperimentalGlancePreviewApi::class)
    @Preview(widthDp = 340, heightDp = 100)
    @Preview(widthDp = 230, heightDp = 100)
    @Preview(widthDp = 130, heightDp = 100)
    fun Test(){

        Box(modifier = GlanceModifier.padding(14.dp)) {
                Row(
                    modifier = GlanceModifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
                    verticalAlignment = Alignment.Vertical.CenterVertically
                ) {
                    Image(
                        provider = ImageProvider(R.drawable.sun_68),
                        contentDescription = null,
                        modifier = GlanceModifier.size(30.dp),
                        contentScale = ContentScale.Crop)

                   Column (
                            modifier = GlanceModifier.padding(1.dp).defaultWeight(),
                            horizontalAlignment = Alignment.Horizontal.Start,
                            verticalAlignment = Alignment.Vertical.CenterVertically
                            )
                    {
                        Text(
                            text = "This text can be truncated or it can fit ",
                            modifier = GlanceModifier.padding(horizontal = 8.dp),
                            style = TextStyle(
                                color = ColorProvider(day = Color.White, night = Color.White),
                                fontSize = 14.sp
                            ),
                            maxLines = 1)
                        Text(
                            text = "But it will not push out rightmost column",
                            modifier = GlanceModifier.padding(horizontal = 8.dp),
                            style = TextStyle(
                                color = ColorProvider(day = Color.White, night = Color.White),
                                fontSize = 10.sp
                            ),
                            maxLines = 1)
                    }
                    Column (
                        modifier = GlanceModifier.wrapContentWidth(),
                        horizontalAlignment = Alignment.Horizontal.Start,
                        verticalAlignment = Alignment.Vertical.CenterVertically
                    ){
                        Text(
                            "Right",
                            maxLines = 1)
                        Text(
                            "side",
                            maxLines = 1)
                    }
           }
        }
    }

   /* private suspend fun CoroutineScope.getGlanceData(context: Context): Triple<Int, InternationalMonth, StateFlow<List<HistoricalEvent>>> {
        val repository = GlanceServiceProvider.get(context).dataRepository
        val preferencesRepository =
            GlanceServiceProvider.get(context).userPreferencesRepository
        val settings = preferencesRepository.getLanguagePreference().first()
        val today = Clock.System.now()
        val monthNumber =
            today.toLocalDateTime(ZoneId.systemDefault().toKotlinTimeZone()).monthNumber
        val dayNumber =
            today.toLocalDateTime(ZoneId.systemDefault().toKotlinTimeZone()).dayOfMonth
        val internationalMonth =
            InternationalMonth(mutableStateOf(settings.langId), mutableIntStateOf(monthNumber))
        val data = repository
            .wikiFlowList(
                HistoryMonth(monthNumber),
                HistoryDay(dayNumber),
                settings.langId,
                DATA_CATEGORY
            )
            .onEach { it ->
                it.forEach { item ->
                    val request = ImageRequest.Builder(context)
                        .data(item.imageUrl)
                        .build()
                    item.bitMap = context
                        .imageLoader
                        .execute(request)
                        .drawable?.toBitmap()
                }
            }
            .flowOn(Dispatchers.IO)
            .stateIn(this)
        return Triple(dayNumber, internationalMonth, data)
    }*/
}
