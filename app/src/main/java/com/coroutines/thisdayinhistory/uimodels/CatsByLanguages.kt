package com.coroutines.thisdayinhistory.uimodels

import com.coroutines.data.models.EventCategoryEnum
import com.coroutines.data.models.LangEnum

data class CatsByLanguage(val langEnum: LangEnum){
    fun getCategories(): Map<EventCategoryEnum, String> {
        return when (langEnum){
            LangEnum.ENGLISH -> catEN
            LangEnum.ARABIC -> catAR
            LangEnum.FRENCH -> catFR
            LangEnum.GERMAN -> catDE
            LangEnum.ITALIAN -> catIT
            LangEnum.RUSSIAN -> catRU
            LangEnum.SPANISH -> catES
            LangEnum.SWEDISH -> catSE
            LangEnum.PORTUGUESE -> catPT
        }
    }

    fun getEnglishCategoryFromValue(value: String) : EventCategoryEnum {
        return when (langEnum){
            LangEnum.ENGLISH -> catEN.filterValues { it == value }.keys.first()
            LangEnum.ARABIC -> catAR.filterValues { it == value }.keys.first()
            LangEnum.FRENCH -> catFR.filterValues { it == value }.keys.first()
            LangEnum.GERMAN -> catDE.filterValues { it == value }.keys.first()
            LangEnum.ITALIAN -> catIT.filterValues { it == value }.keys.first()
            LangEnum.RUSSIAN -> catRU.filterValues { it == value }.keys.first()
            LangEnum.SPANISH -> catES.filterValues { it == value }.keys.first()
            LangEnum.SWEDISH -> catSE.filterValues { it == value }.keys.first()
            LangEnum.PORTUGUESE -> catPT.filterValues { it == value }.keys.first()
        }
    }

    fun getDefaultCategory(): String {
        return when (langEnum){
            LangEnum.ENGLISH -> catEN.getValue(EventCategoryEnum.selected)
            LangEnum.ARABIC -> catAR.getValue(EventCategoryEnum.selected)
            LangEnum.FRENCH -> catFR.getValue(EventCategoryEnum.selected)
            LangEnum.GERMAN -> catDE.getValue(EventCategoryEnum.selected)
            LangEnum.ITALIAN -> catIT.getValue(EventCategoryEnum.selected)
            LangEnum.RUSSIAN -> catRU.getValue(EventCategoryEnum.selected)
            LangEnum.SPANISH -> catES.getValue(EventCategoryEnum.selected)
            LangEnum.SWEDISH -> catSE.getValue(EventCategoryEnum.selected)
            LangEnum.PORTUGUESE -> catPT.getValue(EventCategoryEnum.selected)
        }
    }

    companion object {
        val catEN = mapOf(
            EventCategoryEnum.selected to "selected",
            EventCategoryEnum.events to "events",
            EventCategoryEnum.births to "births",
            EventCategoryEnum.deaths to "deaths")
        val catES = mapOf(
            EventCategoryEnum.selected to "titulares",
            EventCategoryEnum.events to "acontecimientos",
            EventCategoryEnum.births to "nacimienetos",
            EventCategoryEnum.deaths to "fallecimientos")
        val catFR = mapOf(
            EventCategoryEnum.selected to "les titres",
            EventCategoryEnum.events to "événements",
            EventCategoryEnum.births to "naissances",
            EventCategoryEnum.deaths to "décès")
        val catPT = mapOf(
            EventCategoryEnum.selected to "manchetes",
            EventCategoryEnum.events to "eventos",
            EventCategoryEnum.births to "aniversários",
            EventCategoryEnum.deaths to "mortes")
        val catDE = mapOf(
            EventCategoryEnum.selected to "Schlagzeilen",
            EventCategoryEnum.events to "Ereignisse",
            EventCategoryEnum.births to "Geboren",
            EventCategoryEnum.deaths to "Gestorben")
        val catSE = mapOf(
            EventCategoryEnum.selected to "rubriker",
            EventCategoryEnum.events to "händelser",
            EventCategoryEnum.births to "födelsedagar",
            EventCategoryEnum.deaths to "dödsfall")
        val catAR = mapOf(
            EventCategoryEnum.selected to "العناوين",
            EventCategoryEnum.events to "الأحداث",
            EventCategoryEnum.births to "الذي ولد",
            EventCategoryEnum.deaths to "من مات")
        val catRU = mapOf(
            EventCategoryEnum.selected to "избранное",
            EventCategoryEnum.events to "что произошло",
            EventCategoryEnum.births to "кто родился",
            EventCategoryEnum.deaths to "кто умер")
        val catIT = mapOf(
            EventCategoryEnum.selected to "titoli",
            EventCategoryEnum.events to "eventi",
        )
    }
}

