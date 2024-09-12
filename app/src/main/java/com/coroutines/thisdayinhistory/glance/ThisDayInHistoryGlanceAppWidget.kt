package com.coroutines.thisdayinhistory.glance

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.core.net.toUri
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Box
import androidx.glance.layout.ContentScale
import androidx.glance.layout.Row
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.Text
import androidx.glance.unit.ColorProvider
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.coroutines.models.synonyms.HistoryDay
import com.coroutines.models.synonyms.HistoryMonth
import com.coroutines.thisdayinhistory.MainActivity
import com.coroutines.thisdayinhistory.uimodels.InternationalMonth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinTimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.ZoneId

val LocalContentColor = compositionLocalOf { ColorProvider(Color.Black) }
val LocalAccentColor = compositionLocalOf { ColorProvider(Color.White) }

class ThisDayInHistoryGlanceAppWidget: GlanceAppWidget() {

    companion object {
        const val UNIQUE_WORK_TAG = "ThisDayInHistoryWidgetWork"
        const val DATA_CATEGORY = "selected"
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
      coroutineScope {
          try {
              val repository = GlanceServiceProvider.get(context).dataRepository
              val preferencesRepository =
                  GlanceServiceProvider.get(context).userPreferencesRepository
              val settings = preferencesRepository.getLanguagePreference().first()
              val today = kotlinx.datetime.Clock.System.now()
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
                  .stateIn(this@coroutineScope)

              val dataUnwrapped = data.value

              provideContent {
                  GlanceTheme {
                      GlanceContent(
                          context = context,
                          data = dataUnwrapped,
                          header = "${internationalMonth.monthSelected} ,  $dayNumber"
                      )
                  }
              }
          } catch (e: Exception) {
              provideContent {
                  Box(
                      modifier = GlanceModifier
                          .background(Color.White)
                          .padding(16.dp)
                  ) {
                      Text(text = e.message.toString())
                  }
              }
          }
      }
    }
}
