package com.coroutines.thisdayinhistory.ui.components


import androidx.annotation.StringRes
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.coroutines.thisdayinhistory.components.ADJUSTED_COLOR_LIGHTER_FACTOR
import com.coroutines.thisdayinhistory.ui.utils.lighter
import com.coroutines.thisdayinhistory.ui.viewmodels.IHistoryViewModel
import kotlinx.datetime.toInstant
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.datetime.toKotlinTimeZone
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDatePickerDialog(
    viewModel: IHistoryViewModel,
    @StringRes resIdCancel: Int,
    onDateSelected: (LocalDateTime?) -> Unit,
    onDismiss: () -> Unit
) {
   // val calendar = (viewModel as HistoryViewModel).historyCalendar as HistoryCalendar

    val time = kotlinx.datetime.Clock.System.now().toEpochMilliseconds()

        //calendar.currentLocalDateTime.toKotlinLocalDateTime().toInstant(
       // ZoneId.systemDefault().toKotlinTimeZone()).toEpochMilliseconds()

    val datePickerState = remember { DatePickerState(
        locale = androidx.compose.material3.CalendarLocale.FRANCE,
        time)
    }

    val selectedDate = datePickerState.selectedDateMillis?.let {
        Instant.ofEpochMilli(it).atOffset(ZoneOffset.UTC)
    }

    DatePickerDialog(
        colors = DatePickerDefaults.colors().copy(
            containerColor = MaterialTheme.colorScheme.background.lighter(
                ADJUSTED_COLOR_LIGHTER_FACTOR),
        ),
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onDateSelected(selectedDate?.toLocalDateTime())
                onDismiss()
            }

            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = stringResource(id = resIdCancel))
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}