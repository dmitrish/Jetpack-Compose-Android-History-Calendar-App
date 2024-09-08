package com.coroutines.thisdayinhistory.ui.screens.detail

import com.coroutines.models.wiki.Page


fun Page.getThumbnail(): String{
    return when (this.thumbnail ){
        null -> PLACEHOLDER_IMAGE_URL
        else -> when {
            this.thumbnail!!.source.isEmpty() -> PLACEHOLDER_IMAGE_URL
            else -> this.thumbnail!!.source
        }
    }
}

fun Page.getDisplayImage() : String {
    val originalImageIsFit = this.originalImage?.let {
        it.width < MAX_IMAGE_WIDTH_HEIGHT && it.height < MAX_IMAGE_WIDTH_HEIGHT
    } ?: false

    val result = when {
        originalImageIsFit -> this.originalImage!!.source
        else -> this.thumbnail?.source ?: PLACEHOLDER_IMAGE_URL
    }

    return result
}

private const val MAX_IMAGE_WIDTH_HEIGHT = 500
@Suppress("MaxLineLength")
private const val PLACEHOLDER_IMAGE_URL = "https://images.unsplash.com/photo-1579546929518-9e396f3cc809?q=80&w=2340&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
