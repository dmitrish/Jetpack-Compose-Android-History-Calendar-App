package com.coroutines.thisdayinhistory.ui.screens.language

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.coroutines.data.models.LangEnum
import com.coroutines.thisdayinhistory.components.ScreenPlaceholder
import com.coroutines.thisdayinhistory.graph.NavRoutes
import com.coroutines.thisdayinhistory.ui.components.CatLogo
import com.coroutines.thisdayinhistory.ui.previewProviders.ThisDayInHistoryThemeEnumProvider
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum
import com.coroutines.thisdayinhistory.ui.viewmodels.ISettingsViewModel
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModelMock
import kotlinx.coroutines.launch
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.coroutines.thisdayinhistory.R
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryTheme


@Composable
fun LanguageScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ISettingsViewModel,
    languagePrompt: String = ""
){
    val settings by viewModel.appConfigurationState.collectAsStateWithLifecycle()
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
        Text(languagePrompt, color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.height(30.dp))
        val scope = rememberCoroutineScope()

        for (language in LangEnum.entries){
            Row {
                LanguageSelection(
                    language = language,
                    modifier =  Modifier
                ){
                    scope.launch {
                        viewModel.setOnboarded()
                        viewModel.setAppLanguage(language)
                        navController.navigate(NavRoutes.MainRoute.name) {
                            popUpTo(NavRoutes.MainRoute.name)
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
@Preview(locale = "es")
fun LanguageScreenPreview(
    @PreviewParameter(ThisDayInHistoryThemeEnumProvider::class)
    historyCatThemeEnum: ThisDayInHistoryThemeEnum
) {
    val navController = rememberNavController()
    val settingsViewModel = SettingsViewModelMock(historyCatThemeEnum)

    ThisDayInHistoryTheme(
        settingsViewModel
    ) {
        val appThemeColor = MaterialTheme.colorScheme.background
        Surface(
            modifier = Modifier.background(appThemeColor)
        ) {
            LanguageScreen(
                navController = navController,
                viewModel = settingsViewModel,
                languagePrompt = stringResource(id = R.string.language_prompt)
            )
        }
    }
}