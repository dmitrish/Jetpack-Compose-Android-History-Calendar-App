package com.coroutines.thisdayinhistory.drawer

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.coroutines.thisdayinhistory.ui.components.CatLogo
import com.coroutines.thisdayinhistory.ui.state.AppConfigurationState
import com.coroutines.thisdayinhistory.ui.theme.MetallicSilver
import com.coroutines.thisdayinhistory.ui.theme.ThisDayInHistoryThemeEnum
import com.coroutines.thisdayinhistory.ui.viewmodels.ISettingsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MagicNumber")
@OptIn( ExperimentalMaterial3Api::class)
@Composable
fun AppNavigationDrawerWithContent(
    navController: NavController,
    settingsViewModel: ISettingsViewModel,
    content: @Composable () -> Unit
) {
    val settingsViewModelState by settingsViewModel.appConfigurationState.collectAsStateWithLifecycle()
    val items = navDrawerItems()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedItem = remember { mutableStateOf(items[0]) }
    val backgroundColors = gradientColors(themeViewModelState = settingsViewModelState)
    var isItemImageExpanded by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column (
                    Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = backgroundColors
                            )
                        ))
                {
                    CatLogo(settings = settingsViewModelState )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    )
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route
                    items.forEach { item ->
                        BuildNavigationDrawerItem(
                            item,
                            currentRoute,
                            scope,
                            drawerState,
                            selectedItem,
                            navController
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "By coroutines.com",
                        color = Color.Yellow,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(12.dp)
                            .padding(bottom = 20.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    ) {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection ),
            topBar = {
               //todo
            },
            bottomBar = {
               //todo
            }
        ) { paddingValues ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding())
            ) {
                content()
            }
        }
    }
}

@Composable
private fun gradientColors(themeViewModelState: AppConfigurationState) =
    if (themeViewModelState.appTheme == ThisDayInHistoryThemeEnum.Dark)
        listOf(
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.background
        )
    else
        listOf(
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.background,
            MetallicSilver
        )