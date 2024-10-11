package com.coroutines.thisdayinhistory

import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import coil.size.Size
import com.coroutines.data.models.HistoricalEvent
import com.coroutines.data.models.LangEnum
import com.coroutines.data.models.Languages
import com.coroutines.thisdayinhistory.components.rememberSystemUiController
import com.coroutines.thisdayinhistory.ui.components.CatLogo
import com.coroutines.thisdayinhistory.ui.constants.HISTORY_LIST_ITEM_ROW_TAG
import com.coroutines.thisdayinhistory.ui.constants.HISTORY_LIST_ITEM_TAG
import com.coroutines.thisdayinhistory.ui.screens.main.HistoryListImage
import com.coroutines.thisdayinhistory.ui.screens.main.HistoryListItem
import com.coroutines.thisdayinhistory.ui.screens.main.HistoryTextHeaderStyle
import com.coroutines.thisdayinhistory.ui.screens.main.HistoryTextStyle
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryTheme
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum
import com.coroutines.thisdayinhistory.ui.utils.darker
import com.coroutines.thisdayinhistory.ui.utils.lighter
import com.coroutines.thisdayinhistory.ui.viewmodels.HistoryViewModel
import com.coroutines.thisdayinhistory.ui.viewmodels.HistoryViewModelMock
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModel
import com.coroutines.thisdayinhistory.ui.viewmodels.SettingsViewModelMock
import com.coroutines.thisdayinhistory.uimodels.ShareableHistoryEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GlanceConfigurationActivity : ComponentActivity() {

    private val appWidgetId: Int by lazy {
        intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID,
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @SuppressLint(
        "StateFlowValueCalledInComposition", "UnusedMaterialScaffoldPaddingParameter",
        "UnusedMaterial3ScaffoldPaddingParameter"
    )
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setResult(
            RESULT_CANCELED,
            Intent().apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            },
        )


        setContent {
            ConfigurationContent()


        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
@Preview
fun ConfigurationContent() {
        val settingsViewModel = SettingsViewModelMock(ThisDayInHistoryThemeEnum.Dark)
        val appConfigState = settingsViewModel.appConfigurationState.value

        val historyViewModel = HistoryViewModelMock()
        val data = historyViewModel.historyData

        ThisDayInHistoryTheme(viewModel = settingsViewModel) {
            val appThemeColor = MaterialTheme.colorScheme.background
            val appThemeOnBackground = MaterialTheme.colorScheme.onBackground
            val systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(
                color = appThemeColor,
                isNavigationBarContrastEnforced = false
            )

            val cardColors = CardColors(
                containerColor = MaterialTheme.colorScheme.background.lighter(0.5f),
                contentColor = MaterialTheme.colorScheme.onBackground,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                disabledContentColor = MaterialTheme.colorScheme.onBackground
            )

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appThemeColor)
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(appThemeColor)
                        .padding(10.dp)
                ) {
                    CatLogo(settings = appConfigState)
                    ElevatedCard(
                        Modifier.padding(
                            start = 4.dp,
                            end = 4.dp,
                            top = 1.dp
                        ),
                        colors = cardColors,
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 1.dp
                        )
                    )
                    {
                        LazyColumn(
                            Modifier
                                //.fillMaxSize()
                                .background(appThemeColor.lighter(0.5f))
                                .height(300.dp)
                        ) {
                            items(data) { item ->
                                Row(Modifier.padding(top = 5.dp)) {
                                    ConfigurationHistoryListItem(
                                        historyEvent = item,
                                        styles = buildList {
                                            add(
                                                HistoryTextStyle(
                                                    maxLines = 3,
                                                    style = TextStyle(
                                                        color = MaterialTheme.colorScheme.onBackground,
                                                        fontFamily = FontFamily.Monospace,
                                                        fontSize =  12.sp

                                                        )
                                                )
                                            )
                                            add(
                                                HistoryTextHeaderStyle(
                                                    maxLines = 1,
                                                    lineHeight = 14.sp,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            )
                                        },
                                        cardColors = cardColors,
                                        onClick = {},
                                        onImageClick = {}
                                    ) {

                                    }

                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(50.dp))
                    val pagerState = rememberPagerState(pageCount = {
                        10
                    })
                    HorizontalPager(state = pagerState,
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) { page ->
                        // Our page content
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                //.aspectRatio(1f)
                                .clip(RoundedCornerShape(16.dp))
                               // .background(Color.Green)
                        ) {
                            Text(
                                text = Languages.SWEDISH.native,
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 50.sp,
                            )
                        }
                       /* Text(
                            text = "Page: $page",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        )*/
                    }
                }
            }
        }
    }




@Composable
inline fun ConfigurationHistoryListItem(
    historyEvent: HistoricalEvent,
    styles: List<HistoryTextStyle> = buildList {
        add(HistoryTextStyle(style = MaterialTheme.typography.bodyMedium))
        add(HistoryTextHeaderStyle(
            maxLines = 1,
            lineHeight = 20.sp,
            style = MaterialTheme.typography.bodyMedium
        ))
    },
    cardColors: CardColors = CardColors(
        containerColor = MaterialTheme.colorScheme.background.darker(0.01f),
        contentColor = MaterialTheme.colorScheme.onBackground,
        disabledContainerColor = MaterialTheme.colorScheme.background,
        disabledContentColor = MaterialTheme.colorScheme.onBackground
    ),
    //windowSizeClass: WindowSizeClass,
    crossinline onClick: (HistoricalEvent) -> Unit,
    crossinline onImageClick: (HistoricalEvent) -> Unit,
    crossinline onShare: (ShareableHistoryEvent) -> Unit
) {
    val context = LocalContext.current
    val imageSize = 38.dp
    val keyLine1 = 16.dp

    ElevatedCard (
        Modifier
            .padding(start = keyLine1, end = keyLine1, top =  1.dp),
        colors = cardColors,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ))
    {
        Row(
            Modifier
                .testTag(HISTORY_LIST_ITEM_ROW_TAG)
                .padding(start = keyLine1, top = keyLine1, bottom = 6.dp)
        ) {
            HistoryListImage(historyEvent, context, imageSize, onImageClick)

            Column(
                Modifier.padding(
                    start = keyLine1,
                    end = keyLine1
                )
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .testTag(HISTORY_LIST_ITEM_TAG)
                            .height(21.dp),
                        text = historyEvent.year.toString(),
                        textAlign = TextAlign.Start,
                        maxLines = 1,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Text(
                    text = historyEvent.description,
                    maxLines = styles.filterIsInstance<HistoryTextStyle>()[0].maxLines,
                    lineHeight = 20.sp,
                    overflow = TextOverflow.Ellipsis,
                    style = styles.filterIsInstance<HistoryTextStyle>()[0].style,
                    modifier = Modifier
                        .padding(top = 16.dp, end = keyLine1.minus(12.dp))
                        .clickable(enabled = true) {
                            onClick(historyEvent)
                        }
                )

            }

        }

    }

}



