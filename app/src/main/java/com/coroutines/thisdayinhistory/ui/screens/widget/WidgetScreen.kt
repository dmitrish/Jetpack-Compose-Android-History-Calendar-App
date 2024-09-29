package com.coroutines.thisdayinhistory.ui.screens.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.coroutines.thisdayinhistory.R

import com.coroutines.thisdayinhistory.glance.DayInHistoryGlanceAppWidgetReceiver
import com.coroutines.thisdayinhistory.ui.components.CatLogo
import com.coroutines.thisdayinhistory.ui.viewmodels.ISettingsViewModel

@Composable
fun WidgetScreen(
    modifier: Modifier = Modifier,
    viewModel: ISettingsViewModel
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        val context = LocalContext.current
        val themeState by viewModel.appConfigurationState.collectAsState()

        CatLogo(themeState)
        Spacer(modifier = Modifier.height(30.dp))
        Text("Widget for Home Screen", color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.height(30.dp))
        val scope = rememberCoroutineScope()

        val appWidgetManager = AppWidgetManager.getInstance(context)

        if (appWidgetManager.isRequestPinAppWidgetSupported) {
            val appProvider = ComponentName(context, DayInHistoryGlanceAppWidgetReceiver::class.java)

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                WidgetRow(
                    preview = painterResource(R.drawable.cia_seal),
                    description = stringResource(R.string.app_name),
                    onClick = {
                        appWidgetManager.requestPinAppWidget(
                            appProvider,
                            null,
                            null
                        )
                    },
                )


            }
            // Create the PendingIntent object only if your app needs to be notified
            // when the user chooses to pin the widget. Note that if the pinning
            // operation fails, your app isn't notified. This callback receives the ID
            // of the newly pinned widget (EXTRA_APPWIDGET_ID).
          /*  val successCallback = PendingIntent.getBroadcast(
                /* context = */ context,
                /* requestCode = */ 0,
                /* intent = */ Intent(...),
            /* flags = */ PendingIntent.FLAG_UPDATE_CURRENT)*/

          //  appWidgetManager.requestPinAppWidget(myProvider, null, null)
        }

    }
}
@Composable
fun WidgetRow(preview: Painter, description: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = preview,
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            textAlign = TextAlign.Center,
            text = description,
            style = MaterialTheme.typography.bodyMedium
                .copy(color = LocalContentColor.current.copy(alpha = 0.6f)),
        )
    }
}
