package com.coroutines.models.wiki

import com.google.gson.annotations.SerializedName

data class Page(
    @SerializedName("description", alternate = ["d"])
    val description: String? = null,
    @SerializedName("extract", alternate = ["e"])
    val extract: String,
    val extractHtml: String? = null,
    @SerializedName("originalimage")
    val originalImage: OriginalImage? = null,
    @SerializedName("pageid")
    val pageId: Int,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail? = null,
    @SerializedName("title", alternate = ["t"])
    val title: String,
    @SerializedName("videoUrl")
    val videoUrl: String? = null,
)
