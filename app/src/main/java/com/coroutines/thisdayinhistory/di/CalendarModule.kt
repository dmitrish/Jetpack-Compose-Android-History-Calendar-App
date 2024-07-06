package com.coroutines.thisdayinhistory.di


import com.coroutines.thisdayinhistory.uimodels.HistoryCalendar
import com.coroutines.thisdayinhistory.uimodels.IHistoryCalendar
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CalendarModule {

    @Binds
    abstract fun bindCalendar(
        historyCalendar: HistoryCalendar,
    ): IHistoryCalendar
}