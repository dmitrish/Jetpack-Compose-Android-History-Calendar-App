package com.coroutines.thisdayinhistory.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.coroutines.data.models.EventCategoryEnum
import com.coroutines.thisdayinhistory.ui.components.ChoiceChipContent
import com.coroutines.thisdayinhistory.ui.components.GrayishBlack
import com.coroutines.thisdayinhistory.ui.utils.lighter


@Composable
@Suppress("MagicNumber")
fun HistoryViewCategoryTabs(
    categories: List<String>,
    selectedCategory: String,
    tabItemsPadding: Dp,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedIndex = categories.indexOfFirst { it == selectedCategory }
    ScrollableTabRow(
        contentColor = MaterialTheme.colorScheme.background.lighter(0.2f),
        containerColor = MaterialTheme.colorScheme.background,
        selectedTabIndex = selectedIndex,
        divider = {}, // Disable the built-in divider
        edgePadding = tabItemsPadding,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                color = GrayishBlack,
                height = 3.dp,
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[selectedIndex])
                    .padding(horizontal = 35.dp)
            )
        },
        modifier = modifier
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = index == selectedIndex,
                onClick = { onCategorySelected(category) }
            ) {
                ChoiceChipContent(
                    text = category,
                    selected = index == selectedIndex,
                    modifier = Modifier.padding(horizontal = 3.dp, vertical = 1.dp)
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun HistoryViewCategoryTabsPreview() {
    val categories = mutableListOf<String>()
    EventCategoryEnum.entries.forEach {
        categories.add(it.name)
    }
    HistoryViewCategoryTabs(categories, "events", 3.dp, {})
}