package com.coroutines.usecase

import android.util.Log
import com.coroutines.data.extensions.matches
import com.coroutines.data.extensions.matchesByStartsWith
import com.coroutines.data.models.CountryCodeClassification
import com.coroutines.data.models.CountryCodeMapping
import com.coroutines.data.models.HistoricalEvent
import com.coroutines.models.wiki.WikimediaEvent


fun HistoricalEvent.codeMappings (countries: List<CountryCodeMapping>): List<CountryCodeMapping>{
    return getCountryCodesMappings(this, countries)
}

fun WikimediaEvent.codeMappings (countries: List<CountryCodeMapping>): List<CountryCodeMapping>{
    return getCountryCodesMappings(this, countries)
}

// private val regExPattern = "[-.,: '\\s]"
fun getCountryCodesMappings(historyEvent: HistoricalEvent, countries: List<CountryCodeMapping>): List<CountryCodeMapping> {
    var codes = mutableListOf<CountryCodeMapping>()
    countries.forEach{ cc ->
        val code = if (historyEvent matches cc.name) cc.alpha2 else null
        code?.let{
            if (!codes.any {
                    it.countryCode == cc.countryCode
                })
                codes.add(cc)
        }
    }

    if (codes.any {
            (!it.classification.isNullOrEmpty() && it.classification == CountryCodeClassification.US_STATE.readable)
        } && codes.none {
            it.alpha2 == CountryCodeClassification.US_COUNTRY.readable
        }) {
        val usCountry = countries.first { it.alpha2 == CountryCodeClassification.US_COUNTRY.readable }
        codes.add(usCountry)
    }

    codes = codes.sortedBy { e ->
        e.countryCode?.let { e.countryCode + e.name } ?: ("z${e.name}")
    }.toMutableList()


    // codes = codes.sortedBy { if(it.countryCode != null ) it.countryCode + it.name else "z" + it.name}.toMutableList()

    return codes.toList()

}

fun matchesVariant(historyEvent: WikimediaEvent, countryCodeMapping: CountryCodeMapping, language: String): Boolean{
    var result = false

    if ("ru" == language) {
        val variantsRU = countryCodeMapping.nameRUVariants?.split(";")
        variantsRU?.forEach { variant ->
            if (historyEvent matches variant || historyEvent matchesByStartsWith variant ) {
                result = true
                return@forEach
            }
        }
    }

    if ("sv" == language) {
        val variantsSV = countryCodeMapping.nameSVVariants?.split(";")
        variantsSV?.forEach { variant ->
            if (historyEvent matches variant) {
                result = true
                return@forEach
            }
        }
    }

    if ("pt" == language) {
        val variantsPT = countryCodeMapping.namePTVariants?.split(";")
        variantsPT?.forEach { variant ->
            if (historyEvent matches variant) {
                result = true
                return@forEach
            }
        }
    }

    if ("it" == language) {
        val variantsIT = countryCodeMapping.nameITVariants?.split(";")
        variantsIT?.forEach { variant ->
            if (historyEvent matches variant) {
                result = true
                return@forEach
            }
        }
    }
    if ("ar" == language) {
        val variantsAR = countryCodeMapping.nameARVariants?.split(";")
        variantsAR?.forEach { variant ->
            if (historyEvent matches variant) {
                result = true
                return@forEach
            }
        }
    }

    if ("fr" == language) {
        val variantsFR = countryCodeMapping.nameFRVariants?.split(";")
        variantsFR?.forEach { variant ->
            if (historyEvent matches variant) {
                result = true
                return@forEach
            }
        }
    }

    if ("es" == language) {
        val variantsES = countryCodeMapping.nameESVariants?.split(";")
        variantsES?.forEach { variant ->
            if (historyEvent matches variant) {
                result = true
                return@forEach
            }
        }
    }

    if ("de" == language) {
        val variantsDE = countryCodeMapping.nameDEVariants?.split(";")
        variantsDE?.forEach { variant ->
            if (historyEvent matches variant || historyEvent matchesByStartsWith variant) {
                result = true
                return@forEach
            }
        }
    }



    return result



    //return false
}

