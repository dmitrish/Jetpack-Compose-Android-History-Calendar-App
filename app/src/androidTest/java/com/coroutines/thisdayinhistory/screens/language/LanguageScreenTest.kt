package com.coroutines.thisdayinhistory.screens.language

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ActivityScenario
import assertAreDisplayed
import com.coroutines.data.models.LangEnum
import com.coroutines.thisdayinhistory.ui.constants.LANGUAGE_SELECTION_TEXT_TAG
import com.coroutines.thisdayinhistory.ui.screens.language.LanguageScreen
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryTheme
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModelMock
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LanguageScreenTest {

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
                val navController = rememberNavController()
                ThisDayInHistoryTheme(viewModel = settingsViewModel ) {
                    LanguageScreen(
                        navController = navController,
                        viewModel = settingsViewModel
                    )
                }
            }
        }
    }

    @Test
    fun allLanguagesCountTest(){
        initComposable()
        val languageCount = LangEnum.entries.toTypedArray().count()
        composeTestRule
            .onAllNodesWithTag(LANGUAGE_SELECTION_TEXT_TAG)
            .assertCountEquals(languageCount)
    }

    @Test
    fun allLanguagesDisplayedTest(){
        initComposable()
        composeTestRule
            .onAllNodesWithTag(LANGUAGE_SELECTION_TEXT_TAG)
            .assertAreDisplayed()

    }
    @Test
    fun languageNativeNameIsDisplayedTest() {
        initComposable()
        for (entry in LangEnum.entries) {
            composeTestRule.onNodeWithText(entry.langNativeName).assertIsDisplayed()
        }

    }

    @Test
    fun languageClickableTest() {
        initComposable()
        for (entry in LangEnum.entries) {
            composeTestRule.onNodeWithText(entry.langNativeName).assertHasClickAction()
        }
    }

    /*@Test
    fun languagePerformClickTest() {
        initComposable()
        composeTestRule.onNodeWithText(LangEnum.GERMAN.langNativeName).performClick()
        assert(wasCalled)
    }*/
}