@file:OptIn(ExperimentalFoundationApi::class)

package com.stefanoq21.socialcleaningcontrol.presentation.screen.profileCreation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState


data class ProfileCreationState(
    var nickname: TextFieldState = TextFieldState(),
    var name: TextFieldState = TextFieldState(),
    var surname: TextFieldState = TextFieldState(),
)