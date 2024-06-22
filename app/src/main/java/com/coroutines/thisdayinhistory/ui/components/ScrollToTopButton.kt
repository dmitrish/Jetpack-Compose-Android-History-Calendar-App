package com.coroutines.thisdayinhistory.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ScrollToTopButton(onClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp, end = 30.dp),
        Alignment.BottomEnd
    ) {
        Button(
            elevation = null,
            onClick = { onClick() },
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(65.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                contentColor = Color.Green
            )
        ) {
            Icon(
                Icons.Filled.KeyboardArrowUp,
                "arrow up",
                Modifier.background(MaterialTheme.colorScheme.background.copy(alpha = 0.7f))
            )
        }
    }
}