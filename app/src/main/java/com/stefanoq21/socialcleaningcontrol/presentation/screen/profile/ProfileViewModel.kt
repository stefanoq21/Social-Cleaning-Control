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

@file:OptIn(ExperimentalFoundationApi::class)

package com.stefanoq21.socialcleaningcontrol.presentation.screen.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefanoq21.socialcleaningcontrol.data.preference.PrefsDataStore
import com.stefanoq21.socialcleaningcontrol.presentation.screen.model.UIStateForScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ProfileViewModel(
    private val prefsDataStore: PrefsDataStore,
) : ViewModel() {

    private var _nicknameFlow = prefsDataStore.getNickname()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")

    private var _nameFlow = prefsDataStore.getName()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")

    private var _surnameFlow = prefsDataStore.getSurname()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")

    private val _state = MutableStateFlow(ProfileState())

    val state = combine(
        _state,
        _nicknameFlow,
        _nameFlow,
        _surnameFlow
    ) { state, nicknameFlow, nameFlow, surnameFlow ->
        state.copy(
            nickname = nicknameFlow ?: "",
            name = TextFieldState(nameFlow),
            lastNameSaved = nameFlow,
            surname = TextFieldState(surnameFlow),
            lastSurnameSaved = surnameFlow
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProfileState())


    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnScreenLaunch -> {
                performOnScreenStart()
            }

            ProfileEvent.OnSetNameValue -> setNameValue()
            ProfileEvent.OnSetSurnameValue -> setSurnameValue()
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

    private fun setNameValue() {
        viewModelScope.launch(Dispatchers.IO) {
            prefsDataStore.setName(state.value.name.text.toString())
        }
    }

    private fun setSurnameValue() {
        viewModelScope.launch(Dispatchers.IO) {
            prefsDataStore.setSurname(state.value.surname.text.toString())
        }
    }

}