fun getCountryCodesMappings(wikimediaEvent: WikimediaEvent, countries: List<CountryCodeMapping>): List<CountryCodeMapping> {
    Log.d("codeMapping", "start")
    var codes = mutableListOf<CountryCodeMapping>()

    //try {


    countries.parallelStream().forEach { cc ->

        val code = when (wikimediaEvent.language) {
            "ru" -> if (wikimediaEvent matches cc.name
                || (!cc.nameRU.isNullOrEmpty() && wikimediaEvent matches cc.nameRU!!)
                || (!cc.nameRUVariants.isNullOrEmpty() && matchesVariant(
                    wikimediaEvent,
                    cc,
                    wikimediaEvent.language
                ))
            )
                cc.alpha2 else null


            "fr" -> if (wikimediaEvent matches cc.name
                || (!cc.nameFR.isNullOrEmpty() && wikimediaEvent matches cc.nameFR!!)
                || (!cc.nameFRVariants.isNullOrEmpty() && matchesVariant(
                    wikimediaEvent,
                    cc,
                    wikimediaEvent.language
                ))
            )
                cc.alpha2 else null


            "pt" -> if (wikimediaEvent matches cc.name
                || (!cc.namePT.isNullOrEmpty() && wikimediaEvent matches cc.namePT!!)
                || (!cc.namePTVariants.isNullOrEmpty() && matchesVariant(
                    wikimediaEvent,
                    cc,
                    wikimediaEvent.language
                ))
            )
                cc.alpha2 else null

            "es" -> if (wikimediaEvent matches cc.name
                || (!cc.nameES.isNullOrEmpty() && wikimediaEvent matches cc.nameES!!)
                || (!cc.nameESVariants.isNullOrEmpty() && matchesVariant(
                    wikimediaEvent,
                    cc,
                    wikimediaEvent.language
                ))
            )
                cc.alpha2 else null

            "de" -> if (wikimediaEvent matches cc.name
                || (!cc.nameDE.isNullOrEmpty() && wikimediaEvent matches cc.nameDE!!)
                || (!cc.nameDEVariants.isNullOrEmpty() && matchesVariant(
                    wikimediaEvent,
                    cc,
                    wikimediaEvent.language
                ))
            )
                cc.alpha2 else null

            "sv" -> if (wikimediaEvent matches cc.name
                || (!cc.nameSV.isNullOrEmpty() && wikimediaEvent matches cc.nameSV!!)
                || (!cc.nameSVVariants.isNullOrEmpty() && matchesVariant(
                    wikimediaEvent,
                    cc,
                    wikimediaEvent.language
                ))
            )
                cc.alpha2 else null


            "ar" -> if (wikimediaEvent matches cc.name
                || (!cc.nameAR.isNullOrEmpty() && wikimediaEvent matches cc.nameAR!!)
                || (!cc.nameARVariants.isNullOrEmpty() && matchesVariant(
                    wikimediaEvent,
                    cc,
                    wikimediaEvent.language
                ))
            )
                cc.alpha2 else null

            "en" -> if (wikimediaEvent matches cc.name) cc.alpha2 else null


            else -> {}
        }


        /* val code = if (wikimediaEvent matches cc.name
             || (!cc.nameRU.isNullOrEmpty() && wikimediaEvent matches cc.nameRU!!)
             || (!cc.nameRUVariants.isNullOrEmpty() && matchesVariant(wikimediaEvent,
                 cc,
                 wikimediaEvent.language))
             || (!cc.namePT.isNullOrEmpty() && wikimediaEvent matches cc.namePT!!) || (!cc.namePTVariants.isNullOrEmpty() && matchesVariant(
                 wikimediaEvent,
                 cc,
                 wikimediaEvent.language))
             || (!cc.nameSV.isNullOrEmpty() && wikimediaEvent matches cc.nameSV!!)
             || (!cc.nameSVVariants.isNullOrEmpty() && matchesVariant(wikimediaEvent,
                 cc,
                 wikimediaEvent.language))
             || (!cc.nameAR.isNullOrEmpty() && wikimediaEvent matches cc.nameAR!!) || (!cc.nameARVariants.isNullOrEmpty() && matchesVariant(
                 wikimediaEvent,
                 cc,
                 wikimediaEvent.language))
             || (!cc.nameIT.isNullOrEmpty() && wikimediaEvent matches cc.nameIT!!)
             || (!cc.nameITVariants.isNullOrEmpty() && matchesVariant(wikimediaEvent,
                 cc,
                 wikimediaEvent.language))
             || (!cc.nameFR.isNullOrEmpty() && wikimediaEvent matches cc.nameFR!!) || (!cc.nameFRVariants.isNullOrEmpty() && matchesVariant(
                 wikimediaEvent,
                 cc,
                 wikimediaEvent.language))
             || (!cc.nameES.isNullOrEmpty() && wikimediaEvent matches cc.nameES!!) || (!cc.nameESVariants.isNullOrEmpty() && matchesVariant(
                 wikimediaEvent,
                 cc,
                 wikimediaEvent.language))
             || (!cc.nameDE.isNullOrEmpty() && wikimediaEvent matches cc.nameDE!!) || (!cc.nameDEVariants.isNullOrEmpty() && matchesVariant(
                 wikimediaEvent,
                 cc,
                 wikimediaEvent.language))

         ) cc.alpha2 else null

         */
        code?.let {
            if (!codes.any {
                    it.countryCode == cc.countryCode
                })
                codes.add(cc)
        }
    }


    Log.d("codeMapping", "intermediate")
    if (codes.any {
            (!it.classification.isNullOrEmpty() && it.classification == CountryCodeClassification.US_STATE.readable)
        } && codes.none {
            it.alpha2 == CountryCodeClassification.US_COUNTRY.readable
        }) {
        val usCountry =
            countries.first { it.alpha2 == CountryCodeClassification.US_COUNTRY.readable }
        codes.add(usCountry)
    }

    codes = codes.sortedBy { e ->
        e.countryCode?.let { e.countryCode + e.name } ?: ("z${e.name}")
    }.toMutableList()

    Log.d("codeMapping", "end")
    //}
    //catch (e: Exception){
    //    Log.e("codeMappingException", e.message.toString())
    // }
    // codes = codes.sortedBy { if(it.countryCode != null ) it.countryCode + it.name else "z" + it.name}.toMutableList()

    return codes.toList()
}