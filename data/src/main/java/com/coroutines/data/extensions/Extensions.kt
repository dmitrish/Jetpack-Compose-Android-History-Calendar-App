package com.coroutines.data.extensions

import com.coroutines.models.wiki.WikimediaEvent

val WikimediaEvent.extract: String
    get() = pages
        .firstOrNull { it.originalImage != null }?.extract
        ?: "Not found"

val WikimediaEvent.shortTitle: String
    get() = pages.firstOrNull { it.originalImage != null }?.title ?: pages.first().title

infix fun WikimediaEvent.matches(valueToMatch: String): Boolean {
    val pattern = Regex(valueToMatch + WikimediaEvent.matchPattern)
    val extract = if (pages.isEmpty()) "" else text + pages[0].description
    return pattern.containsMatchIn("$text $extract")
}

infix fun WikimediaEvent.matchesByStartsWith(valueToMatch: String): Boolean {
    val pattern = Regex(valueToMatch + WikimediaEvent.startsWithPattern)
    val extract = if (pages.isEmpty()) "" else text + pages[1].description
    return pattern.containsMatchIn(extract)
}

infix fun WikimediaEvent.matchesExtractByStartsWith(valueToMatch: String): Boolean {
    val pattern = Regex(valueToMatch + WikimediaEvent.startsWithPattern)
    val extract = if (pages.isEmpty()) "" else pages[1].extract
    return pattern.containsMatchIn("$text $extract")
}

fun String.isBC(): Boolean {
    return contains("BC")
}

@Suppress("MagicNumber")
fun Int.toHistoryFormattedString(): String {
    return if (this < 10) "0$this" else this.toString()
}