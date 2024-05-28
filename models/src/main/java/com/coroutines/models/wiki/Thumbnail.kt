package com.coroutines.models.wiki

import com.google.gson.annotations.SerializedName

data class Thumbnail(
    @SerializedName("height", alternate = ["h"])
    val height: Int,
    @SerializedName("source", alternate = ["s"])
    var source: String,
    @SerializedName("width", alternate = ["w"])
    val width: Int,
)
