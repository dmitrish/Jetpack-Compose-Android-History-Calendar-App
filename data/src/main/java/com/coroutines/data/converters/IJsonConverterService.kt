package com.coroutines.data.converters

import kotlin.reflect.KClass

interface IJsonConverterService {
    fun <T : Any> convert(json: String, clazz: KClass<T>): T
    fun <T : Any> convertList(json: String, clazz: KClass<T>): List<T>
    fun <T : Any> convertToJon(t: T, clazz: KClass<T>): String
    fun <T : Any> convertListToJson(list: List<T>, clazz: KClass<T>): String
}