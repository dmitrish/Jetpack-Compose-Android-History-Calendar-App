package com.coroutines.thisdayinhistory.glance

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.core.net.toUri
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
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
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.coroutines.models.synonyms.HistoryDay
import com.coroutines.models.synonyms.HistoryMonth
import com.coroutines.thisdayinhistory.MainActivity
import com.coroutines.thisdayinhistory.uimodels.InternationalMonth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.toKotlinTimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.ZoneId


class ThisDayInHistoryGlanceAppWidget: GlanceAppWidget() {


    @SuppressLint("CoroutineCreationDuringComposition")
    override suspend fun provideGlance(context: Context, id: GlanceId) {
      coroutineScope {
            try {
                val repository = GlanceServiceProvider.get(context).dataRepository
                val preferencesRepository = GlanceServiceProvider.get(context).userPreferencesRepository
                val settings = preferencesRepository.getLanguagePreference().first()
                val today = kotlinx.datetime.Clock.System.now()
                val monthNumber = today.toLocalDateTime(ZoneId.systemDefault().toKotlinTimeZone()).monthNumber
                val dayNumber = today.toLocalDateTime(ZoneId.systemDefault().toKotlinTimeZone()).dayOfMonth
                val internationalMonth = InternationalMonth(mutableStateOf(settings.langId), mutableStateOf(monthNumber))
                val data = repository
                    .wikiFlowList(HistoryMonth(monthNumber), HistoryDay(dayNumber), settings.langId, "selected")
                    .onEach { it ->
                        it.forEach { item ->
                            val request = ImageRequest.Builder(context)
                                .data(item.imageUrl)
                                .build()
                            item.bitMap = context.imageLoader.execute(request).drawable?.toBitmap()
                        }
                    }
                    .flowOn(Dispatchers.IO)
                    .stateIn(this@coroutineScope)


                provideContent {
                    //val settingsViewModel: ISettingsViewModel = hiltViewModel<SettingsViewModel>()
                    val dataUnwrapped = data.collectAsState()

                    GlanceContent(
                        context = context,
                        data = dataUnwrapped.value ,
                        header = internationalMonth.monthSelected + ", " + dayNumber)

                  /*  Scaffold (
                        titleBar =  { Text(
                            "Today in History: " + internationalMonth.monthSelected + ", " + dayNumber,
                            modifier = GlanceModifier.padding(20.dp)
                        )
                        }
                    ){
                        Box(
                            modifier = GlanceModifier
                                .background(Color.White)
                                .padding(16.dp)
                            // .clickable(actionRunCallback<RefreshQuoteAction>())
                        ){
                            LazyColumn () {
                                items(items = dataUnwrapped.value) { item ->
                                    Row (GlanceModifier.clickable {
                                        actionStartActivity(
                                            Intent(context.applicationContext, MainActivity::class.java)
                                                .setAction(Intent.ACTION_VIEW)
                                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                               // .setData("https://socialite.google.com/chat/${model.contactId}".toUri()),
                                    }) {
                                        CoinImage(coinImage = item.bitMap!!)
                                        androidx.glance.layout.Spacer( modifier = GlanceModifier.size(16.dp))

                                       // Text(item.description)
                                        Button(text = item.description, onClick = actionStartActivity(
                                            Intent(context.applicationContext, MainActivity::class.java)
                                                .setAction(Intent.ACTION_VIEW)
                                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                    )
                                    }
                                    androidx.glance.layout.Spacer( modifier = GlanceModifier.size(8.dp))
                                }

                            }
                        }
                    }*/
                    }


            }
            catch (e: Exception){
                provideContent {
                    Box(
                        modifier = GlanceModifier
                            .background(Color.White)
                            .padding(16.dp)
                        // .clickable(actionRunCallback<RefreshQuoteAction>())
                    ) {
                        Text(text = e.message.toString())
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
            modifier = GlanceModifier.size(80.dp).cornerRadius(18.dp)
        )
    }

    private suspend fun Context.getNetworkImage(url: String, force: Boolean = false): Bitmap? {

        val request = ImageRequest.Builder(this)
            .data(url)
           // .decoderFactory(SvgDecoder.Factory())
            .apply {
                if (force) {
                    memoryCachePolicy(CachePolicy.DISABLED)
                    diskCachePolicy(CachePolicy.DISABLED)
                }
            }.build()

        // Request the image to be loaded and throw error if it failed
        return when (val result = imageLoader.execute(request)) {
            is ErrorResult -> {
                throw result.throwable
            }
            is SuccessResult -> {
                result.drawable.toBitmapOrNull()
            }
        }
    }
}
