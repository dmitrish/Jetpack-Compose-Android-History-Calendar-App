package com.coroutines.thisdayinhistory.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.coroutines.thisdayinhistory.ui.utils.darker
import com.coroutines.thisdayinhistory.uimodels.ShareWith

@Composable
fun PopupShareMenu(
    onClickCallback: (Int) -> Unit,
    showMenu: Boolean,
    onDismiss: () -> Unit
) {
    val menuItems = ShareWith.entries
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { onDismiss() },
        Modifier.background(MaterialTheme.colorScheme.background.darker(0.1f)),
    ) {
       /* menuItems.forEachIndexed { index, item ->
            DropdownMenuItem(onClick = {
                onDismiss()
                onClickCallback(index)
            }) {
                Row {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        item.displayName,
                        tint = MaterialTheme.colorScheme.onBackground.darker(0.1f)
                    )
                    Text(text = item.displayName,
                        Modifier.padding(start = 18.dp, end = 15.dp)
                    )
                }
            }
        }*/
    }
}