/*  val settingsViewModel = hiltViewModel<SettingsViewModel>()
  val appConfigState = settingsViewModel.appConfigurationState.collectAsState()

  val historyViewModel =
      hiltViewModel<HistoryViewModel, HistoryViewModel.IHistoryViewModelFactory>(
          key = appConfigState.value.appLanguage.langId
      ) { factory ->
          factory.create(appConfigState.value.appLanguage)
      }
  val data = historyViewModel.historyData*/

/*  ThisDayInHistoryTheme(viewModel = settingsViewModel) {
      val appThemeColor = MaterialTheme.colorScheme.background
      val appThemeOnBackground = MaterialTheme.colorScheme.onBackground
      val systemUiController = rememberSystemUiController()
      systemUiController.setSystemBarsColor(
          color = appThemeColor,
          isNavigationBarContrastEnforced = false
      )
      Surface(
          modifier = Modifier
              .fillMaxSize()
              .background(appThemeColor)
      ) {
              Column(
                  Modifier
                      .fillMaxSize()
                      .background(appThemeColor)
                      .padding(20.dp)
              ) {
                 CatLogo(settings = appConfigState.value)
                  LazyColumn (
                      Modifier
                          .fillMaxSize()
                          .background(appThemeColor)
                  ) {
                      items(data){ item ->
                          Row{
                              ConfigurationHistoryListItem(
                                  historyEvent = item,
                                  styles = buildList {
                                      add(HistoryTextStyle(maxLines = 3, style = MaterialTheme.typography.bodySmall))
                                      add(HistoryTextHeaderStyle(maxLines = 1, lineHeight = 20.sp,  style = MaterialTheme.typography.bodyMedium))
                                  },
                                  onClick = {} ,
                                  onImageClick = {}
                              ) {

                              }

                          }
                      }
                  }
             }
          }
  }

       /*   setResult(
              RESULT_OK,
              Intent().apply {
                  putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
              }
          )
          finish()*/


}



}

private fun onItemClick() {
val context = this
lifecycleScope.launch {

  setResult(RESULT_OK, intent)
  finish()
}
}
*/