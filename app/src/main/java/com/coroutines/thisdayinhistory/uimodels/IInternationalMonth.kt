package com.coroutines.thisdayinhistory.uimodels

import androidx.compose.runtime.MutableState

interface IInternationalMonth {
    val monthSelected: String
    var language: MutableState<String>
}