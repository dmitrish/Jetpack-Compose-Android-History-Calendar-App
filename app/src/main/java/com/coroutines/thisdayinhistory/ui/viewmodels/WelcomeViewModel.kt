package com.coroutines.thisdayinhistory.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coroutines.api.translation.TranslationApi
import com.coroutines.data.models.TranslateRequestParams
import com.coroutines.thisdayinhistory.ui.state.WelcomeScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout


class WelcomeViewModel
constructor(private val translationApiService: TranslationApi):
    ViewModel(), IWelcomeViewModel {
    private var _prompt: String = DEFAULT_LANGUAGE_PROMPT
    private val _screenState = MutableStateFlow<WelcomeScreenUiState>(
        WelcomeScreenUiState.Initial(
            DEFAULT_WELCOME_MESSAGE
        )
    )
    override val uiScreenState: StateFlow<WelcomeScreenUiState>
        get() = _screenState

    override val prompt by lazy {
        _prompt
    }

    override fun setDefaultLanguageWelcomeMessage(welcomeMessage: String){
        //this should really be [@assistedinject] but Hilt does not support them (yet)
        //https://github.com/google/dagger/issues/2287
        _screenState.value = WelcomeScreenUiState.Initial(welcomeMessage)
    }
    @Suppress("TooGenericExceptionCaught")
    override fun translate(translateRequestParams: TranslateRequestParams){
        viewModelScope.launch {
            _screenState.value =
                WelcomeScreenUiState.WaitingForTranslation(translateRequestParams.welcomeText)
            try {
                withTimeout(TIMEOUT_MS) {
                    translationApiService
                        .getTranslation(
                            translateRequestParams.language,
                            "${translateRequestParams.welcomeText}|${translateRequestParams.languagePrompt}"
                        )
                        .map {
                            it.getResult()
                        }
                        .catch {
                            _prompt = translateRequestParams.languagePrompt
                            _screenState.value =
                                WelcomeScreenUiState.Error(it.message ?: DEFAULT_ERROR_MESSAGE)
                        }
                        .collect { resultState ->
                            _prompt = resultState.substringAfter("|")
                            val welcomeMessage = resultState.substringBefore("|")
                            _screenState.value = WelcomeScreenUiState.Success(welcomeMessage)
                        }
                }
            }
            catch (e: Exception){
                _screenState.value = WelcomeScreenUiState.Error(e.message ?: DEFAULT_ERROR_MESSAGE)
            }
        }
    }

    companion object {
        private const val DEFAULT_ERROR_MESSAGE  = "Something went wrong. Please try again later."
        private const val DEFAULT_WELCOME_MESSAGE  = "History Cat"
        private const val DEFAULT_LANGUAGE_PROMPT  =  "Please choose your language"
        private const val TIMEOUT_MS = 3000L
    }
}