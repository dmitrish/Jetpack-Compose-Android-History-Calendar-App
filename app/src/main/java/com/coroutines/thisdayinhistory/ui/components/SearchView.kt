package com.coroutines.thisdayinhistory.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.coroutines.thisdayinhistory.ui.utils.darker
import com.coroutines.thisdayinhistory.ui.utils.lighter
import com.coroutines.thisdayinhistory.ui.viewmodels.IHistoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(state: MutableState<TextFieldValue>, viewModel: IHistoryViewModel) {
    val interactionSource = remember { MutableInteractionSource() }


    BasicTextField(
        value = state.value,
        onValueChange = { value -> state.value = value; viewModel.search(state.value.text) },
        modifier = Modifier.padding(0.dp).width(260.dp),
        textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { viewModel.search(state.value.text)}),
        // internal implementation of the BasicTextField will dispatch focus events
        interactionSource = interactionSource,
        enabled = true,
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
    ) {
        TextFieldDefaults.DecorationBox(
            value = state.value.text.ifEmpty { viewModel.filterKey },
            visualTransformation = VisualTransformation.None,
            innerTextField = it,
            trailingIcon = {
                if (state.value != TextFieldValue("")) {
                    IconButton(
                        onClick = {
                            state.value =
                                TextFieldValue("")
                            viewModel.search("")
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "",
                        )
                    }
                } else {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "",
                    )
                }
            },
            // same interaction source as the one passed to BasicTextField to read focus state
            // for text field styling
            interactionSource = interactionSource,
            enabled = true,
            singleLine = true,
            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                top = 0.dp, bottom = 0.dp
            ),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.background.lighter(0.2f),
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            container = { Box(Modifier.background(MaterialTheme.colorScheme.background.darker(0.07f), RoundedCornerShape(12.dp) ))}

            // update border thickness and shape
            /*border = {
                TextFieldDefaults.BorderBox(
                    enabled = true,
                    isError = false,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.onBackground.lighter(0.7f),
                        unfocusedBorderColor = MaterialTheme.colors.onBackground.lighter(0.3f)
                    ),
                    // colors = TextFieldDefaults.textFieldColors(textColor = BabyPowder),
                    interactionSource = interactionSource,
                    shape = CircleShape,
                    unfocusedBorderThickness = 0.dp,
                    focusedBorderThickness = 1.dp
                )
            }*/
        )
    }
}
