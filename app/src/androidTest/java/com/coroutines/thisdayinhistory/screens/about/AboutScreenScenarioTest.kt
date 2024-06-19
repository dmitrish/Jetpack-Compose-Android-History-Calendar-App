package com.coroutines.thisdayinhistory.screens.about

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.LocalContentColor
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.core.app.ActivityScenario
import assertTextColor
import com.coroutines.data.models.LangEnum
import com.coroutines.data.models.Languages
import com.coroutines.thisdayinhistory.ui.constants.ABOUT_SCREEN_COLUMN_TAG
import com.coroutines.thisdayinhistory.ui.constants.ABOUT_SCREEN_TEXT_TAG
import com.coroutines.thisdayinhistory.ui.screens.about.AboutScreen
import com.coroutines.thisdayinhistory.ui.theme.BabyPowder
import com.coroutines.thisdayinhistory.ui.theme.DarkGray
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryTheme
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum
import com.coroutines.thisdayinhistory.ui.viewmodels.ISettingsViewModel
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModelMock
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AboutScreenScenarioTest {
    @get:Rule
    val composeTestRule = createEmptyComposeRule()
    private val aboutScreenTag = ABOUT_SCREEN_TEXT_TAG
    private val aboutScreenColumnTag = ABOUT_SCREEN_COLUMN_TAG
    private val onDarkBackground = Color.White
    private val darkBackground = DarkGray
    private var contentColor = Color.Transparent
    private lateinit var scenario: ActivityScenario<ComponentActivity>
    private lateinit var settingsViewModel : ISettingsViewModel
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
            settingsViewModel =  SettingsViewModelMock(ThisDayInHistoryThemeEnum.Light)
            activity.setContent {
                ThisDayInHistoryTheme(viewModel = settingsViewModel ) {
                    AboutScreen( Modifier, settingsViewModel)
                }
                contentColor = LocalContentColor.current
            }
        }
    }

    private fun initComposableInDarkMode() {
        scenario.onActivity { activity ->
            settingsViewModel =  SettingsViewModelMock(ThisDayInHistoryThemeEnum.Dark)
            activity.setContent {
                ThisDayInHistoryTheme(viewModel = settingsViewModel ) {
                    AboutScreen( Modifier, settingsViewModel)
                }
                contentColor = LocalContentColor.current
            }

        }
    }

    @Test
    fun languageSetPerAppLanguageTest_French() {
        val expectedText = Languages.from(LangEnum.FRENCH.langId)?.appDescription ?: "~+!"
        initComposable()
        settingsViewModel.setAppLanguage(LangEnum.FRENCH)
        composeTestRule
            .onNodeWithTag(aboutScreenTag)
            .assertIsDisplayed()
            .assertTextContains(expectedText)

    }
    @Test
    fun languageSetPerAppLanguageTest_Russian() {
        val expectedText = Languages.from(LangEnum.RUSSIAN.langId)?.appDescription ?: "~+!"
        initComposable()
        settingsViewModel.setAppLanguage(LangEnum.RUSSIAN)

        composeTestRule
            .onNodeWithTag(aboutScreenTag)
            .assertIsDisplayed()
            .assertTextContains(expectedText)

    }

    @Test
    fun colorInLightModeTest() {
        initComposable()
        composeTestRule
            .onNodeWithTag(aboutScreenTag)
            .assertIsDisplayed()
            .assertTextColor(Color.Black)
    }
    @Test
    fun colorInDarkModeTest() {
        initComposableInDarkMode()
        composeTestRule
            .onNodeWithTag(aboutScreenTag)
            .assertIsDisplayed()
            .assertTextColor(Color.White)
    }

    @Test
    fun colorColumnInDarkModeTest() {
        initComposableInDarkMode()
        composeTestRule
            .onNodeWithTag(aboutScreenColumnTag)
            .assertIsDisplayed()

        assert(contentColor == Color.Black)
    }

    @Test
    fun colorColumnInLightModeTest() {
        initComposable()
        composeTestRule
            .onNodeWithTag(aboutScreenColumnTag)
            .assertIsDisplayed()

        assert(contentColor == Color.Black)
    }
}