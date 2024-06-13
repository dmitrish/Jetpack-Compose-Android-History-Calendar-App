package com.coroutines.thisdayinhistory.ui.screens.welcome

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.coroutines.data.models.TranslateRequestParams
import com.coroutines.thisdayinhistory.R
import com.coroutines.thisdayinhistory.components.ScreenPlaceholder
import com.coroutines.thisdayinhistory.graph.IntroNavOption
import com.coroutines.thisdayinhistory.ui.components.CatLogo
import com.coroutines.thisdayinhistory.ui.constants.WELCOME_MESSAGE_TEXT_TAG
import com.coroutines.thisdayinhistory.ui.state.AppConfigurationState
import com.coroutines.thisdayinhistory.ui.state.WelcomeScreenUiState
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryTheme
import com.coroutines.thisdayinhistory.ui.viewmodels.IWelcomeViewModel
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModelMock
import com.coroutines.thisdayinhistory.ui.viewmodels.WelcomeViewModelMock
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun WelcomeScreen (
    navController: NavController = rememberNavController(),
    settings: AppConfigurationState,
    viewModel: IWelcomeViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CatLogo(settings)
        Spacer(modifier = Modifier.height(30.dp))
        Spacer(modifier = Modifier.weight(1f))
        val welcomeMessage: String = stringResource(R.string.welcome_message)
        val languagePrompt: String = stringResource(R.string.language_prompt)
        viewModel.setDefaultLanguageWelcomeMessage(welcomeMessage)
        val uiScreenState = viewModel.uiScreenState.collectAsStateWithLifecycle()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                when (uiScreenState.value) {
                    is WelcomeScreenUiState.Initial -> {
                        WelcomeMessage((uiScreenState.value as WelcomeScreenUiState.Initial).defaultText)
                        val localeLang = settings.deviceLanguage
                        val translateRequestParams = TranslateRequestParams(localeLang, welcomeMessage, languagePrompt)
                        viewModel.translate(translateRequestParams)
                    }
                    is WelcomeScreenUiState.WaitingForTranslation -> {
                        WelcomeMessage( (uiScreenState.value as WelcomeScreenUiState.WaitingForTranslation).defaultText)
                    }
                    is WelcomeScreenUiState.Error ->{
                        val prompt = viewModel.prompt
                        navController.navigate(IntroNavOption.LanguagesScreen.name + "/$prompt")
                    }
                    //time out or any other error - we just proceed to the next screen
                    // navigate to choose language screen
                    //Greeting ((uiScreenState.value as WelcomeScreenUiState.Error).errorDescription)
                    is WelcomeScreenUiState.Success -> {
                        WelcomeMessage(text = (uiScreenState.value as WelcomeScreenUiState.Success).translation)
                        LaunchedEffect(Unit) {
                            delay(3.seconds)
                            val prompt = viewModel.prompt.ifBlank { "Please choose your language" }
                            navController.navigate(IntroNavOption.LanguagesScreen.name + "/$prompt")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomeMessage(text: String, modifier: Modifier = Modifier) {
    Text(
        text = "$text!",
        modifier = modifier
            .testTag(WELCOME_MESSAGE_TEXT_TAG)
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 40.dp)
    )
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
@Preview()
fun WelcomeScreenPreview() {
    val navController = rememberNavController()
    val settingsViewModel = SettingsViewModelMock()

    ThisDayInHistoryTheme(
        settingsViewModel
    ) {
        val appThemeColor = MaterialTheme.colorScheme.background
        Surface(
            modifier = Modifier.background(appThemeColor)
        ) {
            WelcomeScreen(
                navController = navController,
                settings = settingsViewModel.appConfigurationState.value,
                viewModel = WelcomeViewModelMock()
            )
        }
    }
}