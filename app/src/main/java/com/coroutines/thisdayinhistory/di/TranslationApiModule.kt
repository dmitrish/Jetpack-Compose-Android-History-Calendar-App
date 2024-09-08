package com.coroutines.thisdayinhistory.di

import com.coroutines.api.translation.TranslationApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface TranslationApiModule {
    @Binds
    fun provideTranslationApiImpl(classImpl: TranslationApiImplHilt): TranslationApi
}