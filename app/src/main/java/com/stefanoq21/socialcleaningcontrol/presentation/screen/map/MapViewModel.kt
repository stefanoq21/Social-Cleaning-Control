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
import com.google.maps.android.ktx.utils.sphericalDistance
import com.stefanoq21.socialcleaningcontrol.data.database.DatabaseRepository
import com.stefanoq21.socialcleaningcontrol.data.database.location.LocationItem
import com.stefanoq21.socialcleaningcontrol.data.preference.PrefsDataStore
import com.stefanoq21.socialcleaningcontrol.presentation.screen.model.UIStateForScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date


class MapViewModel(
    private val prefsDataStore: PrefsDataStore,
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {

    private val _locationsFlow = databaseRepository.getUncleanedLocAndCleanedLocForLastFiveDays()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())


    private val _state = MutableStateFlow(MapState())

    val state = combine(
        _state,
        _locationsFlow,
    ) { state, locationFlow ->
        var locationItemInTheArea: LocationItem? = null
        if (state.currentLocation.latitude != 0.0 || state.currentLocation.longitude != 0.0) {
            for (location in locationFlow) {
                if (location.latLng.sphericalDistance(state.currentLocation) < 10) {
                    locationItemInTheArea = location
                    break
                }
            }
        }

        state.copy(
            locations = locationFlow,
            locationItemInTheArea = locationItemInTheArea
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MapState())


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

            MapEvent.OnClickFab -> {
                performOnClickFab()
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


    private fun performOnClickFab(
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (state.value.locationItemInTheArea != null) {
                val locationModified =
                    state.value.locationItemInTheArea!!.copy(
                        cleaned = true,
                        date = Date()
                    )
                databaseRepository.replaceLocation(locationModified)
            } else {
                databaseRepository.insertLocation(
                    date = Date(),
                    latLng = state.value.currentLocation,
                    cleaned = false,
                    description = "trash everywhere"
                )
            }
        }
    }


}