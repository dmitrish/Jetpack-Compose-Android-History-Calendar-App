package com.coroutines.thisdayinhistory.ui.screens.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.coroutines.thisdayinhistory.ui.viewmodels.ISettingsViewModel

@Composable
fun WidgetScreen(
    modifier: Modifier = Modifier,
    viewModel: ISettingsViewModel
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        val themeState by viewModel.appConfigurationState.collectAsState()

        Text ("this is a placeholder")
    }
}
