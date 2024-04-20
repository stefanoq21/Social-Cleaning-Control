@file:OptIn(ExperimentalPermissionsApi::class)

package com.stefanoq21.socialcleaningcontrol.presentation.screen.profile

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.stefanoq21.socialcleaningcontrol.presentation.screen.profileCreation.ProfileCreationEvent

sealed interface ProfileEvent {


    data object OnScreenLaunch : ProfileEvent
    data object OnSetNameValue: ProfileEvent
    data object OnSetSurnameValue: ProfileEvent

}