package com.coroutines.thisdayinhistory.ui.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutines.data.models.TranslateRequestParams
import com.coroutines.thisdayinhistory.ui.state.WelcomeScreenUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WelcomeViewModelMock(
    welcomeScreenUiState: WelcomeScreenUiState =
        WelcomeScreenUiState.Initial(DEFAULT_WELCOME_MESSAGE)
): IWelcomeViewModel, ViewModel()
{
    private var _prompt: String = DEFAULT_LANGUAGE_PROMPT
    private val _screenState = MutableStateFlow(
        value = welcomeScreenUiState
    )

    override val uiScreenState: StateFlow<WelcomeScreenUiState>
        get() = _screenState

    override val prompt by lazy {
        _prompt
    }

    override fun setDefaultLanguageWelcomeMessage(welcomeMessage: String){
        _screenState.value = WelcomeScreenUiState.Initial(welcomeMessage)
    }

    override fun translate(translateRequestParams: TranslateRequestParams){
        viewModelScope.launch {
            _screenState.value =  WelcomeScreenUiState.WaitingForTranslation(translateRequestParams.welcomeText)
            delay(2000)
             _prompt = "Choose your language:"
            _screenState.value = WelcomeScreenUiState.Success("this text is translated")

        }
    }
    companion object {
        private const val DEFAULT_WELCOME_MESSAGE  = "History Cat"
        private const val DEFAULT_LANGUAGE_PROMPT  =  "Please choose your language"
    }
}