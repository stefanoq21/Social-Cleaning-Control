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

package com.stefanoq21.socialcleaningcontrol.presentation.screen.report

import android.content.Context
import android.location.Geocoder
import android.net.Uri
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.maps.model.LatLng

sealed interface ReportEvent {
    data class OnScreenLaunch(val latLng: LatLng, val geocoder: Geocoder) : ReportEvent
    data class OnAddUris(val uris: List<Uri>) : ReportEvent
    data class OnRemoveUri(val uri: Uri) : ReportEvent
    data class OnSendReport(val ctx: Context, val onFail: () -> Unit, val onSuccess: () -> Unit) :
        ReportEvent

    data object OnSaveReport : ReportEvent

}