package com.coroutines.thisdayinhistory.ui.screens.main

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coroutines.thisdayinhistory.ui.components.ShimmerAnimation
import com.coroutines.thisdayinhistory.ui.theme.JosefinSans
import com.coroutines.thisdayinhistory.ui.theme.Montserrat
import com.coroutines.thisdayinhistory.ui.viewmodels.IHistoryViewModel


@Composable
fun HistoryScreenLoading(vm: IHistoryViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        val state = vm.uiState.collectAsStateWithLifecycle()
        val categories = state.value.catsByLanguage.getCategories().values.toList()
        val tabItemsPadding = 16.dp // if (vm.windowSize.widthSizeClass > WindowWidthSizeClass.Compact) 50.dp else 16.dp
        HistoryViewCategoryTabs(
            categories = categories,
            state.value.selectedCategory,
            onCategorySelected = vm::onCategoryChanged,
            tabItemsPadding = tabItemsPadding,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        )
        Box(contentAlignment = Alignment.Center) {
            ShimmerAnimation()
            Column() {
                val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransitionForBrushOffset")
                val offset by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 2000, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "brushOffsetFloatAnimation"
                )

                val brush = shaderBrush(offset)

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = state.value.selectedDate.day.toString(),
                    style = TextStyle(
                        brush = brush,
                        fontFamily = Montserrat,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 59.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = state.value.selectedDate.month,
                    style = TextStyle(
                        brush = brush,
                        fontFamily = JosefinSans,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 37.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun shaderBrush(offset: Float) = remember(offset) {
    object : ShaderBrush() {
        override fun createShader(size: Size): Shader {
            val widthOffset = size.width * offset
            val heightOffset = size.height * offset
            return LinearGradientShader(
                colors = listOf(
                    Color.Black,
                    Color.Gray,
                    Color.Black,
                    Color.Black
                ),
                from = Offset(widthOffset, heightOffset),
                to = Offset(widthOffset + size.width, heightOffset + size.height),
                tileMode = TileMode.Mirror
            )
        }
    }
}

/*
@Composable
@Preview
fun PreviewScreenLoading(
    @PreviewParameter(HistoryCatThemeEnumProvider::class) historyCatThemeEnum: HistoryCatThemeEnum
) {

    val historyViewModel = HistoryViewModelMock()
    val settingsViewModel = SettingsViewModelMock(historyCatThemeEnum)

    HistoryCatTheme(
        settingsViewModel
    ) {
        val appThemeColor = MaterialTheme.colorScheme.background
        Surface(
            modifier = Modifier.background(appThemeColor)
        ) {
            ScreenLoading(vm = historyViewModel)
        }
    }
}*/