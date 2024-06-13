package com.coroutines.data.models


data class TranslateRequestParams(
    val language: String,
    val welcomeText: String,
    val languagePrompt: String
)