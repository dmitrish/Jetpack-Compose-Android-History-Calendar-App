package com.coroutines.thisdayinhistory.di

import com.coroutines.thisdayinhistory.uimodels.HistoryDataMap
import com.coroutines.thisdayinhistory.uimodels.IHistoryDataMap
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HistoryDataMapModule {
    @Binds
    abstract fun bindHistoryDataMap(
        historyDataMap: HistoryDataMap,
    ): IHistoryDataMap

   /* @Binds
    abstract fun bindWidgetViewModel(
        widgetViewModel: WidgetViewModel,
    ): WidgetViewModel*/

}

