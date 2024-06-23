package com.coroutines.thisdayinhistory.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.coroutines.data.models.HistoricalEvent
import com.coroutines.models.wiki.Page
import com.coroutines.thisdayinhistory.ui.screens.detail.isDefault

class DetailViewModel
    : ViewModel(), IDetailViewModel {

    override var selectedEvent: HistoricalEvent = HistoricalEvent(HistoricalEvent.DEFAULT_DESCRIPTION)
    override val isSelectedEventPlaceholder = selectedEvent.isDefault()
    override var historyImage = mutableStateOf<String> ( "")
    override val pages : List<Page>
        get() = selectedEvent.pages
            .asSequence()
            .filter { it.videoUrl != null ||  !it.extract.contains("foi um ano") }
            .filter { it.videoUrl != null || !it.extract.contains("Cette page concerne")}
            .filter { it.videoUrl != null || !it.extract.contains("по григорианскому")}
            .filter  { it.videoUrl != null || !it.extract.contains("est une année")}
            .filter { it.videoUrl != null || it.thumbnail != null || it.originalImage != null }
            .filter { it.videoUrl != null || !it.thumbnail?.source.isNullOrEmpty() ||
                    !it.originalImage?.source.isNullOrEmpty()}
            .filter { it.videoUrl != null || it.thumbnail?.source!!.isNotBlank() ||
                    it.originalImage?.source!!.isNotBlank()}
            .toList()
}