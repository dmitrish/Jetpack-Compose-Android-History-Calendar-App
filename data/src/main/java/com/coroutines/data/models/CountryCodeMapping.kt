package com.coroutines.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
@Parcelize
data class CountryCodeMapping(
    @SerializedName("name") var name: String,
    @SerializedName("namePT") var namePT: String? = null,
    @SerializedName("namePTVariants") var namePTVariants: String? = null,
    @SerializedName("nameRU") var nameRU: String? = null,
    @SerializedName("nameRUVariants") var nameRUVariants: String? = null,
    @SerializedName("nameSV") var nameSV: String? = null,
    @SerializedName("nameSVVariants") var nameSVVariants: String? = null,
    @SerializedName("nameIT") var nameIT: String? = null,
    @SerializedName("nameITVariants") var nameITVariants: String? = null,
    @SerializedName("nameAR") var nameAR: String? = null,
    @SerializedName("nameARVariants") var nameARVariants: String? = null,
    @SerializedName("nameFR") var nameFR: String? = null,
    @SerializedName("nameFRVariants") var nameFRVariants: String? = null,
    @SerializedName("nameES") var nameES: String? = null,
    @SerializedName("nameESVariants") var nameESVariants: String? = null,
    @SerializedName("nameDE") var nameDE: String? = null,
    @SerializedName("nameDEVariants") var nameDEVariants: String? = null,
    @SerializedName("alpha-2") var alpha2: String? = null,
    @SerializedName("country-code") var countryCode: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("classification") var classification: String? = null,
) : Parcelable