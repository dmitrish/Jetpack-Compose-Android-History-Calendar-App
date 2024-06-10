

package com.coroutines.thisdayinhistory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coroutines.thisdayinhistory.ui.viewmodels.ISettingsViewModel
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModelMock

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runUi()
    }

    private fun runUi() = setContent {

       val settingsViewModel: ISettingsViewModel = SettingsViewModelMock()
       val appConfigState by settingsViewModel.appConfigurationState.collectAsStateWithLifecycle()

       MainContent(settingsViewModel, appConfigState)


    }

}