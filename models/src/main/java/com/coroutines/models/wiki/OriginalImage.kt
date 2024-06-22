package com.coroutines.models.wiki

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OriginalImage(
    @SerializedName("height", alternate = ["h"])
    val height: Int,
    @SerializedName("source", alternate = ["s"])
    var source: String,
    @SerializedName("width", alternate = ["w"])
    val width: Int,
) : Parcelable