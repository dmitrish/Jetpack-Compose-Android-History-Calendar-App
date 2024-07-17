package com.coroutines.thisdayinhistory.components

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ActivityScenario
import com.coroutines.thisdayinhistory.R
import com.coroutines.thisdayinhistory.ui.components.HistoryDatePickerDialog
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryTheme
import com.coroutines.thisdayinhistory.ui.viewmodels.HistoryViewModelMock
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModelMock
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HistoryDatePickerTest {
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
                    historyViewModel.selectedItem = historyViewModel.historyData[0]
                    HistoryDatePickerDialog(
                        viewModel = historyViewModel,
                        resIdCancel = R.string.cancel,
                        onDateSelected = { } ,
                        onDismiss = { }
                    )
                }
            }
        }
    }

    @Test
    fun datePickerDialogShouldDisplay(){
        initComposable()
        composeTestRule
            .onNodeWithText("datePickerDialog", true)
            .assertIsDisplayed()
    }

}