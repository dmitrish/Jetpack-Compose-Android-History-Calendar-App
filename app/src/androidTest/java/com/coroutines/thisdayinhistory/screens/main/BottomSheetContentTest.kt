package com.coroutines.thisdayinhistory.screens.main

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ActivityScenario
import com.coroutines.thisdayinhistory.ui.components.MinContrastOfPrimaryVsSurface
import com.coroutines.thisdayinhistory.ui.components.contrastAgainst
import com.coroutines.thisdayinhistory.ui.components.rememberDominantColorState
import com.coroutines.thisdayinhistory.ui.screens.main.BottomSheetContent
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryTheme
import com.coroutines.thisdayinhistory.ui.viewmodels.HistoryViewModelMock
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModelMock
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BottomSheetContentTest {

    @get:Rule
    val composeTestRule = createEmptyComposeRule()
    private var wasCalled = false
    private lateinit var scenario: ActivityScenario<ComponentActivity>
    private val historyViewModel = HistoryViewModelMock()

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
                    historyViewModel.selectedItem = historyViewModel.historyData.get(0)

                    val backgroundColor = MaterialTheme.colorScheme.background
                    val dominantColorState = rememberDominantColorState { color ->
                        color.contrastAgainst(backgroundColor) >= MinContrastOfPrimaryVsSurface
                    }

                    BottomSheetContent(
                        viewModel = historyViewModel,
                        readyState = true,
                        dominantColorState = dominantColorState
                    )
                }
            }
        }
    }

    @Test
    fun bottomSheetTitleShouldDisplay(){
        initComposable()
        composeTestRule
            .onNodeWithText("bottomSheetContentText", true)
            .assertIsDisplayed()
    }

}