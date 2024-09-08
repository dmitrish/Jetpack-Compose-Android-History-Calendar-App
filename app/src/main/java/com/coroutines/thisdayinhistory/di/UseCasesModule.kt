package com.coroutines.thisdayinhistory.di

import com.coroutines.usecase.HistoryDataStandardPlusFlagsUseCase
import com.coroutines.usecase.HistoryDataStandardUseCase
import com.coroutines.usecase.IHistoryDataStandardUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCasesModule {
    @Binds
    abstract fun bindHistoryDataUseCase(
        historyDataStandardUseCase: HistoryDataStandardUseCase,
    ): IHistoryDataStandardUseCase
}

