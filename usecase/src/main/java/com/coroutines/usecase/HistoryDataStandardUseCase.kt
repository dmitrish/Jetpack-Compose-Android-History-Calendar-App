package com.coroutines.usecase

import android.util.Log
import com.coroutines.api.wiki.WikiMediaApi
import com.coroutines.data.converters.IJsonConverterService
import com.coroutines.data.extensions.extract
import com.coroutines.data.extensions.shortTitle
import com.coroutines.data.models.CountryCodeMapping
import com.coroutines.data.models.HistoricalEvent
import com.coroutines.models.synonyms.HistoryDay
import com.coroutines.models.synonyms.HistoryMonth
import com.coroutines.models.wiki.OriginalImage
import com.coroutines.models.wiki.WikimediaEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.streams.asSequence

class CountryCodeMappingsRawJson (val value: String) {
}
open class HistoryDataStandardUseCase @Inject
constructor(
    private val wikiMediaApi: WikiMediaApi,
    private val jsonConverter: IJsonConverterService,

    ) : IHistoryDataStandardUseCase {

    protected open fun buildHistoricalEvent(
        we: WikimediaEvent,
        option: String

    ): HistoricalEvent {
        val historicalEvent = HistoricalEvent(
            description = we.text,
            year = we.year.toString(),
            imageBigUrl = mapImage(we),
            extract = we.extract,
            pages = we.pages,
            shortTitle = we.shortTitle,
            originalImage = mapBigImage(we),
            wikiId = mapWikiId(we, option)

        )
        return historicalEvent
    }

    override fun wikiFlow(
        month: HistoryMonth,
        day: HistoryDay,
        language: String,
        option: String,
    ): Flow<HistoricalEvent> = flow {
        Log.d(TAG, "Fetching remote data started")
        val result = wikiMediaApi.getHistoricalEvents(month, day, language, option)
        Log.d(TAG, "Fetching remote data completed")


        for (we in result.wikimediaEvents) {
            val historicalEvent = HistoricalEvent(
                description = we.text,
                year = we.year.toString(),
                imageBigUrl = mapImage(we),
                extract = we.extract,
                pages = we.pages,
                shortTitle = we.shortTitle,
                originalImage = mapBigImage(we),
                wikiId = mapWikiId(we, option)
            )
            historicalEvent.wikiId?.let {
                emit(historicalEvent)
            }
        }
    }


    override fun wikiFlowList(
        month: HistoryMonth,
        day: HistoryDay,
        language: String,
        option: String,
    ): Flow<List<HistoricalEvent>> = flow {
        Log.d(TAG, "Fetching remote data started for option: $option")
        val result = wikiMediaApi.getHistoricalEvents(month, day, language, option)

        val resultSize = result.wikimediaEvents.size
        val list: MutableList<HistoricalEvent> = mutableListOf()
        Log.d(TAG, "Fetching remote data for $option completed with size: $resultSize")
        for (we in result.wikimediaEvents) {
            try {
                we.language = language
                val historicalEvent = buildHistoricalEvent(we, option)

                if (historicalEvent.imageUrl != HistoricalEvent.DEFAULT_IMAGE_URL) {
                    historicalEvent.wikiId?.let {
                        list.add((historicalEvent))
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "Fetching remote data for $option exception: ${e.message}")
            }
        }

        Log.d(TAG, "Fetching remote data for $option emitted list with size: ${list.size}")
        if (list.isEmpty()) {
            Log.d(TAG, "Fetching remote data for $option - list is empty")
        }
        emit(list)
    }


    private fun mapWikiId(wikimediaEvent: WikimediaEvent, option: String): String? {
        return when (wikimediaEvent.pages.firstOrNull()) {
            null -> null
            else -> wikimediaEvent.pages.first().pageId.toString() + option
        }
    }

    private fun transformEvent(
        filtered: List<WikimediaEvent>,
        countryMappings: List<CountryCodeMapping>,
        optionSelected: String,
        language: String,
    ): Sequence<HistoricalEvent> {
        val transformed: Sequence<HistoricalEvent> = filtered.parallelStream().map { event ->
            event.language = language
            HistoricalEvent(
                event.text,
                event.year.toString(),
                listOf(),
                // it.codeMappings(countryMappings),
                mapImage(event),
                event.pages.firstOrNull { it.originalImage != null }?.extract
                    ?: event.pages.first().extract,
                event.pages,
                optionSelected,
                language,
                originalImage = mapBigImage(event),
                shortTitle = event.pages.firstOrNull { it.originalImage != null }?.title,
                wikiId = event.pages.firstOrNull()?.pageId.toString()
            )/*.also { he ->
                try {
                    he.imageUrl = event.pages.firstOrNull { it.thumbnail != null }?.thumbnail?.source
                        ?: he.imageBigUrl!!
                } catch (e: Exception) {
                    println("transformEvent error:" + e.message)
                }
            }*/
        }.filter { it.imageUrl.isNotBlank() }.asSequence()
        return transformed
    }

    private fun mapImage(it: WikimediaEvent): String? {
        var result: String? = null
        try {
            it.pages.forEach { page ->
                page.originalImage?.let { originalImage ->
                    adjustOriginalImage(originalImage)
                }
                page.thumbnail?.let { thumbnail ->
                    if (!thumbnail.source.startsWith(IMAGE_STANDARD_PREFIX)) {
                        thumbnail.source = IMAGE_CUSTOM_PREFIX + thumbnail.source
                    }
                }
            }

            val originalImage = it.pages.first { it.originalImage != null }.originalImage
            val thumbnailImage = it.pages.first { it.thumbnail != null }.thumbnail

            thumbnailImage?.let {
                result = if (it.width < MAX_THUMBNAIL_WIDTH) {
                    it.source
                } else {
                    originalImage?.source
                }
            }
            println("image path: $result")
        } catch (e: Exception) {
            println("mapImage: ${e.message}")
        }
        return result
    }

    private fun adjustOriginalImage(originalImage: OriginalImage) {
        if (!originalImage.source.startsWith(IMAGE_STANDARD_PREFIX)) {
            originalImage.source = IMAGE_CUSTOM_PREFIX + originalImage.source
        }
    }

    private fun mapBigImage(it: WikimediaEvent): OriginalImage? {
        var result: OriginalImage? = null
        try {
            val originalImage = it.pages.first { it.originalImage != null }.originalImage
            val thumbnailImage = it.pages.first { it.thumbnail != null }.thumbnail
            result = originalImage
            println("big image path: $result")
        } catch (e: Exception) {
            Log.e(TAG, "mapImage: ${e.message}")
        }
        return result
    }

    companion object {
        const val IMAGE_CUSTOM_PREFIX = "https://upload.wikimedia.org/wikipedia/"
        const val IMAGE_STANDARD_PREFIX = "https://"
        const val TAG = "HistoryDataStandardUseCase"
        const val MAX_THUMBNAIL_WIDTH = 400
    }
}