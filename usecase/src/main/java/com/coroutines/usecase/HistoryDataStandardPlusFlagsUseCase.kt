package com.coroutines.usecase

import com.coroutines.api.wiki.WikiMediaApi
import com.coroutines.data.converters.IJsonConverterService
import com.coroutines.data.models.CountryCodeMapping
import com.coroutines.data.models.HistoricalEvent
import com.coroutines.models.wiki.WikimediaEvent


class HistoryDataStandardPlusFlagsUseCase
constructor(
    wikiMediaApi: WikiMediaApi,
    private val jsonConverter: IJsonConverterService,
    private val countryCodeMappingsRawJson: CountryCodeMappingsRawJson? = null,
) : HistoryDataStandardUseCase(wikiMediaApi, jsonConverter){
    private var countryMappings: List<CountryCodeMapping> = mutableListOf()

    init {
        if (countryMappings.isEmpty()) {
            countryMappings = countryCodeMappingsRawJson?.let {
                jsonConverter.convertList(
                    countryCodeMappingsRawJson.value,
                    CountryCodeMapping::class
                )
            } ?: listOf()
        }
    }

    override fun buildHistoricalEvent(
        we: WikimediaEvent,
        option: String

    ): HistoricalEvent {
        val result = super.buildHistoricalEvent(we, option)
            .also { it.countryCodeMap = we.codeMappings(countryMappings) }
        return result
    }
}