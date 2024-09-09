package com.coroutines.thisdayinhistory.glance

import android.content.Context
import com.coroutines.thisdayinhistory.preferences.UserPreferencesRepository
import com.coroutines.usecase.IHistoryDataStandardUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlanceServiceProvider @Inject internal constructor(
    var dataRepository: IHistoryDataStandardUseCase,
    var userPreferencesRepository: UserPreferencesRepository
) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface WidgetEntryPoint {
        fun glanceServiceProvider(): GlanceServiceProvider
    }

    companion object {
        fun get(applicationContext: Context): GlanceServiceProvider {
            var widgetEntryPoint: WidgetEntryPoint = EntryPoints.get(
                applicationContext,
                WidgetEntryPoint::class.java,
            )
            return widgetEntryPoint.glanceServiceProvider()
        }
    }
}
