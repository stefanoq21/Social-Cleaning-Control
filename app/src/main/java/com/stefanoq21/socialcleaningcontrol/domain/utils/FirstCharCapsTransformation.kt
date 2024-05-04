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


package com.stefanoq21.socialcleaningcontrol.domain.utils

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.intl.Locale

data class FirstCharCapsTransformation(private val locale: Locale) : InputTransformation {
    override val keyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.Words
    )

    override fun TextFieldBuffer.transformInput() {
        if (length > 0) {
            replace(
                0,
                1,
                asCharSequence().first().toString().uppercase()
            )
        }
    }

    override fun toString(): String = "InputTransformation.FirstCharCaps(locale=$locale)"

}