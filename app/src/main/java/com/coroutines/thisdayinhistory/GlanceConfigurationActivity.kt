package com.coroutines.thisdayinhistory

import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import coil.size.Size
import com.coroutines.thisdayinhistory.components.rememberSystemUiController
import com.coroutines.thisdayinhistory.ui.components.CatLogo
import com.coroutines.thisdayinhistory.ui.screens.main.HistoryListItem
import com.coroutines.thisdayinhistory.ui.screens.main.HistoryTextHeaderStyle
import com.coroutines.thisdayinhistory.ui.screens.main.HistoryTextStyle
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryTheme
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum
import com.coroutines.thisdayinhistory.ui.utils.darker
import com.coroutines.thisdayinhistory.ui.utils.lighter
import com.coroutines.thisdayinhistory.ui.viewmodels.HistoryViewModel
import com.coroutines.thisdayinhistory.ui.viewmodels.HistoryViewModelMock
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModel
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModelMock
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GlanceConfigurationActivity : ComponentActivity() {

    private val appWidgetId: Int by lazy {
        intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID,
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @SuppressLint("StateFlowValueCalledInComposition", "UnusedMaterialScaffoldPaddingParameter",
        "UnusedMaterial3ScaffoldPaddingParameter"
    )
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setResult(
            RESULT_CANCELED,
            Intent().apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            },
        )


        setContent {
            val settingsViewModel = hiltViewModel<SettingsViewModel>()
            val appConfigState = settingsViewModel.appConfigurationState.collectAsState()

            val historyViewModel =
                hiltViewModel<HistoryViewModel, HistoryViewModel.IHistoryViewModelFactory>(
                    key = appConfigState.value.appLanguage.langId
                ) { factory ->
                    factory.create(appConfigState.value.appLanguage)
                }
            val data = historyViewModel.historyData

            ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                val appThemeColor = MaterialTheme.colorScheme.background
                val appThemeOnBackground = MaterialTheme.colorScheme.onBackground
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(
                    color = appThemeColor,
                    isNavigationBarContrastEnforced = false
                )
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(appThemeColor)
                ) {
                        Column(
                            Modifier
                                .fillMaxSize()
                                .background(appThemeColor)
                                .padding(20.dp)
                        ) {
                           CatLogo(settings = appConfigState.value)
                            LazyColumn (
                                Modifier
                                    .fillMaxSize()
                                    .background(appThemeColor)
                            ) {
                                items(data){ item ->
                                    Row{
                                        HistoryListItem(
                                            historyEvent = item,
                                            styles = buildList {
                                                add(HistoryTextStyle(maxLines = 3, style = MaterialTheme.typography.bodySmall))
                                                add(HistoryTextHeaderStyle(maxLines = 1, lineHeight = 20.sp,  style = MaterialTheme.typography.bodyMedium))
                                            },
                                            onClick = {} ,
                                            onImageClick = {}
                                        ) {

                                        }

                                    }
                                }
                            }
                       }
                    }
            }

                 /*   setResult(
                        RESULT_OK,
                        Intent().apply {
                            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                        }
                    )
                    finish()*/


        }



    }

    private fun onItemClick() {
        val context = this
        lifecycleScope.launch {

            setResult(RESULT_OK, intent)
            finish()
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
@Preview
fun ConfigurationContent(){
    val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Dark)
    val appConfigState = settingsViewModel.appConfigurationState.value

    val historyViewModel = HistoryViewModelMock()
    val data = historyViewModel.historyData

    ThisDayInHistoryTheme(viewModel = settingsViewModel) {
        val appThemeColor = MaterialTheme.colorScheme.background
        val appThemeOnBackground = MaterialTheme.colorScheme.onBackground
        val systemUiController = rememberSystemUiController()
        systemUiController.setSystemBarsColor(
            color = appThemeColor,
            isNavigationBarContrastEnforced = false
        )
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(appThemeColor)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(appThemeColor)
                    .padding(20.dp)
            ) {
                CatLogo(settings = appConfigState)
                ElevatedCard (
                    Modifier
                        .padding(start = 10.dp, end = 10.dp, top =  1.dp),
                        //.height(200.dp),
                    colors = CardColors(
                        containerColor = MaterialTheme.colorScheme.background.lighter(0.5f),
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        disabledContainerColor = MaterialTheme.colorScheme.background,
                        disabledContentColor = MaterialTheme.colorScheme.onBackground
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 1.dp
                    ))
                {
                    LazyColumn(
                        Modifier
                            //.fillMaxSize()
                            .background(appThemeColor.lighter(0.1f))
                            .height(300.dp)
                    ) {
                        items(data) { item ->
                            Row {
                                HistoryListItem(
                                    historyEvent = item,
                                    styles = buildList {
                                        add(
                                            HistoryTextStyle(
                                                maxLines = 3,
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        )
                                        add(
                                            HistoryTextHeaderStyle(
                                                maxLines = 1,
                                                lineHeight = 20.sp,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        )
                                    },
                                    onClick = {},
                                    onImageClick = {}
                                ) {

                                }

                            }
                        }
                    }
                }


            }
        }
    }

}