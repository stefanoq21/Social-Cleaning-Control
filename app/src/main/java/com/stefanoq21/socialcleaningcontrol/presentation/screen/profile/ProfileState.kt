@file:OptIn(ExperimentalFoundationApi::class)

package com.stefanoq21.socialcleaningcontrol.presentation.screen.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.stefanoq21.socialcleaningcontrol.presentation.screen.model.UIStateForScreen


data class ProfileState(
    var uiState: UIStateForScreen = UIStateForScreen.OnLoadingState,
    var nickname: String = "",
    var name: TextFieldState = TextFieldState(),
    var surname: TextFieldState = TextFieldState(),
    var lastNameSaved: String = "",
    var lastSurnameSaved: String = "",
    var points: Int = 0,
    var uncleanedLocations: Int = 0,
    var cleanedLocations: Int = 0,
    )