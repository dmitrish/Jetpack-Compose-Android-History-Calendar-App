package com.coroutines.models.wiki

import com.google.gson.annotations.SerializedName

data class WikimediaEvent(
    @SerializedName("pages")
    val pages: List<Page>,
    @SerializedName("text")
    val text: String,
    @SerializedName("year")
    val year: Int,
    var language: String,
) {
    companion object {
        const val matchPattern = "[(-.,:; '\\s,;]"
        const val startsWithPattern = "[^S]"
    }
}
