package com.coroutines.thisdayinhistory

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.coroutines.thisdayinhistory.components.rememberSystemUiController
import com.coroutines.thisdayinhistory.ui.components.CatLogo
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryTheme
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModel
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
            ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                val appThemeColor = MaterialTheme.colorScheme.background

                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(
                    color = appThemeColor,
                    isNavigationBarContrastEnforced = false
                )
                Surface(
                    modifier = Modifier.fillMaxSize()
                        .background(appThemeColor)
                ) {
                        Column(Modifier.background(appThemeColor)) {
                        CatLogo(settings = settingsViewModel.appConfigurationState.value)
                        Text(
                            settingsViewModel.appConfigurationState.value.appLanguage.name,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Button(onClick = { onItemClick() }) {
                            Text(text = "Here we go")

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
           // Text("this is text", color = Color.Black)
           /* val settingsViewModel = hiltViewModel<SettingsViewModel>()
            ThisDayInHistoryTheme(viewModel = settingsViewModel) {
                Column(Modifier.background(MaterialTheme.colorScheme.background )) {
                    CatLogo(settings = settingsViewModel.appConfigurationState.value)
                    Text(settingsViewModel.appConfigurationState.value.appLanguage.name, color = MaterialTheme.colorScheme.onBackground)
                    Button(onClick = { onItemClick() }) {
                        Text(text = "Here we go")

                    }
                }
            }*/
       // }


    }

    private fun onItemClick() {
        val context = this
        lifecycleScope.launch {

            setResult(RESULT_OK, intent)
            finish()
        }
    }
}