package com.coroutines.thisdayinhistory.ui.appbar

import androidx.compose.material3.LocalContentColor
import com.coroutines.thisdayinhistory.R
import com.coroutines.thisdayinhistory.ui.viewmodels.IHistoryViewModel
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("LongParameterList")
@Composable
fun AppBar(
    drawerState: DrawerState? = null,
    navigationIcon: (@Composable () -> Unit)? = null,
    historyViewModel: IHistoryViewModel,
    @StringRes title: Int? = null,
    @StringRes cancelButtonText: Int,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
) {
    TopAppBar(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = {}/* {
            title?.let {
                Text(
                    text = stringResource(R.string.title),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }*/,
        actions = {
            /*appBarActions?.let {
                for (appBarAction in it) {
                    AppBarAction(appBarAction)
                }
            }*/

            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface) {
                var showDatePicker by remember {
                    mutableStateOf(false)
                }

                IconButton(
                    onClick = {
                        showDatePicker = true
                    },
                    ) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = stringResource(R.string.appbar_calendar)
                    )
                }
            }
        },
        navigationIcon = {
            if (drawerState != null && navigationIcon == null){
                DrawerIcon(drawerState = drawerState)
            } else {
                navigationIcon?.invoke()
            }
        },
        scrollBehavior = scrollBehavior
    )
}


@Composable
private fun DrawerIcon(drawerState: DrawerState) {
    val coroutineScope = rememberCoroutineScope()
    IconButton(onClick = {
        coroutineScope.launch {
            drawerState.open()
        }
    }) {
        Icon(
            Icons.Rounded.Menu,
            tint = MaterialTheme.colorScheme.onBackground,
            contentDescription = "hello"
        )
    }
}