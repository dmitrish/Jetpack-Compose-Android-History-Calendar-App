package com.coroutines.thisdayinhistory.drawer

import androidx.compose.runtime.Composable
import com.coroutines.thisdayinhistory.ui.utils.AppIcons

sealed class NavDrawerItem(var route: String, var icon: Int, var title: String) {
    object Home : NavDrawerItem("HistoryScreen", AppIcons.Home, "drawer_home")
    object About : NavDrawerItem("AboutScreen", AppIcons.About, "drawer_about")
    object Language : NavDrawerItem("LanguagesScreen", AppIcons.Language, "drawer_language")
    object Theme : NavDrawerItem("ThemeScreen", AppIcons.Settings, "drawer_settings")
    object Widget: NavDrawerItem("WidgetScreen", AppIcons.Widget, "drawer_widget")
}

@Composable
fun navDrawerItems() = listOf(
    NavDrawerItem.Home,
    NavDrawerItem.About,
    NavDrawerItem.Language,
    NavDrawerItem.Theme,
    NavDrawerItem.Widget
)
