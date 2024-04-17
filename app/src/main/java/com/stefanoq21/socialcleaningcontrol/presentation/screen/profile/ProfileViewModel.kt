@file:OptIn(ExperimentalPermissionsApi::class, ExperimentalFoundationApi::class)

package com.stefanoq21.socialcleaningcontrol.presentation.screen.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.stefanoq21.socialcleaningcontrol.data.preference.PrefsDataStore
import com.stefanoq21.socialcleaningcontrol.presentation.screen.model.UIStateForScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ProfileViewModel(
    private val prefsDataStore: PrefsDataStore,
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()


    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnScreenLaunch -> {
                performOnScreenStart()
            }
        }
    }

    private fun performOnScreenStart() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                ProfileState(uiState = UIStateForScreen.OnLoadingState)
            }




            _state.update { state ->
                state.copy(
                    uiState = UIStateForScreen.WaitingState,
                )
            }


        }
    }
}