package com.coroutines.thisdayinhistory.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coroutines.api.translation.TranslationApi
import com.coroutines.data.models.LangEnum
import com.coroutines.data.models.Languages
import com.coroutines.data.models.OnboardingStatusEnum
import com.coroutines.thisdayinhistory.preferences.UserPreferencesRepository
import com.coroutines.thisdayinhistory.ui.state.AppConfigurationState
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum
import com.coroutines.thisdayinhistory.ui.utils.zip
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private data class SettingsViewModelState(
    val isLoading: Boolean = true,
    val isOnboarded: Boolean = false,
    val useDynamicColors: Boolean = true,
    val appTheme: ThisDayInHistoryThemeEnum = ThisDayInHistoryThemeEnum.Light,
    val appLanguage: LangEnum = LangEnum.ENGLISH,
    val deviceLanguage: String = ""
) {
    fun asActivityState() = AppConfigurationState(
        isLoading = isLoading,
        isOnboarded = isOnboarded,
        useDynamicColors = useDynamicColors,
        appTheme = appTheme,
        appLanguage = appLanguage,
        deviceLanguage = deviceLanguage
    )
}

class SettingsViewModelFactory(private val userPreferencesRepository: UserPreferencesRepository):
    ViewModelProvider.Factory {@Suppress("UNCHECKED_CAST")
override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return SettingsViewModel(userPreferencesRepository) as T
    }
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) :
    ISettingsViewModel, ViewModel() {

    private var _aboutDescription = MutableStateFlow(Languages.ENGLISH.appDescription)
    private val viewModelState = MutableStateFlow(value = SettingsViewModelState())

    override val appConfigurationState = viewModelState.map { it.asActivityState() }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = viewModelState.value.asActivityState()
    )

    init {
        viewModelScope.launch() {
            viewModelState.update { it.copy(isLoading = true) }
            val languageFlow = userPreferencesRepository.getLanguagePreference()
            val themeFlow =  userPreferencesRepository.getThemePreference()
            val onboardedFlow = userPreferencesRepository.getOnboardedStatus()

            zip(languageFlow, themeFlow, onboardedFlow) { language, theme, onboarding ->
                return@zip Triple<LangEnum, ThisDayInHistoryThemeEnum, OnboardingStatusEnum>(language, theme, onboarding)
            }.collect { combinedData ->
                viewModelState.update { state ->
                    state.copy(
                        isLoading = false,
                        isOnboarded = combinedData.third == OnboardingStatusEnum.Onboarded,
                        useDynamicColors = true,
                        appLanguage = combinedData.first,
                        appTheme = combinedData.second
                    )
                }
            }
        }
    }

    override val aboutDescription: StateFlow<String>
        get() = _aboutDescription

    override fun setAppTheme(theme: ThisDayInHistoryThemeEnum) {
        Log.d(TAG, theme.name)
        viewModelScope.launch {
            userPreferencesRepository.setThemePreference(theme)
            viewModelState.update { state ->
                state.copy(
                    appTheme = theme
                )
            }
        }
    }

    override fun setAppLanguage(langEnum: LangEnum) {
        viewModelScope.launch {
            _aboutDescription.value = Languages.from(langEnum.langId)?.appDescription ?:  Languages.ENGLISH.appDescription
            userPreferencesRepository.setLanguagePreference(langEnum)
            viewModelState.update { state ->
                state.copy(
                    appLanguage = langEnum,
                )
            }
        }
    }

    override fun setDeviceLanguage(language: String) {
        viewModelState.update { state ->
            state.copy(
                deviceLanguage = language
            )
        }
    }

    override fun setOnboarded() {
        Log.d(TAG, "onboarded")
        viewModelScope.launch {
            userPreferencesRepository.setOnboarded()
            viewModelState.update { state ->
                state.copy(
                    isOnboarded = true
                )
            }
        }
    }



    companion object {
        const val TAG = "ThemeViewModel"
    }
}
