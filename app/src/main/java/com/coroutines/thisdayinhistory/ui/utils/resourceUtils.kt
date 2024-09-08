package com.coroutines.thisdayinhistory.ui.utils

import com.coroutines.thisdayinhistory.R
import java.lang.reflect.Field
import java.util.concurrent.ConcurrentHashMap

inline fun <reified T: Class<*>> T.getId(resourceName: String): Int {
    return try {
        val idField = getDeclaredField (resourceName)
        idField.getInt(idField)
    } catch (e:Exception) {
        e.printStackTrace()
        -1
    }
}

object ResourceLookUpByReflection {
    private var typeCache: MutableMap<String, Int?> =  ConcurrentHashMap()

    @Suppress("ReturnCount")
    fun getResourceId(name: String) : Int {
        if (typeCache.containsKey(name)) {
            return typeCache[name]!!
        }

        return try{
            val field: Field =  R.drawable::class.java.getField(name)
            val resId = field.getInt(null)
            typeCache[name] = resId

            resId
        } catch (e: Exception){
            0
        }


    }
}

fun getResourceId(name: String) : Int {
    return ResourceLookUpByReflection.getResourceId(name)
}
