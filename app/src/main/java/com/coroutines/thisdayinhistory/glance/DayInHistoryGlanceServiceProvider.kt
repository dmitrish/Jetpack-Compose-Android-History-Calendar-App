package com.coroutines.thisdayinhistory.glance

import android.content.Context
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DayInHistoryGlanceServiceProvider @Inject internal constructor(

    var widgetStateHolder: WidgetStateHolder
) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface WidgetEntryPoint {
        fun glanceServiceProvider(): DayInHistoryGlanceServiceProvider
    }

    companion object {
        fun get(applicationContext: Context): DayInHistoryGlanceServiceProvider {
            var widgetEntryPoint: WidgetEntryPoint = EntryPoints.get(
                applicationContext,
                WidgetEntryPoint::class.java,
            )
            return widgetEntryPoint.glanceServiceProvider()
        }
    }
}
