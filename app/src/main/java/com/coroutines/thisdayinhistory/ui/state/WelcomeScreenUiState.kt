package com.coroutines.thisdayinhistory.ui.state

sealed class WelcomeScreenUiState {
    data class Success(val translation: String) : WelcomeScreenUiState()
    data class Error(val errorDescription: String) : WelcomeScreenUiState()
    data class WaitingForTranslation (val defaultText: String) : WelcomeScreenUiState()
    data class Initial (val defaultText: String): WelcomeScreenUiState()
}