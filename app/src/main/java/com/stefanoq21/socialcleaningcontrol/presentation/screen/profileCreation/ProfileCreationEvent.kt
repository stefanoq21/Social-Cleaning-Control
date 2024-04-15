package com.stefanoq21.socialcleaningcontrol.presentation.screen.profileCreation

sealed interface ProfileCreationEvent {
   // data object OnScreenLaunch : ProfileCreationEvent
    data class OnNicknameChange(val value: String) : ProfileCreationEvent
    data class OnNameChange(val value: String) : ProfileCreationEvent
    data class OnSurnameChange(val value: String) : ProfileCreationEvent
}