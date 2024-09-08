package com.coroutines.thisdayinhistory.uimodels
import android.graphics.Bitmap

data class ShareableHistoryEvent(
    val text: String,
    val bitmap: Bitmap?,
    val bitMapName: String?,
    val shareWith: ShareWith = ShareWith.OTHER
)

