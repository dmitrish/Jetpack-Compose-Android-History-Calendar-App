@file:Suppress("MagicNumber")
package com.coroutines.thisdayinhistory.ui.screens.main

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coroutines.thisdayinhistory.ui.components.DominantColorState
import com.coroutines.thisdayinhistory.ui.components.DynamicThemePrimaryColorsFromImage
import com.coroutines.thisdayinhistory.ui.components.ExpandedImage
import com.coroutines.thisdayinhistory.ui.components.verticalGradientScrim
import com.coroutines.thisdayinhistory.ui.viewmodels.IHistoryViewModel


fun getSheetContentSize(viewModel: IHistoryViewModel, readyState: Boolean): Float {
    val originalImage = viewModel.selectedItem.originalImage
    return if (readyState) {
        if (originalImage?.height!! > originalImage.width) 0.9f else 0.5f
    } else {
        0.9f
    }
}

@Composable
fun BottomSheetContent(viewModel: IHistoryViewModel, readyState: Boolean, dominantColorState: DominantColorState) {
    fun getPadding(): Dp {
        val originalImage = viewModel.selectedItem.originalImage
        return if (readyState) {
            if (originalImage?.height!! > originalImage.width) 0.dp else 25.dp
        } else {
            0.dp
        }
    }

    val sel = viewModel.selectedItem.description != "No Events"

    DynamicThemePrimaryColorsFromImage(dominantColorState) {
        Surface(
            modifier = Modifier
                .fillMaxHeight(getSheetContentSize(viewModel, sel))
                .verticalGradientScrim(
                    color = dominantColorState.color.copy(alpha = if (isSystemInDarkTheme()) 0.89f else 0.99f),
                    startYPercentage = 1f,
                    endYPercentage = 0f
                )
            // color = MaterialTheme.colors.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(
                        WindowInsets.navigationBars.only(WindowInsetsSides.Bottom)
                    )
                    .verticalGradientScrim(
                        color = dominantColorState.color.copy(alpha = if (isSystemInDarkTheme()) 0.89f else 0.99f),
                        startYPercentage = 1f,
                        endYPercentage = 0f
                    )
                    .padding(getPadding()),
                // .padding(bottom = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (readyState) {
                    Text(
                        text = viewModel.selectedItem.shortTitle!!.replace("_", " "),
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .padding(bottom = 10.dp),
                        color = dominantColorState.onColor,
                        textAlign = TextAlign.Center
                    )
                    ExpandedImage(viewModel.selectedItem)
                }
            }
        }
    }
}