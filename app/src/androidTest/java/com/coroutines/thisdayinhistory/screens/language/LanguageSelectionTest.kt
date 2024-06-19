package com.coroutines.thisdayinhistory.screens.language

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import assertTextColor
import com.coroutines.data.models.LangEnum
import com.coroutines.thisdayinhistory.ui.constants.LANGUAGE_SELECTION_TEXT_TAG
import com.coroutines.thisdayinhistory.ui.screens.language.LanguageSelection
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryTheme
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModelMock
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LanguageSelectionTest {

    @get:Rule
    val composeTestRule = createEmptyComposeRule()
    private var wasCalled = false
    private lateinit var scenario: ActivityScenario<ComponentActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(ComponentActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    private fun initComposable() {
        scenario.onActivity { activity ->
            val settingsViewModel = SettingsViewModelMock()
            activity.setContent {
                ThisDayInHistoryTheme(viewModel = settingsViewModel ) {
                    LanguageSelection(LangEnum.GERMAN, Modifier) {
                        wasCalled = true
                    }
                }
            }
        }
    }

    private fun initComposableInDarkMode() {
        scenario.onActivity { activity ->
            val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Dark)
            activity.setContent {
                ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                    LanguageSelection(LangEnum.GERMAN, Modifier) {
                        wasCalled = true
                    }
                }
            }
        }
    }

    @Test
    fun allLanguagesDisplayedTest(){
        initComposable()
        val languageCount = LangEnum.entries.toTypedArray().count()
        composeTestRule
            .onAllNodesWithTag(LANGUAGE_SELECTION_TEXT_TAG)
            .assertCountEquals(languageCount)

    }
    @Test
    fun languageNativeNameIsDisplayedTest() {
        initComposable()
        composeTestRule
            .onNodeWithText(LangEnum.GERMAN.langNativeName)
            .assertIsDisplayed()

    }

    @Test
    fun languageClickableTest() {
        initComposable()
        composeTestRule.onNodeWithText(LangEnum.GERMAN.langNativeName).assertHasClickAction()
    }

    @Test
    fun languagePerformClickTest() {
        initComposable()
        composeTestRule.onNodeWithText(LangEnum.GERMAN.langNativeName).performClick()
        assert(wasCalled)
    }

    @Test
    fun languageTextColorInLightModeTest() {
        initComposable()
        composeTestRule
            .onNodeWithText(LangEnum.GERMAN.langNativeName)
            .assertTextColor(Color.Black)
    }

    @Test
    fun languageTextColorInDarkModeTest() {
        initComposableInDarkMode()
        composeTestRule
            .onNodeWithText(LangEnum.GERMAN.langNativeName)
            .assertTextColor(Color.White)
    }
}