@file:OptIn(ExperimentalPermissionsApi::class)

package com.stefanoq21.socialcleaningcontrol.presentation.screen.map

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

sealed interface MapEvent {
    data class OnScreenLaunch(
        val multiplePermissionState: MultiplePermissionsState,
        val onRestart:()->Unit,
        val onPermissionMissed:()->Unit,
    ) : MapEvent
}