package com.coroutines.thisdayinhistory.glance

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThisDayInHistoryGlanceAppWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = ThisDayInHistoryGlanceAppWidget()

}

