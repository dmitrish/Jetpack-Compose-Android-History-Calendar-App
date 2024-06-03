package com.coroutines.models.wiki
import com.google.gson.annotations.SerializedName

data class WikiEvent(
    @SerializedName(
        value = "events",
        alternate = [
            "births",
            "deaths",
            "selected",
            "Selected",
            "Births",
            "Deaths",
            "Events",
            "holidays"
        ]
    )
    val wikimediaEvents: List<WikimediaEvent>,
)
