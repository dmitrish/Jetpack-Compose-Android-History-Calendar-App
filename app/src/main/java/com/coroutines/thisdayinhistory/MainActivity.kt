

package com.coroutines.thisdayinhistory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coroutines.thisdayinhistory.ui.viewmodels.ISettingsViewModel
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModelMock

class MainActivity : ComponentActivity() {
    val settingsViewModel: ISettingsViewModel = SettingsViewModelMock()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runUi()
    }

    private fun runUi() = setContent {


       val appConfigState by settingsViewModel.appConfigurationState.collectAsStateWithLifecycle()

       when (!appConfigState.isOnboarded) {

         true ->  MainContent(settingsViewModel, appConfigState)
         else ->  Text ("onboard first")
       }


    }

}