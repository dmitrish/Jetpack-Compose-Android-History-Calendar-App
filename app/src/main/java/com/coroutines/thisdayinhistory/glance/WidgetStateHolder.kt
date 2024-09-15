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
import com.coroutines.thisdayinhistory.uimodels.InternationalMonth
import com.coroutines.usecase.IHistoryDataStandardUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlinx.datetime.toKotlinTimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class WidgetStateHolder @Inject constructor(
    private val dataRepository: IHistoryDataStandardUseCase,
    private val userPreferencesRepository: UserPreferencesRepository
)  {
    private val today = Clock.System.now().toLocalDateTime(ZoneId.systemDefault().toKotlinTimeZone())
    private var _dataFlow = MutableStateFlow<List<HistoricalEvent>>(listOf())
    private var _widgetState = MutableStateFlow<WidgetState>(WidgetState.Empty)

    val dataFlow = _dataFlow.asStateFlow()
    val dataState = _widgetState.asStateFlow()

    suspend fun start(context: Context){
        _widgetState.value = WidgetState.Loading

        val langId = userPreferencesRepository.getLanguagePreference().first().langId

        val monthNumber =  today.monthNumber
        val dayNumber = today.dayOfMonth

        val internationalMonth =  InternationalMonth(mutableStateOf(langId), mutableIntStateOf(monthNumber))

        dataRepository.wikiFlowList(
            HistoryMonth(monthNumber),
            HistoryDay(dayNumber),
            langId,
            "selected")
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
            .onCompletion {
                _widgetState.value = WidgetState.Loaded
            }
            .collect {
                _dataFlow.value = it
        }
    }
}