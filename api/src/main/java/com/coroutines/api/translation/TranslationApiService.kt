package com.coroutines.api.translation

import com.coroutines.data.models.TranslateResult
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TranslationApiService {

    @GET("t?client=any_client_id_works&sl=auto&tbb=1&ie=UTF-8&oe=UTF-8")
    @Headers("Content-Type: application/json")
    suspend fun getTranslation(
        @Query("tl") language: String,
        @Query("q") text: String,
    ): TranslateResult

    companion object {
        const val BASE_URL = "https://translate.google.so/translate_a/"
    }
}
