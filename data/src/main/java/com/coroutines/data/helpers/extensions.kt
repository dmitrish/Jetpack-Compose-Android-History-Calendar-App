package com.coroutines.data.helpers

fun String.isBC(): Boolean {
    return contains("BC")
}

@Suppress("MagicNumber")
fun Int.toHistoryFormattedString(): String {
    return if (this < 10) "0$this" else this.toString()
}