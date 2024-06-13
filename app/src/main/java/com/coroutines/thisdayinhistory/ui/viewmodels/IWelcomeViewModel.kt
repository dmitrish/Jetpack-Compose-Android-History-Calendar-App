package com.coroutines.thisdayinhistory.ui.viewmodels

import com.coroutines.data.models.TranslateRequestParams
import com.coroutines.thisdayinhistory.ui.state.WelcomeScreenUiState
import kotlinx.coroutines.flow.StateFlow

interface IWelcomeViewModel {
    val uiScreenState: StateFlow<WelcomeScreenUiState>
    val prompt: String
    fun setDefaultLanguageWelcomeMessage(welcomeMessage: String)
    fun translate(translateRequestParams: TranslateRequestParams)
}