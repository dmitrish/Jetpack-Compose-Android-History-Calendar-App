package com.coroutines.api.wiki

import com.coroutines.models.synonyms.HistoryDay
import com.coroutines.models.synonyms.HistoryMonth
import com.coroutines.models.wiki.WikiEvent
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface WikiMediaApiService {

    @GET("{lang}/onthisday/{option}/{month}/{day}")
    @Headers("Content-Type: application/json")
    suspend fun getWikimediaPage(
        @Path("month") month: HistoryMonth,
        @Path("day") day: HistoryDay,
        @Path("lang") language: String = "en",
        @Path("option") option: String = "selected",
    ): WikiEvent

    companion object {
        const val BASE_WIKI_URL = "https://api.wikimedia.org/feed/v1/wikipedia/"
    }
}
