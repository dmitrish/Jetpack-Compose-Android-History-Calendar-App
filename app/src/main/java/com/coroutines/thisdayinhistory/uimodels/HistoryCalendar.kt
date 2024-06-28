package com.coroutines.thisdayinhistory.uimodels


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.datetime.toKotlinTimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.LocalDateTime
import java.time.ZoneId


class HistoryCalendar  constructor() : IHistoryCalendar {

    private val clock: Clock = Clock.System
    private var currentMoment : Instant
    init {
        currentMoment = clock.now()
    }

    override var currentLocalDateTime: LocalDateTime
        get() = currentMoment.toLocalDateTime(ZoneId.systemDefault().toKotlinTimeZone()).toJavaLocalDateTime()
        set(value) {
            currentMoment = value.toKotlinLocalDateTime().toInstant(ZoneId.systemDefault().toKotlinTimeZone())
        }
    override var monthOfCalendar: MutableState<Int> =
        mutableIntStateOf(
            currentMoment.toLocalDateTime(ZoneId.systemDefault().toKotlinTimeZone()).monthNumber
        )
    override var dayOfCalendar: MutableState<Int> =
        mutableIntStateOf(
            currentMoment.toLocalDateTime(ZoneId.systemDefault().toKotlinTimeZone()).dayOfMonth
        )
    override val year: Int = currentMoment.toLocalDateTime(ZoneId.systemDefault().toKotlinTimeZone()).year

}