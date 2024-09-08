package com.coroutines.thisdayinhistory.uimodels


import androidx.compose.runtime.MutableState
import java.time.LocalDateTime

interface IHistoryCalendar {
    var monthOfCalendar: MutableState<Int>
    var dayOfCalendar: MutableState<Int>
    val year: Int
    var currentLocalDateTime: LocalDateTime
}

