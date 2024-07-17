package com.coroutines.thisdayinhistory.screens.main


import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertLeftPositionInRootIsEqualTo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coroutines.data.models.CountryCodeMapping
import com.coroutines.data.models.HistoricalEvent
import com.coroutines.thisdayinhistory.ui.screens.main.HistoryListItem
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryTheme
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModelMock
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class HistoryListItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private var wasCalled = false
    private val historicalEvent = HistoricalEvent(
        description = "this is an Italy sample",
        extract = "this is a test",
        shortTitle = "this is short title",
        countryCodeMappings = buildList(1) {
            add(CountryCodeMapping("Italy"))
        },
        year = "1865"
    )

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Test
    fun historyListItemShouldDisplay() = runTest {
        val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Light)
        composeTestRule.setContent {
            ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                val appSettingsState =
                    settingsViewModel.appConfigurationState.collectAsStateWithLifecycle()
                HistoryListItem(
                    historyEvent = historicalEvent,
                    windowSizeClass = WindowSizeClass.calculateFromSize(DpSize.Zero),
                    onClick = { wasCalled = true },
                    onImageClick = {}
                ) {

                }
            }
        }

        composeTestRule
            .onNodeWithTag("historyListItem")
            .assertIsDisplayed()
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Test
    fun historyListItemDescriptionShouldDisplay() = runTest {
        composeTestRule.setContent {
            val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Light)
            ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                HistoryListItem(
                    historyEvent = historicalEvent,
                    windowSizeClass = WindowSizeClass.calculateFromSize(DpSize.Zero),
                    onClick = { wasCalled = true },
                    onImageClick = {}
                ) {

                }
            }
        }

        composeTestRule
            .onNodeWithText(historicalEvent.description)
            .assertIsDisplayed()
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Test
    fun historyListItemDescriptionShouldBeClickable() = runTest {
        composeTestRule.setContent {
            val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Light)
            ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                HistoryListItem(
                    historyEvent = historicalEvent,
                    windowSizeClass = WindowSizeClass.calculateFromSize(DpSize.Zero),
                    onClick = { wasCalled = true },
                    onImageClick = {}
                ) {

                }
            }
        }

        composeTestRule
            .onNodeWithText(historicalEvent.description)
            .assertHasClickAction()
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Test
    fun historyListItemDescriptionOnClickShouldBeCalled() = runTest {
        composeTestRule.setContent {
            HistoryListItem(
                historyEvent = historicalEvent,
                windowSizeClass = WindowSizeClass.calculateFromSize(DpSize.Zero),
                onClick = { wasCalled = true },
                onImageClick =  {}
            ) {

            }
        }

        composeTestRule
            .onNodeWithText(historicalEvent.description)
            .performClick()

        assert(wasCalled)
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Test
    fun historyListItemRowPaddingTest() {
        composeTestRule.setContent {
            HistoryListItem(
                historyEvent = historicalEvent,
                windowSizeClass = WindowSizeClass.calculateFromSize(DpSize.Zero),
                onClick = { wasCalled = true },
                onImageClick =  {}
            ) {

            }
        }

        composeTestRule
            .onNodeWithTag("historyListItemRow", useUnmergedTree = true)
            .assertLeftPositionInRootIsEqualTo(16.dp)

    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Test
    fun historyListItemContentColorTest() = runTest {
        val onBackground = Color.Black
        var content = Color.Transparent
        val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Light)
        composeTestRule.setContent {
            ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                content = LocalContentColor.current
                HistoryListItem(
                    historyEvent = historicalEvent,
                    windowSizeClass = WindowSizeClass.calculateFromSize(DpSize.Zero),
                    onClick = { wasCalled = true },
                    onImageClick =  {}
                ) {

                }
            }
        }

        assert(content == onBackground)


    }
}