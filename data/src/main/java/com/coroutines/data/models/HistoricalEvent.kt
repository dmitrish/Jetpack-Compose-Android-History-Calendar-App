package com.coroutines.data.models

import android.graphics.Bitmap
import android.os.Parcelable
import com.coroutines.data.helpers.isBC
import com.coroutines.models.wiki.OriginalImage
import com.coroutines.models.wiki.Page
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize


@Parcelize
data class HistoricalEvent(
    @SerializedName("description")
    val description: String,
    @SerializedName("year")
    val year: String? = null,
    val countryCodeMappings: List<CountryCodeMapping> = listOf(),
    val imageBigUrl: String? = null,
    val extract: String? = null,
    val pages: List<Page> = arrayListOf(),
    val optionSelected: String = EMPTY_STRING,
    val language: String = EMPTY_STRING,
    val originalImage: OriginalImage? = null,
    val shortTitle: String? = null,
    val wikiId: String? = null,
) : Parcelable {
    companion object {
        const val DEFAULT_DESCRIPTION = "None"
        private const val EMPTY_STRING = ""
        private const val MATCH_PATTERN = "[-.,:; '\\s,]"
        const val DEFAULT_IMAGE_URL = "https://placehold.co/60x60@2x.png"
    }

    infix fun matches(valueToMatch: String): Boolean {
        val pattern = Regex(valueToMatch + MATCH_PATTERN)
        return pattern.containsMatchIn(description)
    }

    var bitMap: Bitmap? = null

    @IgnoredOnParcel
    var countryCodeMap : List<CountryCodeMapping>? = null
    @IgnoredOnParcel

    val imageUrl by lazy { pages.firstOrNull { page -> page.thumbnail != null }?.thumbnail?.source
        ?: (imageBigUrl ?: DEFAULT_IMAGE_URL)}

    private val nonNullYear: String
        get() = if (year.isNullOrBlank()) "" else year

    @Suppress("MagicNumber")
    val century: Int
        get() {
            val year = nonNullYear.filter { it.isDigit() }
            return when (year.length) {
                2 -> 1
                3 -> nonNullYear.substring(0, 1).toInt() + 1
                4 -> nonNullYear.substring(0, 2).toInt() + 1
                else -> 1
            }.let {
                if (nonNullYear.isBC()) it.unaryMinus() else it
            }
        }
}
