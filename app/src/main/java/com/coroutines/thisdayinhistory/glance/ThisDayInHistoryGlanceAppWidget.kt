package com.coroutines.thisdayinhistory.glance

import android.content.Context
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.unit.ColorProvider
import coil.imageLoader
import coil.request.ImageRequest
import com.coroutines.data.models.HistoricalEvent
import com.coroutines.models.synonyms.HistoryDay
import com.coroutines.models.synonyms.HistoryMonth
import com.coroutines.thisdayinhistory.uimodels.InternationalMonth
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.Clock
import kotlinx.datetime.toKotlinTimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.ZoneId
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.layout.ContentScale
import androidx.glance.layout.padding
import androidx.glance.layout.size
import com.coroutines.thisdayinhistory.R
import com.coroutines.thisdayinhistory.ui.state.DataRequestState
import com.coroutines.thisdayinhistory.ui.state.RequestCategory
import kotlinx.coroutines.delay

val LocalContentColor = compositionLocalOf { ColorProvider(Color.Black) }
val LocalAccentColor = compositionLocalOf { ColorProvider(Color.White) }

class ThisDayInHistoryGlanceAppWidget: GlanceAppWidget() {

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
              val widgetState = GlanceServiceProvider.get(context).widgetStateHolder

              provideContent {
                  val dataState by widgetState.dataState.collectAsState()
                  LaunchedEffect(key1 = Unit) {
                      delay(3999)
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
                              val header = widgetState.internationalHeader

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
                          //DataRequestState.CompletedSuccessfully -> TODO()
                         // DataRequestState.CompletedWithError -> TODO()
                      }
                  }
              }
          } catch (e: CancellationException) {
              println(e)
          } catch (e: Exception) {
              println(e)
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
