package com.coroutines.thisdayinhistory.glance

import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DayInHistoryGlanceAppWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = DayInHistoryGlanceAppWidget()

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        context?.let { nonNullContext ->
            nonNullContext.starGlanceWorker()
        }
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        context?.let { nonNullContext ->
            nonNullContext.starGlanceWorker()
        }
    }

}

