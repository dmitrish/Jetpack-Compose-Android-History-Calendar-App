package com.coroutines.thisdayinhistory.ui.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coroutines.data.models.EventCategoryEnum
import com.coroutines.data.models.HistoricalEvent
import com.coroutines.data.models.LangEnum
import com.coroutines.models.synonyms.HistoryDay
import com.coroutines.models.synonyms.HistoryMonth
import com.coroutines.thisdayinhistory.preferences.UserPreferencesRepository
import com.coroutines.thisdayinhistory.ui.state.DataRequestParams
import com.coroutines.thisdayinhistory.ui.state.DataRequestState
import com.coroutines.thisdayinhistory.ui.state.HistoryViewModelState
import com.coroutines.thisdayinhistory.ui.state.RequestCategory
import com.coroutines.thisdayinhistory.uimodels.CatsByLanguage
import com.coroutines.thisdayinhistory.uimodels.IHistoryCalendar
import com.coroutines.thisdayinhistory.uimodels.IHistoryDataMap
import com.coroutines.thisdayinhistory.uimodels.IInternationalMonth
import com.coroutines.thisdayinhistory.uimodels.InternationalMonth
import com.coroutines.thisdayinhistory.uimodels.SelectedDate
import com.coroutines.usecase.IHistoryDataStandardUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.time.LocalDateTime

private data class HistoryState(
    val dataRequestState: DataRequestState = DataRequestState.NotStarted,
    val selectedCategory: String = CatsByLanguage(LangEnum.ENGLISH).getDefaultCategory(),
    val previousCategory: String = CatsByLanguage(LangEnum.ENGLISH).getDefaultCategory(),
    val selectedItem: HistoricalEvent = HistoricalEvent(description = "No Events"),
    val selectedDate: SelectedDate = SelectedDate("January", 1),
    val catsByLanguage: CatsByLanguage = CatsByLanguage(LangEnum.ENGLISH),
    val filter: String = ""
) {
    fun asActivityState() = HistoryViewModelState(
        dataRequestState = dataRequestState,
        selectedCategory = selectedCategory,
        previousCategory = previousCategory,
        selectedItem = selectedItem,
        selectedDate = selectedDate,
        catsByLanguage = catsByLanguage,
        filter = filter
    )
}

class HistoryViewModelFactory(private val lang: LangEnum,
                              private val historyDataUseCase: IHistoryDataStandardUseCase,
                              private val historyDataMap: IHistoryDataMap,
                              val historyCalendar: IHistoryCalendar
    ) : ViewModelProvider.Factory {@Suppress("UNCHECKED_CAST")
override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return HistoryViewModel(lang, historyDataUseCase, historyDataMap, historyCalendar) as T
}
}

