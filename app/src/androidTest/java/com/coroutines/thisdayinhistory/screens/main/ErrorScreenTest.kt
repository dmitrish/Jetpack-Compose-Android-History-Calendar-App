package com.coroutines.thisdayinhistory.screens.main

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.DeviceConfigurationOverride
import androidx.compose.ui.test.Locales
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.text.intl.LocaleList
import com.coroutines.thisdayinhistory.R
import com.coroutines.thisdayinhistory.ui.screens.main.HistoryScreenError
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryTheme
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModelMock
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test


class ErrorScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private val errorMessage = "test"
    private var expectedDisplayedErrorMessage = ""


    @Test
    fun screenErrorDutchLocaleNotInAppLanguageTest() {
        composeTestRule.setContent {
            DeviceConfigurationOverride(
                DeviceConfigurationOverride.Locales(LocaleList("nl"))
            ) {
                val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Light)
                ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                    val appThemeColor = MaterialTheme.colorScheme.background
                    Surface(
                        modifier = Modifier.background(appThemeColor)
                    ) {
                        //expect to fall back on English
                        expectedDisplayedErrorMessage = "There was an error: $errorMessage"
                        HistoryScreenError(errorMessage = errorMessage)
                    }
                }
            }
        }

        composeTestRule
            .onNodeWithText(expectedDisplayedErrorMessage, true)
            .assertIsDisplayed()
    }

    @Test
    fun screenErrorArabicLocaleTest() {
        composeTestRule.setContent {
            DeviceConfigurationOverride(
                DeviceConfigurationOverride.Locales(LocaleList("ar"))
            ) {
                val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Light)
                ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                    val appThemeColor = MaterialTheme.colorScheme.background
                    Surface(
                        modifier = Modifier.background(appThemeColor)
                    ) {

                        expectedDisplayedErrorMessage = "errorMessage :" + "خطأ"
                        HistoryScreenError(errorMessage = errorMessage)
                    }
                }
            }
        }

        composeTestRule
            .onNodeWithText(expectedDisplayedErrorMessage)
            .assertIsDisplayed()
    }

    @Test
    fun screenErrorSpanishLocaleTest() = runTest {
        composeTestRule.setContent {
            val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Light)
            DeviceConfigurationOverride(
                DeviceConfigurationOverride.Locales(LocaleList("es"))
            ) {
                ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                    val appThemeColor = MaterialTheme.colorScheme.background
                    Surface(
                        modifier = Modifier.background(appThemeColor)
                    ) {
                        expectedDisplayedErrorMessage = "Error: $errorMessage"
                        HistoryScreenError(errorMessage = errorMessage)
                    }
                }
            }
        }

        composeTestRule
            .onNodeWithText(expectedDisplayedErrorMessage, true)
            .assertIsDisplayed()
    }

    @Test
    fun screenErrorGermanLocaleTest() {
        composeTestRule.setContent {
            DeviceConfigurationOverride(
                DeviceConfigurationOverride.Locales(LocaleList("de"))
            ) {
                val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Light)
                ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                    val appThemeColor = MaterialTheme.colorScheme.background
                    Surface(
                        modifier = Modifier.background(appThemeColor)
                    ) {
                        expectedDisplayedErrorMessage =
                            stringResource(id = R.string.error_message_prefix)
                        HistoryScreenError(errorMessage = errorMessage)
                    }
                }
            }
        }

        composeTestRule
            .onNodeWithText(expectedDisplayedErrorMessage, true)
            .assertIsDisplayed()
    }

    @Test
    fun screenErrorSwedishLocaleTest() {
        composeTestRule.setContent {
            DeviceConfigurationOverride(
                DeviceConfigurationOverride.Locales(LocaleList("se"))
            ) {
                val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Dark)

                ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                    val appThemeColor = MaterialTheme.colorScheme.background
                    Surface(
                        modifier = Modifier.background(appThemeColor)
                    ) {

                        expectedDisplayedErrorMessage =
                            stringResource(id = R.string.error_message_prefix)
                        HistoryScreenError(errorMessage = errorMessage)
                    }
                }
            }
        }

        composeTestRule
            .onNodeWithText(expectedDisplayedErrorMessage, true)
            .assertIsDisplayed()
    }

    @Test
    fun screenErrorRussianLocaleTest() {
        composeTestRule.setContent {
            DeviceConfigurationOverride(
                DeviceConfigurationOverride.Locales(LocaleList("ru"))
            ) {
                val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Dark)

                ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                    val appThemeColor = MaterialTheme.colorScheme.background
                    Surface(
                        modifier = Modifier.background(appThemeColor)
                    ) {


                        expectedDisplayedErrorMessage = "Ошибка: $errorMessage"
                        HistoryScreenError(errorMessage = errorMessage)
                    }
                }
            }
        }

        composeTestRule
            .onNodeWithText(expectedDisplayedErrorMessage, true)
            .assertIsDisplayed()
    }

    @Test
    fun screenErrorPortugueseLocaleTest() {
        composeTestRule.setContent {
            DeviceConfigurationOverride(
                DeviceConfigurationOverride.Locales(LocaleList("pt"))
            ) {
                val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Dark)

                ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                    val appThemeColor = MaterialTheme.colorScheme.background
                    Surface(
                        modifier = Modifier.background(appThemeColor)
                    ) {


                        expectedDisplayedErrorMessage = "Erro: $errorMessage"
                        HistoryScreenError(errorMessage = errorMessage)
                    }
                }
            }
        }

        composeTestRule
            .onNodeWithText(expectedDisplayedErrorMessage, true)
            .assertIsDisplayed()
    }

    @Test
    fun screenErrorItalianLocaleTest() {
        composeTestRule.setContent {
            DeviceConfigurationOverride(
                DeviceConfigurationOverride.Locales(LocaleList("it"))
            ) {
                val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Dark)

                ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                    val appThemeColor = MaterialTheme.colorScheme.background
                    Surface(
                        modifier = Modifier.background(appThemeColor)
                    ) {


                        expectedDisplayedErrorMessage = "Errore: $errorMessage"
                        HistoryScreenError(errorMessage = errorMessage)
                    }
                }
            }
        }

        composeTestRule
            .onNodeWithText(expectedDisplayedErrorMessage, true)
            .assertIsDisplayed()
    }

    @Test
    fun screenErrorSwissItalianLocaleTest() {
        composeTestRule.setContent {
            DeviceConfigurationOverride(
                DeviceConfigurationOverride.Locales(LocaleList("it-CH"))
            ) {
                val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Dark)

                ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                    val appThemeColor = MaterialTheme.colorScheme.background
                    Surface(
                        modifier = Modifier.background(appThemeColor)
                    ) {


                        expectedDisplayedErrorMessage = "Errore: $errorMessage"
                        HistoryScreenError(errorMessage = errorMessage)
                    }
                }
            }
        }

        composeTestRule
            .onNodeWithText(expectedDisplayedErrorMessage)
            .assertIsDisplayed()
    }


    @Test
    fun screenErrorFrenchLocaleTest() {
        composeTestRule.setContent {
            DeviceConfigurationOverride(
                DeviceConfigurationOverride.Locales(LocaleList("fr"))
            ) {
                val settingsViewModel =
                    SettingsViewModelMock(ThisDayInHistoryThemeEnum.Dark)

                ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                    val appThemeColor = MaterialTheme.colorScheme.background
                    Surface(
                        modifier = Modifier.background(appThemeColor)
                    ) {


                        expectedDisplayedErrorMessage = "Erreur: $errorMessage"
                        HistoryScreenError(errorMessage = errorMessage)
                    }
                }
                }
            }

            composeTestRule
                .onNodeWithText(expectedDisplayedErrorMessage)
                .assertIsDisplayed()
        }

    @Test
    fun screenErrorFrenchMonacoLocaleTest() {
        composeTestRule.setContent {
            DeviceConfigurationOverride(
                DeviceConfigurationOverride.Locales(LocaleList("fr-MC"))
            ) {
                val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Dark)

                ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                    val appThemeColor = MaterialTheme.colorScheme.background
                    Surface(
                        modifier = Modifier.background(appThemeColor)
                    ) {
                        expectedDisplayedErrorMessage = "Erreur: $errorMessage"
                        HistoryScreenError(errorMessage = errorMessage)
                    }
                }
            }

            composeTestRule
                .onNodeWithText(expectedDisplayedErrorMessage)
                .assertIsDisplayed()
        }
    }

}



