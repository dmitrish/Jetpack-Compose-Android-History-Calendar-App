package com.coroutines.thisdayinhistory.di

import com.coroutines.api.wiki.WikiMediaApi
import com.coroutines.api.wiki.WikiMediaApiServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface WikiMediaApiModule {
    @Binds
    fun provideWikiMediaApiImpl(classImpl: WikiMediaApiServiceImpl): WikiMediaApi
}