package com.stefanoq21.socialcleaningcontrol.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SavableTextField(
    state: TextFieldState,
    onClickSave: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Row(
        modifier
            .fillMaxWidth()
            .border(
                2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField2(
            modifier = Modifier
                .weight(1f)
                .padding(6.dp),
            state = state,
            lineLimits = TextFieldLineLimits.SingleLine,
            keyboardOptions = KeyboardOptions(imeAction = if (enabled) ImeAction.Done else ImeAction.Go),
            keyboardActions = KeyboardActions(
                onGo = { focusManager.clearFocus() },
                onDone = {
                    focusManager.clearFocus()
                    onClickSave()
                })
        )
        IconButton(
            modifier = Modifier.padding(end = 6.dp),
            enabled = enabled,
            onClick = {
                focusManager.clearFocus()
                onClickSave()
            }
        ) {
            Icon(
                modifier = Modifier,
                imageVector = Icons.Default.Save,
                contentDescription = null,
            )
        }
    }


}