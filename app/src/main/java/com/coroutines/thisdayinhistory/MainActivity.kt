

package com.coroutines.thisdayinhistory

import android.app.LocaleManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import java.util.Locale
import android.os.LocaleList
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass

import androidx.compose.runtime.getValue
import androidx.core.os.LocaleListCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coroutines.data.models.LangEnum
import com.coroutines.thisdayinhistory.preferences.UserPreferencesRepository
import com.coroutines.thisdayinhistory.ui.state.AppConfigurationState
import com.coroutines.thisdayinhistory.ui.viewmodels.ISettingsViewModel
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob



class MainActivity : AppCompatActivity() {
    private var isStatePendingRestore = true

    override fun onCreate(savedInstanceState: Bundle?) {
        //https://www.youtube.com/watch?v=mlL6H-s0nF0
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                return@setKeepOnScreenCondition isStatePendingRestore
            }
        }

        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        runUi()
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    private fun runUi() = setContent {

        val prefStore = PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { application.applicationContext.preferencesDataStoreFile("USER_PREFF") }
        )
        val userPreferencesRepository = UserPreferencesRepository(prefStore)
        val settingsViewModel : ISettingsViewModel  by viewModels { SettingsViewModelFactory (userPreferencesRepository) }
        val appConfigState by settingsViewModel.appConfigurationState.collectAsStateWithLifecycle()
        val deviceLanguage = getDeviceLanguage()
        settingsViewModel.setDeviceLanguage(deviceLanguage)



        when (appConfigState.isLoading) {
            true ->
                { }//load animation
            false -> {
                if (deviceLanguage != appConfigState.appLanguage.langId) {
                    setPerAppLanguage(appConfigState)
                }

                isStatePendingRestore = false

                val windowSize = calculateWindowSizeClass(this)

                MainContent(settingsViewModel, appConfigState, windowSize)
            }
        }
    }

    private fun getDeviceLanguage(): String  {
        return LocaleListCompat
            .getDefault()
            .get(LANGUAGE_INDEX)?.language
            ?:
            LangEnum.ENGLISH.langId
    }

    private fun setPerAppLanguage(settingsUiState: AppConfigurationState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            with(
                getSystemService(
                    LocaleManager::class.java
                )
            ) {
                val appLang = applicationLocales[LANGUAGE_INDEX]
                if (appLang == null || appLang.toLanguageTag() != settingsUiState.appLanguage.langId) {
                    applicationLocales =
                        LocaleList(Locale.forLanguageTag(settingsUiState.appLanguage.langId))
                }
            }
        } else {
            val appLang = AppCompatDelegate.getApplicationLocales()[LANGUAGE_INDEX]
            if (appLang == null || appLang.toLanguageTag() != settingsUiState.appLanguage.langId) {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(
                        settingsUiState.appLanguage.langId
                    )
                )
            }
        }
    }

    companion object {
        const val TAG = "MainActivity"
        const val SPLASH_DURATION = 500L
        const val SPLASH_Y_TARGET = 0f
        const val LANGUAGE_INDEX = 0
    }


}

