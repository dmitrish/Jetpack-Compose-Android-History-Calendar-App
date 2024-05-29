package com.coroutines.api.translation


import com.coroutines.data.models.TranslateResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

open class TranslationApiImpl(private val apiService: TranslationApiService) : TranslationApi {
    override fun getTranslation(language: String, text: String): Flow<TranslateResult> {
        return flow { emit(apiService.getTranslation(language = language, text = text)) }
    }
}