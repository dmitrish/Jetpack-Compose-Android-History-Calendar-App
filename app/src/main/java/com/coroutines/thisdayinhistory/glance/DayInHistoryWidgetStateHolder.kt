package com.coroutines.thisdayinhistory.glance

import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.graphics.drawable.toBitmap
import coil.imageLoader
import coil.request.ImageRequest
import com.coroutines.data.models.HistoricalEvent
import com.coroutines.models.synonyms.HistoryDay
import com.coroutines.models.synonyms.HistoryMonth
import com.coroutines.thisdayinhistory.preferences.UserPreferencesRepository
import com.coroutines.thisdayinhistory.ui.state.DataRequestState
import com.coroutines.thisdayinhistory.ui.state.RequestCategory
import com.coroutines.thisdayinhistory.uimodels.InternationalMonth
import com.coroutines.usecase.IHistoryDataStandardUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlinx.datetime.toKotlinTimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.ZoneId
import javax.inject.Inject

data class AppWidgetState(
    val dataRequestState: DataRequestState,
    val data: List<HistoricalEvent>? = null
)

const val DATA_CATEGORY = "selected"

class WidgetStateHolder @Inject constructor(
    private val dataRepository: IHistoryDataStandardUseCase,
    private val userPreferencesRepository: UserPreferencesRepository
)  {

    private val _dataFlow = MutableStateFlow<List<HistoricalEvent>>(value = listOf())
    private val _widgetState = MutableStateFlow<AppWidgetState>(value = AppWidgetState(DataRequestState.NotStarted))

    val dataFlow = _dataFlow.asStateFlow()
    val dataState = _widgetState.asStateFlow()

    lateinit var internationalHeader: String

    suspend fun start(context: Context){

        val today = Clock.System.now().toLocalDateTime(timeZone = ZoneId.systemDefault().toKotlinTimeZone())

        val langId = userPreferencesRepository.getLanguagePreference().first().langId

        val monthNumber =  today.monthNumber
        val dayNumber = today.dayOfMonth

        val internationalMonth =  InternationalMonth(mutableStateOf(langId), mutableIntStateOf(monthNumber))

        internationalHeader = "${internationalMonth.monthSelected}, $dayNumber"

        dataRepository.wikiFlowList(
            HistoryMonth(monthNumber),
            HistoryDay(dayNumber),
            langId,
            DATA_CATEGORY)
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
            .flowOn(Dispatchers.Default)
            .onStart {
                _widgetState.update { appWidgetState ->
                    appWidgetState.copy(dataRequestState = DataRequestState.Started)
                }
            }
            .onCompletion {
                _widgetState.update { appWidgetState ->
                    appWidgetState.copy(dataRequestState = DataRequestState.CompletedSuccessfully(RequestCategory.Data))
                }
            }
            .collect { data ->
                _widgetState.update { appWidgetState ->
                    appWidgetState.copy(data = data)
                }
        }
    }
}