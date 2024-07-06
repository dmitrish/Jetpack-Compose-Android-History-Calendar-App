package com.coroutines.thisdayinhistory.di

import com.coroutines.data.converters.IJsonConverterService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class JsonConverterServiceModule {

    @Binds
    abstract fun bindJsonConverterService(
        jsonConverter: JsonConverterServiceImplHilt,
    ): IJsonConverterService
}