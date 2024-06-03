package com.coroutines.data.models

class TranslateResult : ArrayList<TranslateResultSubList>() {
    fun getResult(): String {
        return this[0][0]
    }
}