package com.coroutines.thisdayinhistory.ui.state

import com.coroutines.data.models.EventCategoryEnum
import com.coroutines.data.models.LangEnum
import com.coroutines.models.synonyms.HistoryDay
import com.coroutines.models.synonyms.HistoryMonth


data class DataRequestParams(
    val month: HistoryMonth,
    val day: HistoryDay,
    val langEnum: LangEnum,
    val category: EventCategoryEnum,
)
