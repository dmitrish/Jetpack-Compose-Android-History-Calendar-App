package com.coroutines.data.converters
import com.coroutines.data.models.CountryCodeMapping
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.reflect.KClass

open class JsonConverterService : IJsonConverterService {
    private val gson = Gson()

    fun getCountryMappings(countryMappingsJson: String): List<CountryCodeMapping>? {
        val itemType = object : TypeToken<List<CountryCodeMapping>>() {}.type
        return gson.fromJson(countryMappingsJson, itemType)
    }

    inline fun <reified T> convert(json: String): T {
        val gson = Gson()
        val itemType = T::class.java
        return gson.fromJson(json, itemType)
    }

    inline fun <reified T> convertList(json: String): List<T> {
        val gson = Gson()
        val itemType = object : TypeToken<List<T>>() {}.type
        return gson.fromJson(json, itemType)
    }

    override fun <T : Any> convert(json: String, clazz: KClass<T>): T {
        val gson = Gson()
        val itemType = object : TypeToken<T>() {}.type
        return gson.fromJson(json, itemType)
    }

    override fun <T : Any> convertToJon(t: T, clazz: KClass<T>): String {
        val gson = Gson()
        val itemType = object : TypeToken<T>() {}.type
        return gson.toJson(t, itemType)
    }

    override fun <T : Any> convertList(json: String, clazz: KClass<T>): List<T> {
        val gson = Gson()
        val typeOfT = TypeToken.getParameterized(MutableList::class.java, clazz.java).type
        return gson.fromJson(json, typeOfT)
    }

    override fun <T : Any> convertListToJson(list: List<T>, clazz: KClass<T>): String {
        val gson = Gson()
        val typeOfT = TypeToken.getParameterized(MutableList::class.java, clazz.java).type
        return gson.toJson(list, typeOfT)
    }
}