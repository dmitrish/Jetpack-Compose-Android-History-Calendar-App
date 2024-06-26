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
import com.coroutines.data.models.OnboardingStatusEnum
import com.coroutines.models.wiki.OriginalImage
import com.coroutines.models.wiki.Page
import com.coroutines.models.wiki.Thumbnail
import com.coroutines.thisdayinhistory.ui.state.DataRequestState
import com.coroutines.thisdayinhistory.ui.state.HistoryViewModelState
import com.coroutines.thisdayinhistory.uimodels.CatsByLanguage
import com.coroutines.thisdayinhistory.uimodels.SelectedDate
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

private data class HistoryStateMock(
    val dataRequestState: DataRequestState = DataRequestState.NotStarted,
    val selectedCategory: String = CatsByLanguage(LangEnum.ENGLISH).getDefaultCategory(),
    val previousCategory: String = CatsByLanguage(LangEnum.ENGLISH).getDefaultCategory(),
    val selectedItem: HistoricalEvent = HistoricalEvent(description = "No Events"),
    val selectedDate: SelectedDate = SelectedDate("June", 22),
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
    private val viewModelState = MutableStateFlow(value = HistoryStateMock())
    init{

        viewModelState.update { state ->
            state.copy(
                dataRequestState = DataRequestState.Started
            )
        }
        data.add(HistoricalEvent(
            description = "Nik Wallenda becomes the first man to successfully walk across the Grand Canyon on a tight rope.",
            countryCodeMappings = buildList {
                CountryCodeMapping("USA", alpha2 = "US")
            },
            year = "2022",
            imageBigUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Nik-Wallenda-Skyscraper-Live.jpg/320px-Nik-Wallenda-Skyscraper-Live.jpg",
            originalImage = OriginalImage(200, "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Nik-Wallenda-Skyscraper-Live.jpg/320px-Nik-Wallenda-Skyscraper-Live.jpg", 200),
            shortTitle = "Grand Canyon",
            extract = "extract test",
            pages = listOf ( Page(
                description = "test",
                extract = "The Grand Canyon is a steep-sided canyon carved by the Colorado River in Arizona, United States. The Grand Canyon is 277 miles (446 km) long, up to 18 miles (29 km) wide and attains a depth of over a mile.",
                extractHtml = "test",
                title = "Grand_Canyon",
                pageId = 1,
                thumbnail = Thumbnail(320, "https://upload.wikimedia.org/wikipedia/commons/thumb/3/31/Canyon_River_Tree_%28165872763%29.jpeg/320px-Canyon_River_Tree_%28165872763%29.jpeg", width = 427),
                originalImage = OriginalImage(400, "https://upload.wikimedia.org/wikipedia/commons/thumb/3/31/Canyon_River_Tree_%28165872763%29.jpeg/320px-Canyon_River_Tree_%28165872763%29.jpeg", width = 400)
            ),
            Page(
                description = "test",
                extract = "Tightrope walking, also called funambulism, is the skill of walking along a thin wire or rope. It has a long tradition in various countries and is commonly associated with the circus. Other skills similar to tightrope walking include slack rope walking and slacklining.",
                extractHtml = "test",
                title = "Tightrope walking",
                pageId = 2,
                thumbnail = Thumbnail(320, "https://upload.wikimedia.org/wikipedia/commons/thumb/d/dc/Tightrope_walking.jpg/320px-Tightrope_walking.jpg", width = 427),
                originalImage = OriginalImage(400, "https://upload.wikimedia.org/wikipedia/commons/thumb/d/dc/Tightrope_walking.jpg/320px-Tightrope_walking.jpg", width = 400)
            ))

            ))

        data.add(HistoricalEvent(
            description = "Israeli archaeologists discover the tomb of Herod the Great south of Jerusalem.",
            countryCodeMappings = buildList {
                CountryCodeMapping("Israel", alpha2 = "IL")
            },
            year = "2007",
            imageBigUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Flag_of_Israel.svg/320px-Flag_of_Israel.svg.png",
            originalImage = OriginalImage(200, "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Flag_of_Israel.svg/320px-Flag_of_Israel.svg.png", 200),
            shortTitle = "Herod the Great",
            extract = "extract test",
            pages = listOf (
                Page(
                    description = "test",
                    extract = "Herod I or Herod the Great was a Roman Jewish client king of the Herodian Kingdom of Judea. He is known for his colossal building projects throughout Judea. Among these works are the rebuilding of the Second Temple in Jerusalem and the expansion of its base—the Western Wall being part of it. Vital details of his life are recorded in the works of the 1st century CE Roman–Jewish historian Josephus.",
                    extractHtml = "test",
                    title = "Herod_the_Great",
                    pageId = 1,
                    thumbnail = Thumbnail(320, "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ee/Herods_grave_israel_museum_2013_mrach_by_history_on_the_map_efi_elian.jpg/320px-Herods_grave_israel_museum_2013_mrach_by_history_on_the_map_efi_elian.jpg", width = 427),
                    originalImage = OriginalImage(667, "https://upload.wikimedia.org/wikipedia/commons/e/ee/Herods_grave_israel_museum_2013_mrach_by_history_on_the_map_efi_elian.jpg", width = 1000)
                ),
                Page(
                    description = "Jerusalem",
                    extract = "Jerusalem is a city in the Southern Levant, on a plateau in the Judaean Mountains between the Mediterranean and the Dead Sea. It is one of the oldest cities in the world, and is considered holy to the three major Abrahamic religions—Judaism, Christianity, and Islam. Both the State of Israel and the State of Palestine claim Jerusalem as their capital city. Israel maintains its primary governmental institutions there, and the State of Palestine ultimately foresees it as its seat of power. Neither claim is widely recognized internationally.",
                    extractHtml = "test",
                    title = "Jerusalem",
                    pageId = 1,
                    thumbnail = Thumbnail(320, "https://upload.wikimedia.org/wikipedia/commons/thumb/9/94/%D7%94%D7%9E%D7%A6%D7%95%D7%93%D7%94_%D7%91%D7%9C%D7%99%D7%9C%D7%94.jpg/320px-%D7%94%D7%9E%D7%A6%D7%95%D7%93%D7%94_%D7%91%D7%9C%D7%99%D7%9C%D7%94.jpg", width = 427),
                    originalImage = OriginalImage(667, "https://upload.wikimedia.org/wikipedia/commons/thumb/9/94/%D7%94%D7%9E%D7%A6%D7%95%D7%93%D7%94_%D7%91%D7%9C%D7%99%D7%9C%D7%94.jpg/320px-%D7%94%D7%9E%D7%A6%D7%95%D7%93%D7%94_%D7%91%D7%9C%D7%99%D7%9C%D7%94.jpg", width = 1000)
                ),

                )
        ))
        data.add(HistoricalEvent(
            description = "The prime minister of Bangladesh, Sheikh Hasina inaugurates the longest bridge of Bangladesh, Padma Bridge.",
            countryCodeMappings = buildList {
                CountryCodeMapping("UK", alpha2 = "UK")
            },
            year = "1992",
            imageBigUrl = "https://upload.wikimedia.org/wikipedia/commons/c/c7/Sheikh_Hasina_in_Sep_2023.jpg",
            originalImage = OriginalImage(1567, "https://upload.wikimedia.org/wikipedia/commons/c/c7/Sheikh_Hasina_in_Sep_2023.jpg", 828),
            shortTitle = "Shiekh Hasina",
            extract = "extract test",
            pages = listOf ( Page(
                description = "test",
                extract = "Sheikh Hasina Wazed is a Bangladeshi politician who has served as the tenth prime minister of Bangladesh from June 1996 to July 2001 and again since January 2009. She is the daughter of Sheikh Mujibur Rahman, the founding father and first president of Bangladesh. Having served for a combined total of over 20 years, she is the longest serving prime minister in the history of Bangladesh. As of 23 June 2024, she is the world's longest-serving female head of government",
                extractHtml = "test",
                title = "Sheikh_Hasina",
                pageId = 1,
                thumbnail = Thumbnail(320, "https://upload.wikimedia.org/wikipedia/commons/c/c7/Sheikh_Hasina_in_Sep_2023.jpg", width = 427),
                originalImage = OriginalImage(400, "https://upload.wikimedia.org/wikipedia/commons/c/c7/Sheikh_Hasina_in_Sep_2023.jpg", width = 400)
            ))
        ))
        data.add(HistoricalEvent(
            description = "Kim Campbell is sworn in as the first female Prime Minister of Canada.",
            countryCodeMappings = buildList {
                CountryCodeMapping("UK", alpha2 = "UK")
            },
            year = "1992",
            imageBigUrl = "https://upload.wikimedia.org/wikipedia/commons/0/07/Kim_Campbell.jpg",
            originalImage = OriginalImage(2998, "https://upload.wikimedia.org/wikipedia/commons/0/07/Kim_Campbell.jpg", 1862),
            shortTitle = "Kim Campbell",
            extract = "extract test",
            pages = listOf ( Page(
                description = "test",
                extract = "Avril Phaedra Douglas \\\"Kim\\\" Campbell is a former Canadian politician, diplomat, lawyer, and writer who served as the 19th prime minister of Canada from June 25 to November 4, 1993. Campbell is the first and only female prime minister of Canada. Prior to becoming the final Progressive Conservative (PC) prime minister, she was also the first woman to serve as minister of justice in Canadian history and the first woman to become minister of defence in a NATO member state.",
                extractHtml = "test",
                title = "Kim Campbell",
                pageId = 1,
                thumbnail = Thumbnail(320, "https://upload.wikimedia.org/wikipedia/commons/0/07/Kim_Campbell.jpg", width = 427),
                originalImage = OriginalImage(2998, "https://upload.wikimedia.org/wikipedia/commons/0/07/Kim_Campbell.jpg", width = 1862)
            ))
        ))

        data.add(HistoricalEvent(
            description = "Betty Boothroyd becomes the first woman to be elected Speaker of the British House of Commons in its 700-year history.",
            countryCodeMappings = buildList {
                CountryCodeMapping("UK", alpha2 = "UK")
            },
            year = "1992",
            imageBigUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8f/Official_portrait_of_Baroness_Boothroyd_%28cropped%29.jpg/320px-Official_portrait_of_Baroness_Boothroyd_%28cropped%29.jpg",
            originalImage = OriginalImage(200, "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8f/Official_portrait_of_Baroness_Boothroyd_%28cropped%29.jpg/320px-Official_portrait_of_Baroness_Boothroyd_%28cropped%29.jpg", 200),
            shortTitle = "Betty Boothroyd",
            extract = "extract test",
            pages = listOf ( Page(
                description = "test",
                extract = "The 1992 election of the Speaker of the House of Commons occurred on 27 April 1992, in the first sitting of the House of Commons following the 1992 general election and the retirement of the previous Speaker Bernard Weatherill. The election resulted in the election of Labour MP Betty Boothroyd, one of Weatherill's deputies, who was the first woman to become Speaker. This was at a time when the Conservative Party had a majority in the House of Commons. It was also the first contested election since William Morrison defeated Major James Milner on 31 October 1951, although Geoffrey de Freitas had been nominated against his wishes in the 1971 election.",
                extractHtml = "test",
                title = "1992_Speaker_of_the_British_House_of_Commons_election",
                pageId = 1,
                thumbnail = Thumbnail(320, "https://upload.wikimedia.org/wikipedia/commons/f/f2/Official_portrait_of_Baroness_Boothroyd_crop_2.jpg", width = 427),
                originalImage = OriginalImage(400, "https://upload.wikimedia.org/wikipedia/commons/f/f2/Official_portrait_of_Baroness_Boothroyd_crop_2.jpg", width = 400)
            ))
        ))
        data.add(HistoricalEvent(
            description = "The famous Hand of God goal, scored by Diego Maradona in the quarter-finals of the 1986 FIFA World Cup match between Argentina and England, ignites controversy. This was later followed by the Goal of the Century. Argentina wins 2–1 and later goes on to win the World Cup.",
            year = "1986",
            imageBigUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/42/Maradona_shilton_mano_dios.jpg/320px-Maradona_shilton_mano_dios.jpg",
            originalImage = OriginalImage(200, "https://upload.wikimedia.org/wikipedia/commons/thumb/4/42/Maradona_shilton_mano_dios.jpg/320px-Maradona_shilton_mano_dios.jpg", 200),
            shortTitle = "Hand of God",
            extract = "extract test",

            pages = listOf (
                Page(
                    description = "test",
                    extract = "Diego Armando Maradona was an Argentine professional football player and manager. Widely regarded as one of the greatest players in the history of the sport, he was one of the two joint winners of the FIFA Player of the 20th Century award.",
                    extractHtml = "test",
                    title = "Diego_Maradona",
                    pageId = 1,
                    thumbnail = Thumbnail(320, "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Maradona-Mundial_86_con_la_copa.JPG/320px-Maradona-Mundial_86_con_la_copa.JPG", width = 427),
                    originalImage = OriginalImage(667, "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Maradona-Mundial_86_con_la_copa.JPG/320px-Maradona-Mundial_86_con_la_copa.JPG", width = 1000)
                ),
                Page(
                    description = "1986 World Cup",
                    extract = "The 1986 FIFA World Cup was the 13th FIFA World Cup, a quadrennial football tournament for men's senior national teams. It was played in Mexico from 31 May to 29 June 1986. The tournament was the second to feature a 24-team format. Colombia had been originally chosen to host the competition by FIFA but, largely due to economic reasons, was not able to do so, and resigned in November 1982. Mexico was selected as the new host in May 1983, and became the first country to host the World Cup more than once, after previously hosting the 1970 edition.",
                    extractHtml = "test",
                    title = "1986_FIFA_World_Cup",
                    pageId = 1,
                    thumbnail = Thumbnail(320, "https://upload.wikimedia.org/wikipedia/en/thumb/7/77/1986_FIFA_World_Cup.svg/320px-1986_FIFA_World_Cup.svg.png", width = 427),
                    originalImage = OriginalImage(667, "https://upload.wikimedia.org/wikipedia/en/thumb/7/77/1986_FIFA_World_Cup.svg/320px-1986_FIFA_World_Cup.svg.png", width = 1000)
                ),

            ))
        )





        viewModelState.update { state ->
            state.copy(
                dataRequestState = DataRequestState.CompletedSuccessfully()
            )
        }

    }

    override val historyData: SnapshotStateList<HistoricalEvent>
        get() = data
    override var isScrolled = mutableStateOf(false)
    override var filterKey: String = ""
    override var selectedItem: HistoricalEvent = HistoricalEvent("No Events")
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