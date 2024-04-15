package com.stefanoq21.socialcleaningcontrol.presentation.screen.permission

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
sealed interface PermissionEvent {

    data class OnRequestPermissions(val multiplePermissionState: MultiplePermissionsState) :
        PermissionEvent

}