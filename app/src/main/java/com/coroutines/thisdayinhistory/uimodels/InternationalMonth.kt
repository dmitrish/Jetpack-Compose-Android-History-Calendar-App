package com.coroutines.thisdayinhistory.uimodels

import androidx.compose.runtime.MutableState
import com.coroutines.data.models.LangEnum

class InternationalMonth(
    override var language: MutableState<String>,
    var monthOfCalendar: MutableState<Int>,
) :
    IInternationalMonth {
    override val monthSelected: String
        get() = when (language.value) {
            LangEnum.RUSSIAN.langId -> monthsRussian[monthOfCalendar.value] ?: INVALID_MONTH
            LangEnum.FRENCH.langId -> monthsFrench[monthOfCalendar.value] ?: INVALID_MONTH
            LangEnum.SPANISH.langId -> monthsSpanish[monthOfCalendar.value] ?: INVALID_MONTH
            LangEnum.PORTUGUESE.langId -> monthsPortuguese[monthOfCalendar.value] ?: INVALID_MONTH
            LangEnum.GERMAN.langId -> monthsGerman[monthOfCalendar.value] ?: INVALID_MONTH
            LangEnum.SWEDISH.langId -> monthsSwedish[monthOfCalendar.value] ?: INVALID_MONTH
            LangEnum.ARABIC.langId -> monthsArabic[monthOfCalendar.value] ?: INVALID_MONTH
            "bs" -> monthsBosnian[monthOfCalendar.value] ?: INVALID_MONTH
            LangEnum.ITALIAN.langId -> monthsItalian[monthOfCalendar.value] ?: INVALID_MONTH
            else -> monthsEnglish[monthOfCalendar.value] ?: INVALID_MONTH
        }

    companion object {
        const val TAG = "InternationalMonth"
        const val INVALID_MONTH = "Invalid month"
    }
}