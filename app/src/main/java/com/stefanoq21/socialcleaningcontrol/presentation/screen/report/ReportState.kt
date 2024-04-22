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

@file:OptIn(ExperimentalFoundationApi::class)

package com.stefanoq21.socialcleaningcontrol.presentation.screen.report

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.google.android.gms.maps.model.LatLng
import com.stefanoq21.socialcleaningcontrol.presentation.screen.model.UIStateForScreen


data class ReportState(
    val uiState: UIStateForScreen = UIStateForScreen.OnLoadingState,
    val latLng: LatLng? = null,
    val name: String = "",
    val surname: String = "",
    val address: String = "",
    val description: TextFieldState = TextFieldState(),
)