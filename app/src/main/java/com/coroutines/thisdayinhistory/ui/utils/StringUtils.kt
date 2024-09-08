package com.coroutines.thisdayinhistory.ui.utils

import com.coroutines.thisdayinhistory.components.MAX_COUNT_CHARS_BEFORE_PAD_UP

fun String.stripHtml (): String {
    val regexPattern = ("\\<.*?\\>");
    // Use regex_replace function in regex
    // to erase every tags enclosed in <>
    val pattern = Regex(regexPattern)
    return pattern.replace(this, "")
        .replace("&#039;", "'")
        .replace("&#39;", "'")
        .replace("&#160;", " ")
        .replace("&amp;", "&")
        .replace("&quot;", "\"")
        .replace("\n", "")
        .replace("\n\n", "")
        .replace("\n\\s*\n", "")
}

fun String.breakUpWithNewLines() :String{
    // val regexRule = "(?<!\\b\\p{L})\\.(?<!\\d.(?=\\d))(?!\$)"
    val reg = Regex("(?<!\\b..|\\d.)(?<=\\.)\\s+")
    // val regexR = Regex( "(?<!\\b\\p{L})\\.(?<!\\d.(?=\\d))(?!\\s*\$)\\s*")

    // val regexRule = "(?<!\\b\\p{L})\\.(?<!\\d.(?=\\d))(?!\$)"
    //val reg = "(?<!\b.|\\d)\\."
    //val regexRule = "\\.\\s+"
    val splitText = this.split(reg)
    val result = splitText.joinToString( separator = "\n\n")

    // val del = "\\.\\s+[^(?!(?:p.m.|a.m.))]"
    //val delim = "\\.(\\s*s)[^\b[a-zA-Z0-9](split?!.)]"
    return result
}
fun String.padUp(maxCountBeforePadUp: Int = MAX_COUNT_CHARS_BEFORE_PAD_UP) : String {
    val count =  this.chars().count()
    return if ( count < maxCountBeforePadUp){
        val diff = maxCountBeforePadUp - count
        this + "  ".repeat(diff.toInt())
    }
    else{
        this
    }
}