@SuppressLint("StaticFieldLeak")
@Immutable
//@HiltViewModel(assistedFactory = HistoryViewModel.IHistoryViewModelFactory::class)
class HistoryViewModel  constructor(
    private val lang: LangEnum,
    private val historyDataUseCase: IHistoryDataStandardUseCase,
    private val historyDataMap: IHistoryDataMap,
    val historyCalendar: IHistoryCalendar,
) : IHistoryViewModel,
    IHistoryCalendar by historyCalendar,
    IInternationalMonth by InternationalMonth(
        mutableStateOf(lang.langId),
        historyCalendar.monthOfCalendar
    ),
    ViewModel() {

    private lateinit var _dataRequestParams: DataRequestParams
    private val viewModelState = MutableStateFlow(value = HistoryState())
    private var count = 0

    override var isScrolled = mutableStateOf(false)
    override var filterKey: String = ""
    override var selectedItem: HistoricalEvent = HistoricalEvent("No Events")

    init {
        language.value = lang.langId
        viewModelState.update { state ->
            state.copy( catsByLanguage = CatsByLanguage(lang),
                selectedCategory = CatsByLanguage(lang).getDefaultCategory(),
                selectedDate = SelectedDate(monthSelected, dayOfCalendar.value)
            )
        }
        init(lang)
    }
    override val uiState = viewModelState
        .map {it.asActivityState() }
        .stateIn (
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = viewModelState.value.asActivityState()
        )

    private fun init(language: LangEnum) {
        setDataRequestState(DataRequestState.NotStarted)
        setDefaultCategoryByLanguage(language)
        viewModelScope.launch() {
            _dataRequestParams = DataRequestParams(
                HistoryMonth(historyCalendar.monthOfCalendar.value),
                HistoryDay(historyCalendar.dayOfCalendar.value),
                language,
                EventCategoryEnum.events
            )
            collectHistory(_dataRequestParams)
        }
    }


    override val historyData: SnapshotStateList<HistoricalEvent>
        get() = getData()

    private fun getData(): SnapshotStateList<HistoricalEvent> {
        val category = CatsByLanguage(lang).getEnglishCategoryFromValue(viewModelState.value.selectedCategory)
        val data = historyDataMap.historyDataMap[category]

        return if (filterKey.isNotBlank()) {
            val result = data?.filter {
                it.description.contains(filterKey, true) }!!.toMutableStateList()
            result
        } else {
            data ?: mutableStateListOf()
        }
    }

    override fun updateDate(count: Int) {
        val currentDate = historyCalendar.currentLocalDateTime.plusDays(count.toLong())
        onDateChanged(currentDate)
    }

    override fun onDateChanged(localDateTime: LocalDateTime) {

        historyCalendar.currentLocalDateTime = localDateTime
        monthOfCalendar.value = localDateTime.monthValue
        dayOfCalendar.value = localDateTime.dayOfMonth

        _dataRequestParams = _dataRequestParams.copy(
            month = HistoryMonth(localDateTime.monthValue),
            day = HistoryDay(localDateTime.dayOfMonth)
        )

        viewModelState.update { state ->
            state.copy(
                selectedDate = SelectedDate(monthSelected, dayOfCalendar.value)
            )
        }

        collectHistory(_dataRequestParams)
    }

    override fun onCategoryChanged(optionSelected: String) {
        setDataRequestState (DataRequestState.Started)
        updateCategory(optionSelected)
        setDataRequestState (DataRequestState.CompletedSuccessfully(RequestCategory.Option))
    }

    private fun setDefaultCategoryByLanguage(language: LangEnum) {
        viewModelState.update { state ->
            state.copy(
                selectedCategory = CatsByLanguage(language).getDefaultCategory()
            )
        }
    }

    override fun search(searchTerm: String) {
        filterKey = searchTerm
        viewModelState.update { state ->
            state.copy(
                filter = searchTerm
            )
        }

        //setDataRequestState (DataRequestState.CompletedSuccessfully(RequestCategory.Option))
    }

    private suspend fun collect(
        month: HistoryMonth,
        day: HistoryDay,
        language: LangEnum,
        category: EventCategoryEnum,
        collector: SnapshotStateList<HistoricalEvent>,
    ) {
        historyDataUseCase
            .wikiFlowList(month, day, language.langId, category.name)
            .retryWhen { cause, attempt ->
                Log.d(TAG, "Fetching IO exception for category ${category.name}: $cause")
                if (attempt < REQUEST_RETRY_COUNT &&
                    (cause is SocketTimeoutException || cause is retrofit2.HttpException)
                ) {
                    Log.d(TAG, "Fetching IO exception: $cause. Will retry")
                    delay(REQUEST_RETRY_DELAY)
                    true
                } else {
                    Log.d(TAG, "Fetching exception: $cause. No retries.")
                    false
                }
            }
            .flowOn(Dispatchers.Default)

            .onCompletion {
                if (viewModelState.value.dataRequestState == DataRequestState.Started
                    && (count == REQUEST_COUNT_SUCCESS || category == EventCategoryEnum.events)) {
                    viewModelState.update { state ->
                        state.copy(
                            dataRequestState = DataRequestState.CompletedSuccessfully(),
                        )
                    }
                }
                count++
                Log.d(TAG, "Fetching complete with count: ${collector.count()}")
            }
            .onStart {
                Log.d(TAG, "Fetching started for ${category.name}")
            }
            .catch {
                Log.d(TAG, "Fetching errored out for ${category.name}: ${it.message}")
            }
            .collect { historicalEvent ->
                if (historicalEvent.isEmpty()) {
                    Log.d(TAG, "Fetching error for ${category.name}")
                }
                collector.addAll(historicalEvent)
            }
    }

    private fun collectHistory(dataRequestParams: DataRequestParams) {
        viewModelState.update { state ->
            state.copy(
                dataRequestState = DataRequestState.Started,
            )
        }

        val handler = CoroutineExceptionHandler { _, exception ->
            println("collectHistory CoroutineExceptionHandler got $exception")
        }

        count = 0
        viewModelScope.launch(handler) {
            enumValues<EventCategoryEnum>().forEach { eventCategory ->
                Log.d(
                    TAG,
                    "Requesting fetching data for $eventCategory" +
                            " and language: ${dataRequestParams.langEnum}" +
                            " on month: ${dataRequestParams.month}" +
                            " and day: ${dataRequestParams.day}"
                )
                async {
                    historyDataMap.historyDataMap[eventCategory]?.let { snapshotList ->
                        snapshotList.clear()
                        collect(dataRequestParams.month,
                            dataRequestParams.day,
                            dataRequestParams.langEnum,
                            eventCategory,
                            snapshotList)
                    }
                }
            }
        }
    }

    private fun setDataRequestState(dataRequestState: DataRequestState){
        viewModelState.update { state ->
            state.copy(
                dataRequestState = dataRequestState
            )
        }
    }

    private fun updateCategory(optionSelected: String) {
        _dataRequestParams = _dataRequestParams.copy(
            category = CatsByLanguage(lang).getEnglishCategoryFromValue(optionSelected)
        )

        viewModelScope.launch {
            viewModelState.update { state ->
                state.copy(
                    previousCategory = state.selectedCategory,
                    selectedCategory = optionSelected
                )
            }
        }
    }

   /* @AssistedFactory
    interface IHistoryViewModelFactory {
        fun create(language: LangEnum): HistoryViewModel
    }*/
    companion object {
        const val TAG = "HistoryViewModel"
        const val REQUEST_COUNT_SUCCESS = 3
        const val REQUEST_RETRY_COUNT = 3
        const val REQUEST_RETRY_DELAY = 200L
    }
}