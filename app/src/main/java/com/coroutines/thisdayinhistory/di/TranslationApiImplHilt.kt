package com.coroutines.thisdayinhistory.di

import com.coroutines.api.translation.TranslationApiImpl
import com.coroutines.api.translation.TranslationApiService
import javax.inject.Inject

@Suppress("EmptyClassBlock")
class TranslationApiImplHilt @Inject constructor
    (private val apiService: TranslationApiService) :
    TranslationApiImpl(apiService)