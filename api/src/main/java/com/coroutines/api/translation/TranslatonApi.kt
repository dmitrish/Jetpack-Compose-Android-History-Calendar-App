package com.coroutines.api.translation


import com.coroutines.data.models.TranslateResult
import kotlinx.coroutines.flow.Flow

interface TranslationApi {
    fun getTranslation(language: String, text: String): Flow<TranslateResult>
}