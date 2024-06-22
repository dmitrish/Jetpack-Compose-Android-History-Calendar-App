package com.coroutines.thisdayinhistory.ui.state


sealed class DataRequestState {
    data object NotStarted : DataRequestState()
    data object Started : DataRequestState()
    data class CompletedSuccessfully(val requestCategory: RequestCategory = RequestCategory.Data) : DataRequestState()
    data class CompletedWithError(val errorMessage: String) : DataRequestState()
}

enum class RequestCategory{
    Data,
    Option
}
