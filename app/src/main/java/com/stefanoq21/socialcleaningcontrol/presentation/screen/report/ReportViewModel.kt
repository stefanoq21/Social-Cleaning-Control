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

package com.stefanoq21.socialcleaningcontrol.presentation.screen.report

import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.stefanoq21.socialcleaningcontrol.data.database.DatabaseRepository
import com.stefanoq21.socialcleaningcontrol.data.preference.PrefsDataStore
import com.stefanoq21.socialcleaningcontrol.presentation.screen.model.UIStateForScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
class ReportViewModel(
    private val prefsDataStore: PrefsDataStore,
    databaseRepository: DatabaseRepository,
) : ViewModel() {

    private val _nameFlow = prefsDataStore.getName()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")

    private val _surnameFlow = prefsDataStore.getSurname()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")


    private val _state = MutableStateFlow(ReportState())

    val state = combine(
        _state,
        _nameFlow,
        _surnameFlow,
    ) { state, nameFlow, surnameFlow ->
        state.copy(
            name = nameFlow,
            surname = surnameFlow
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ReportState())


    fun onEvent(event: ReportEvent) {
        when (event) {
            is ReportEvent.OnScreenLaunch -> {
                performOnScreenStart(event.latLng, event.geocoder)
            }
        }
    }


    private fun performOnScreenStart(latLng: LatLng, geocoder: Geocoder) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                ReportState(uiState = UIStateForScreen.OnLoadingState)
            }



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(
                    latLng.latitude,
                    latLng.longitude,
                    1,
                    object : Geocoder.GeocodeListener {
                        override fun onGeocode(addresses: MutableList<Address>) {
                            _state.update { state ->
                                state.copy(
                                    uiState = UIStateForScreen.WaitingState,
                                    address = addresses[0].getAddressLine(0)
                                )
                            }

                        }

                        override fun onError(errorMessage: String?) {
                            super.onError(errorMessage)

                            _state.update { state ->
                                state.copy(
                                    uiState = UIStateForScreen.WaitingState,
                                    address = "${latLng.latitude} / ${latLng.longitude}"
                                )
                            }


                        }
                    })
            } else {
                val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                _state.update { state ->
                    state.copy(
                        uiState = UIStateForScreen.WaitingState,
                        address = addresses?.get(0)?.getAddressLine(0) ?: ""
                    )
                }
            }


        }
    }

}