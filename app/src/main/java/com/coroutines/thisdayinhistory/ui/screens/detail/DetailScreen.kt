package com.coroutines.thisdayinhistory.ui.screens.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.coroutines.data.models.HistoricalEvent
import com.coroutines.models.wiki.Page
import com.coroutines.thisdayinhistory.components.ADJUSTED_COLOR_DARKER_FACTOR_ON_DARK_THEME
import com.coroutines.thisdayinhistory.components.ADJUSTED_COLOR_LIGHTER_FACTOR
import com.coroutines.thisdayinhistory.components.DARKER_BG_FACTOR
import com.coroutines.thisdayinhistory.components.MIN_LUMINANCE
import com.coroutines.thisdayinhistory.components.NAV_ARGUMENT_DOMINANT_COLOR
import com.coroutines.thisdayinhistory.components.NAV_ARGUMENT_HISTORY_EVENT
import com.coroutines.thisdayinhistory.ui.components.CollapsingEffectScreen
import com.coroutines.thisdayinhistory.ui.components.DominantColorState
import com.coroutines.thisdayinhistory.ui.components.MinContrastOfPrimaryVsSurface
import com.coroutines.thisdayinhistory.ui.components.contrastAgainst
import com.coroutines.thisdayinhistory.ui.components.rememberDominantColorState
import com.coroutines.thisdayinhistory.ui.configurations.StyleConfiguration
import com.coroutines.thisdayinhistory.ui.modifiers.pagerOffsetAnimation
import com.coroutines.thisdayinhistory.ui.utils.darker
import com.coroutines.thisdayinhistory.ui.utils.lighter
import com.coroutines.thisdayinhistory.ui.viewmodels.DetailViewModel
import com.coroutines.thisdayinhistory.ui.viewmodels.IDetailViewModel


fun HistoricalEvent.isDefault(): Boolean{
    return this.description == HistoricalEvent.DEFAULT_DESCRIPTION
}

fun <T> NavController.getArgument(handleName: String): T? {
    return this.previousBackStackEntry?.savedStateHandle?.get<T>(handleName)
}

@OptIn(ExperimentalFoundationApi::class)
@Suppress("LongParameterList")
@Composable
fun DetailScreen (
    modifier: Modifier,
    navController: NavController,
    backHandler: () -> Unit,
    darkThemeHandler: () -> Unit,
    styleConfiguration: StyleConfiguration,
    viewModel: IDetailViewModel = DetailViewModel()
) {
    if (viewModel.isSelectedEventPlaceholder) {
        navController
            .getArgument<HistoricalEvent>(NAV_ARGUMENT_HISTORY_EVENT)?.let {
                viewModel.selectedEvent = it
            }
    }

    val pages = viewModel.pages

    val pagerState = rememberPagerState(pageCount = {
        pages.size
    })

    if (styleConfiguration.isDark) {
        darkThemeHandler()
    }

    BackHandler {
        backHandler()
    }

    val regularBackground = MaterialTheme.colorScheme.background
    val dominantColor = navController
        .getArgument<String>(NAV_ARGUMENT_DOMINANT_COLOR)?.toULong()
        ?: regularBackground.value

    var imageDisplayed by remember { mutableStateOf (viewModel.selectedEvent.imageUrl ?: "") }
    var adjustedColor by remember { mutableStateOf (if (styleConfiguration.isDark) Color(dominantColor).darker(
        ADJUSTED_COLOR_DARKER_FACTOR_ON_DARK_THEME) else regularBackground) }
    val dominantColorState = rememberDominantColorState { color ->
        color.contrastAgainst(styleConfiguration.contrastAgainstColor) >= MinContrastOfPrimaryVsSurface
    }
    LaunchedEffect(imageDisplayed) {
        dominantColorState.updateColorsFromImageUrl(imageDisplayed)
    }

    PreloadImages(pages = pages, dominantColorState = dominantColorState)

    HorizontalPager(
        modifier = Modifier
            .background(adjustedColor)
            .padding(
                top = 50.dp,
                bottom = 50.dp
            ),
        state = pagerState,
        verticalAlignment = Alignment.Top,
    ) { page ->
        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { item ->
                imageDisplayed = pages[item].getThumbnail()
                try {
                    if (styleConfiguration.isDark) {
                        adjustedColor =
                            dominantColorState.calculateDominantColor(url = imageDisplayed)!!.color.darker(
                                DARKER_BG_FACTOR
                            )
                        if (adjustedColor.luminance() < MIN_LUMINANCE){
                            adjustedColor = adjustedColor.lighter(
                                ADJUSTED_COLOR_LIGHTER_FACTOR)
                        }
                    }
                }
                catch(e: Exception){
                    println("snapshotFlow exception: ${e.message}")
                }
            }
        }

        Column(
            modifier = Modifier
                .background(adjustedColor)
                .pagerOffsetAnimation(pagerState, page),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imageUrl = pages[page].getDisplayImage()
            val loadingUrl = pages[page].getThumbnail()
            val title = pages[page].title.replace("_", " ")
            val bodyText = pages[page].extract

            CollapsingEffectScreen(
                modifier,
                styleConfiguration,
                adjustedColor,
                pagerState,
                imageUrl,
                loadingUrl,
                title,
                bodyText,
            )
        }
    }
}

@Composable
private fun PreloadImages(pages: List<Page>, dominantColorState: DominantColorState) {
    Column() {
        Modifier.size(1.dp)
        pages.forEach { page ->
            val key = page.getDisplayImage()
            LaunchedEffect(page) {
                dominantColorState.calculateDominantColor(key)
            }
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(key)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .memoryCacheKey(key)
                    .crossfade(true)
                    .build(),
                contentDescription = key,
                Modifier.size(1.dp)
            )
        }
    }
}
