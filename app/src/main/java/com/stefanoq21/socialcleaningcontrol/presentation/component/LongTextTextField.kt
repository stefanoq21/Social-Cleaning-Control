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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.InputTransformation
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.maxLengthInChars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.stefanoq21.socialcleaningcontrol.R


@Composable
@OptIn(ExperimentalFoundationApi::class)
fun LongTextTextField(
    state: TextFieldState,
    maxChars: Int = 200
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .border(
                2.dp,
                MaterialTheme.colorScheme.primary,
                MaterialTheme.shapes.small
            )
            .padding(12.dp)
    ) {
        BasicTextField2(
            modifier = Modifier.fillMaxWidth(),
            state = state,
            lineLimits = TextFieldLineLimits.MultiLine(8, 8),
            inputTransformation = InputTransformation.maxLengthInChars(
                maxChars
            ),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)

        )
        Text(
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 2.dp),
            text = stringResource(
                R.string.description_count,
                state.text.length,
                maxChars
            ),
            style = MaterialTheme.typography.labelSmall
        )
    }
}
