package com.coroutines.thisdayinhistory.screens.welcome


import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import assertCurrentRouteName
import com.coroutines.thisdayinhistory.graph.IntroNavOption
import com.coroutines.thisdayinhistory.graph.MainNavOption
import com.coroutines.thisdayinhistory.graph.NavRoutes
import com.coroutines.thisdayinhistory.graph.introGraphTest
import com.coroutines.thisdayinhistory.ui.constants.LANGUAGE_SELECTION_TEXT_TAG
import com.coroutines.thisdayinhistory.ui.constants.WELCOME_MESSAGE_TEXT_TAG
import com.coroutines.thisdayinhistory.ui.screens.welcome.WelcomeScreen
import com.coroutines.thisdayinhistory.ui.state.WelcomeScreenUiState
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryTheme
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModelMock
import com.coroutines.thisdayinhistory.ui.viewmodels.WelcomeViewModelMock
import org.junit.Rule
import org.junit.Test

class WelcomeScreenScenarioTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: NavHostController// TestNavHostController
    private val welcomeScreenTag = WELCOME_MESSAGE_TEXT_TAG
    private val expectedDefaultText = "Welcome to our app!"
    private val settingsViewModel = SettingsViewModelMock()


    @Test
    fun screenInitialStateTest() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            val appSettingsState = settingsViewModel.appConfigurationState.value
            val viewModel = WelcomeViewModelMock(WelcomeScreenUiState.Initial(expectedDefaultText))
            ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                WelcomeScreen(navController, appSettingsState, viewModel)
            }
        }

        composeTestRule.onNodeWithTag(welcomeScreenTag)
            .assertIsDisplayed()
            .assertTextContains(expectedDefaultText, true)
    }


    @Test
    fun welcomeTransitionsToLanguagesOnErrorTest() {
        composeTestRule.setContent {
            navController = rememberNavController()
            val welcomeViewModel = WelcomeViewModelMock(WelcomeScreenUiState.Error("error"))
            ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                NavHost(
                    navController = navController,
                    startDestination = NavRoutes.IntroRoute.name
                ) {
                    introGraphTest(navController, settingsViewModel, welcomeViewModel)
                }
            }
        }


        composeTestRule.mainClock.advanceTimeBy(3999)

        composeTestRule.waitUntil(timeoutMillis = 5900) {
            composeTestRule
                .onAllNodesWithTag(LANGUAGE_SELECTION_TEXT_TAG)
                .fetchSemanticsNodes().size == 9
        }
        navController.assertCurrentRouteName(IntroNavOption.LanguagesScreen.name + "/{prompt}")


    }


    @Test
    fun welcomeTransitionsToLanguagesOnSuccessTest() {
        composeTestRule.setContent {
            navController = rememberNavController()
            val welcomeViewModel = WelcomeViewModelMock(WelcomeScreenUiState.Success("translated"))
            ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                NavHost(
                    navController = navController,
                    startDestination = NavRoutes.IntroRoute.name
                ) {
                    introGraphTest(navController, settingsViewModel, welcomeViewModel)
                }
            }
        }

        composeTestRule.mainClock.advanceTimeBy(3999)

        composeTestRule.waitUntil(timeoutMillis = 5900) {
            composeTestRule
                .onAllNodesWithTag(LANGUAGE_SELECTION_TEXT_TAG)
                .fetchSemanticsNodes().size == 9

        }
        navController.assertCurrentRouteName(IntroNavOption.LanguagesScreen.name + "/{prompt}")
    }


    @Test
    fun welcomeOnInitialStateTest() {
        composeTestRule.setContent {
            navController = rememberNavController()
            val welcomeViewModel = WelcomeViewModelMock(WelcomeScreenUiState.Initial("welcome"))
            ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                NavHost(
                    navController = navController,
                    startDestination = NavRoutes.IntroRoute.name
                ) {
                    introGraphTest(navController, settingsViewModel, welcomeViewModel)
                }
            }
        }

        composeTestRule.mainClock.advanceTimeByFrame()

        navController.assertCurrentRouteName(IntroNavOption.WelcomeScreen.name)
    }

    @Test
     fun welcomeOnSuccessStateStaysOnWelcomeForAtLeast2SecondsTest() {
        composeTestRule.setContent {
            navController = rememberNavController()
            val welcomeViewModel = WelcomeViewModelMock(WelcomeScreenUiState.Success("translated"))
            ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                NavHost(
                    navController = navController,
                    startDestination = NavRoutes.IntroRoute.name
                ) {
                    introGraphTest(navController, settingsViewModel, welcomeViewModel)
                }
            }
        }

        composeTestRule.mainClock.advanceTimeBy(1800)
        composeTestRule.runOnIdle {
            navController.assertCurrentRouteName(IntroNavOption.WelcomeScreen.name)
        }
    }

    @Test
    fun welcomeOnSuccessStateTransitions2LanguagesAfter3SecondsTest() {


        composeTestRule.setContent {
            navController = rememberNavController()
            val welcomeViewModel = WelcomeViewModelMock(WelcomeScreenUiState.Success("translated"))
            ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                NavHost(
                    navController = navController,
                    startDestination = NavRoutes.IntroRoute.name
                ) {
                    introGraphTest(navController, settingsViewModel, welcomeViewModel)
                }
            }
        }

        composeTestRule.waitUntil(timeoutMillis = 5100) {
            composeTestRule
                .onAllNodesWithTag(LANGUAGE_SELECTION_TEXT_TAG)
                .fetchSemanticsNodes().size == 9

        }

        navController.assertCurrentRouteName(IntroNavOption.LanguagesScreen.name + "/{prompt}")
    }
}

