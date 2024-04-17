@file:OptIn(ExperimentalPermissionsApi::class)

package com.stefanoq21.socialcleaningcontrol.presentation.screen.profile

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

sealed interface ProfileEvent {
    data object OnScreenLaunch : ProfileEvent
}