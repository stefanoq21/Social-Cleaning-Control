package com.stefanoq21.socialcleaningcontrol.presentation.screen.profileCreation

import com.stefanoq21.socialcleaningcontrol.presentation.screen.model.UIStateForScreen


data class ProfileCreationState(
   // var uiState: UIStateForScreen = UIStateForScreen.OnLoadingState,
    var nickname: String = "",
    var name: String = "",
    var surname: String = "",
)