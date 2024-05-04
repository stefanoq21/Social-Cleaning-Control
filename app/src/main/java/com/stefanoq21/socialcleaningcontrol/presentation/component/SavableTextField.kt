/*
 *  Copyright 2024 stefanoq21
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.stefanoq21.socialcleaningcontrol.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
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
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import com.stefanoq21.socialcleaningcontrol.domain.utils.FirstCharCapsTransformation

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
        BasicTextField(
            modifier = Modifier
                .weight(1f)
                .padding(6.dp),
            state = state,
            lineLimits = TextFieldLineLimits.SingleLine,
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
            keyboardOptions = KeyboardOptions(imeAction = if (enabled) ImeAction.Done else ImeAction.Go),
            inputTransformation = FirstCharCapsTransformation(Locale.current),
            onKeyboardAction = {
                if (enabled) {
                    focusManager.clearFocus()
                    onClickSave()
                } else {
                    focusManager.clearFocus()
                }
            }
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