package com.coroutines.thisdayinhistory.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.DarkMode
import androidx.compose.ui.test.DeviceConfigurationOverride
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import assertTextColor
import com.coroutines.thisdayinhistory.R
import com.coroutines.thisdayinhistory.ui.components.CatLogo
import com.coroutines.thisdayinhistory.ui.constants.CAT_LOGO_HEADER_TEXT_TAG
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryTheme
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModelMock
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class CatLogoThemeTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun catLogoImageInDarkMode()  {
        val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Dark)
        composeTestRule.setContent {
            val appSettingsState = settingsViewModel.appConfigurationState.collectAsStateWithLifecycle()
            CatLogo(appSettingsState.value)
        }

        composeTestRule
            .onNodeWithTag(R.drawable.cat_logo_for_dark_theme.toString())
            .assertIsDisplayed()
    }

    @Test
    fun catLogoHeaderColorInDarkMode() {
        val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Dark)
        composeTestRule.setContent {
            ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                val appSettingsState = settingsViewModel.appConfigurationState.collectAsStateWithLifecycle()
                CatLogo(appSettingsState.value, true)
            }
        }

        composeTestRule
            .onNodeWithTag(CAT_LOGO_HEADER_TEXT_TAG)
            .assertIsDisplayed()
            .assertTextColor(Color.White)
    }

    @Test
    fun catLogoImageInLightMode()  {
        val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Light)
        composeTestRule.setContent {
            val appSettingsState = settingsViewModel.appConfigurationState.collectAsStateWithLifecycle()
            CatLogo(appSettingsState.value)
        }

        composeTestRule
            .onNodeWithTag(R.drawable.cat_logo_for_light_theme.toString())
            .assertIsDisplayed()
    }

    @Test
    fun catLogoImageInAutoThemeInDarkMode()  {
        composeTestRule.setContent {
            DeviceConfigurationOverride(
                DeviceConfigurationOverride.DarkMode(true)
            ) {
            val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Auto)
            ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                val appSettingsState =
                    settingsViewModel.appConfigurationState.collectAsStateWithLifecycle()
                val appThemeColor = MaterialTheme.colorScheme.background
                Surface (modifier = Modifier.fillMaxSize().background(appThemeColor)) {
                    CatLogo(appSettingsState.value)
                    }
                }
            }
        }

        composeTestRule
            .onNodeWithTag(R.drawable.cat_logo_for_dark_theme.toString())
            .assertIsDisplayed()
    }

    @Test
    fun catLogoImageInAutoThemeInLightMode()  {
        composeTestRule.setContent {
            DeviceConfigurationOverride(
                DeviceConfigurationOverride.DarkMode(false)
            ) {
                val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Auto)
                ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                    val appSettingsState =
                        settingsViewModel.appConfigurationState.collectAsStateWithLifecycle()
                    val appThemeColor = MaterialTheme.colorScheme.background
                    Surface (modifier = Modifier.fillMaxSize().background(appThemeColor)) {
                        CatLogo(appSettingsState.value)
                    }
                }
            }
        }

        composeTestRule
            .onNodeWithTag(R.drawable.cat_logo_for_light_theme.toString())
            .assertIsDisplayed()
    }
}