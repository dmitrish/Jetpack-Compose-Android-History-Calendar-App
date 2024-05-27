package com.coroutines.thisdayinhistory.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.coroutines.thisdayinhistory.R
import com.coroutines.thisdayinhistory.ui.utils.getId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Suppress("LongParameterList")
@Composable
fun BuildNavigationDrawerItem(
    item: NavDrawerItem,
    currentRoute: String?,
    scope: CoroutineScope,
    drawerState: DrawerState,
    selectedItem: MutableState<NavDrawerItem>,
    navController: NavController,
) {
    NavigationDrawerItem(
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = MaterialTheme.colorScheme.background,
            selectedContainerColor = MaterialTheme.colorScheme.background
        ),
        icon = {
            Image(
                painter = painterResource(id = item.icon),
                contentDescription = item.title,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(35.dp)
                    .width(35.dp)
            )
        },
        label = { Text(stringResource(id = R.string::class.java.getId(item.title))) },
        selected = currentRoute == item.route,
        onClick = {
            scope.launch { drawerState.close() }
            selectedItem.value = item
            navController.navigate(item.route) {
                launchSingleTop = true
                restoreState = true
            }
        },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}
