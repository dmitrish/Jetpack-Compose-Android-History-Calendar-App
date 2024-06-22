package com.coroutines.thisdayinhistory.ui.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutines.data.models.CountryCodeMapping
import com.coroutines.data.models.HistoricalEvent
import com.coroutines.data.models.LangEnum
import com.coroutines.models.wiki.OriginalImage
import com.coroutines.thisdayinhistory.ui.state.DataRequestState
import com.coroutines.thisdayinhistory.ui.state.HistoryViewModelState
import com.coroutines.thisdayinhistory.uimodels.CatsByLanguage
import com.coroutines.thisdayinhistory.uimodels.SelectedDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime

private data class HistoryStateMock(
    val dataRequestState: DataRequestState = DataRequestState.NotStarted,
    val selectedCategory: String = CatsByLanguage(LangEnum.ENGLISH).getDefaultCategory(),
    val previousCategory: String = CatsByLanguage(LangEnum.ENGLISH).getDefaultCategory(),
    val selectedItem: HistoricalEvent = HistoricalEvent(description = "No Events"),
    val selectedDate: SelectedDate = SelectedDate("March", 1),
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
        filter =  filter
    )
}
class HistoryViewModelMock : ViewModel(), IHistoryViewModel {
    private val data = mutableStateListOf<HistoricalEvent>()
    private val isScrolledState = mutableStateOf(false)
    init{
        data.add(HistoricalEvent(
            description = "The worst day of the tornado outbreak sequence of April 25–28, 2024, with 42 tornadoes, including one confirmed EF4 tornado, and two confirmed EF3 tornadoes, which killed 4 people in total.",
            year = "2023",
            imageBigUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/34/Tornado_outbreak_sequence_of_April_25%E2%80%9328%2C_2024.png/320px-Tornado_outbreak_sequence_of_April_25%E2%80%9328%2C_2024.png",
            originalImage = OriginalImage(200, "https://upload.wikimedia.org/wikipedia/commons/thumb/3/34/Tornado_outbreak_sequence_of_April_25%E2%80%9328%2C_2024.png/320px-Tornado_outbreak_sequence_of_April_25%E2%80%9328%2C_2024.png", 200),
            shortTitle = "short title test",
            extract = "extract test",

            ))
        data.add(HistoricalEvent(
            description = "The Panmunjom Declaration is signed between North and South Korea, officially declaring their intentions to end the Korean conflict.",
            countryCodeMappings = buildList {
                CountryCodeMapping("Israel", alpha2 = "KR")
            },
            year = "2022",
            imageBigUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7d/Korea_Summit_2018_v3.jpg/320px-Korea_Summit_2018_v3.jpg",
            originalImage = OriginalImage(200, "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7d/Korea_Summit_2018_v3.jpg/320px-Korea_Summit_2018_v3.jpg", 200),
            shortTitle = "short title test",
            extract = "extract test",

            ))
        data.add(HistoricalEvent(
            description = "Israeli archaeologists discover the tomb of Herod the Great south of Jerusalem.",
            countryCodeMappings = buildList {
                CountryCodeMapping("Israel", alpha2 = "IL")
            },
            year = "1864",
            imageBigUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Flag_of_Israel.svg/320px-Flag_of_Israel.svg.png",
            originalImage = OriginalImage(200, "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Flag_of_Israel.svg/320px-Flag_of_Israel.svg.png", 200),
            shortTitle = "short title test",
            extract = "extract test",

            ))
        data.add(HistoricalEvent(
            description = "Betty Boothroyd becomes the first woman to be elected Speaker of the British House of Commons in its 700-year history.",
            countryCodeMappings = buildList {
                CountryCodeMapping("UK", alpha2 = "UK")
            },
            year = "1864",
            imageBigUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8f/Official_portrait_of_Baroness_Boothroyd_%28cropped%29.jpg/320px-Official_portrait_of_Baroness_Boothroyd_%28cropped%29.jpg",
            originalImage = OriginalImage(200, "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8f/Official_portrait_of_Baroness_Boothroyd_%28cropped%29.jpg/320px-Official_portrait_of_Baroness_Boothroyd_%28cropped%29.jpg", 200),
            shortTitle = "short title test",
            extract = "extract test",

            ))
    }
    private val viewModelState = MutableStateFlow(value = HistoryStateMock())
    override val historyData: SnapshotStateList<HistoricalEvent>
        get() = data
    override var isScrolled: MutableState<Boolean>
        get() = isScrolledState
        set(value) {}
    override var filterKey: String
        get() = TODO("Not yet implemented")
        set(value) {}
    override var selectedItem: HistoricalEvent
        get() = data[0]
        set(value) {}
    override val uiState = viewModelState
        .map {it.asActivityState() }
        .stateIn (
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = viewModelState.value.asActivityState()
        )


    override fun onDateChanged(localDateTime: LocalDateTime) {
        TODO("Not yet implemented")
    }

    override fun updateDate(count: Int) {
        TODO("Not yet implemented")
    }

    override fun onCategoryChanged(optionSelected: String) {
        TODO("Not yet implemented")
    }

    override fun search(searchTerm: String) {
        TODO("Not yet implemented")
    }
}