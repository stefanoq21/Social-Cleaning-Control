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

@file:OptIn(ExperimentalPermissionsApi::class)

package com.stefanoq21.socialcleaningcontrol.presentation.screen.map

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapType

sealed interface MapEvent {
    data class OnScreenLaunch(
        val multiplePermissionState: MultiplePermissionsState,
        val onRestart: () -> Unit,
        val onPermissionMissed: () -> Unit,
    ) : MapEvent

    data class OnCurrentLocationChange(val newLocation: LatLng) : MapEvent
    data object OnMarkCleanedLocation : MapEvent
    data object OnResetPointsDialog : MapEvent
    data class OnSetMapType(val mapType: MapType) : MapEvent
}