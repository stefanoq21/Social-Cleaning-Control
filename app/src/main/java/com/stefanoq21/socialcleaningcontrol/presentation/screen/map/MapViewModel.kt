/*
 *  Copyright 2024 stefanoq21
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

@file:OptIn(ExperimentalPermissionsApi::class)

package com.stefanoq21.socialcleaningcontrol.presentation.screen.map

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
import kotlinx.coroutines.withContext


class MapViewModel(
    private val prefsDataStore: PrefsDataStore,
) : ViewModel() {

    private val _state = MutableStateFlow(MapState())
    val state = _state.asStateFlow()


    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.OnScreenLaunch -> {
                performOnScreenStart(
                    multiplePermissionState = event.multiplePermissionState,
                    onRestart = event.onRestart,
                    onPermissionMissed = event.onPermissionMissed
                )
            }

            is MapEvent.OnCurrentLocationChange -> {
                _state.update { state ->
                    state.copy(
                        currentLocation = event.newLocation,
                    )
                }
            }
        }
    }

    private fun performOnScreenStart(
        multiplePermissionState: MultiplePermissionsState,
        onRestart: () -> Unit,
        onPermissionMissed: () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                MapState(uiState = UIStateForScreen.OnLoadingState)
            }

            val nickname = prefsDataStore.getNicknameDirect()

            withContext(Dispatchers.Main) {
                if (nickname == null) {
                    onRestart()
                } else if (!multiplePermissionState.allPermissionsGranted) {
                    onPermissionMissed()
                }
            }


            _state.update { state ->
                state.copy(
                    uiState = UIStateForScreen.WaitingState,

                    )
            }


        }
    }
}