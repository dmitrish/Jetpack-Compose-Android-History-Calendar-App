package com.coroutines.thisdayinhistory.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.coroutines.thisdayinhistory.ui.theme.AppTypography

@Composable
fun ChoiceChipContent(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = when {
            selected -> MaterialTheme.colorScheme.background
            else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.0f)
        },

        contentColor = when {
            selected -> Color.Black
            else -> MetallicSilver
        },
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Row(Modifier.height(60.dp)) {
            Text(
                text = text,
                color = GrayishBlack,// if (selected) MaterialTheme.colorScheme.onSecondary else GrayishBlack,
                style = AppTypography.bodyMedium,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .align(Alignment.Bottom)

            )
        }
    }
}

@Preview(showBackground = true)
@PreviewLightDark
@Composable
fun PreviewChoiceChipContentSelected(){
    ChoiceChipContent(text = "this is text", selected = true)
}

@Preview(showBackground = true)
@PreviewLightDark
@Composable
fun PreviewChoiceChipContentUnSelected(){
    ChoiceChipContent(text = "this is text", selected = false)
}
