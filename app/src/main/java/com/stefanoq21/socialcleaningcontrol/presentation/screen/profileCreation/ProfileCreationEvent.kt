package com.stefanoq21.socialcleaningcontrol.presentation.screen.profileCreation

sealed interface ProfileCreationEvent {
    // data object OnScreenLaunch : ProfileCreationEvent
    data object OnSetProfileValues: ProfileCreationEvent
}