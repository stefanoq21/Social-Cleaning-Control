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

package com.stefanoq21.socialcleaningcontrol.presentation.screen.profileCreation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefanoq21.socialcleaningcontrol.data.preference.PrefsDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ProfileCreationViewModel(
    private val prefsDataStore: PrefsDataStore,
) : ViewModel() {

    /* private var _nicknameFlow = prefsDataStore.getNickname()
         .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")

     private var _nameFlow = prefsDataStore.getName()
         .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")

     private var _surnameFlow = prefsDataStore.getSurname()
         .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")*/

    private val _state = MutableStateFlow(ProfileCreationState())
    val state = _state.asStateFlow()

    /* val state =  combine(
    _state,
    _nicknameFlow,
    _nameFlow,
    _surnameFlow
) { state, nicknameFlow, nameFlow, surnameFlow ->
    state.copy(
        nickname = nicknameFlow ?: "",
        name = nameFlow,
        surname = surnameFlow
    )
}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProfileCreationState())*/


    fun onEvent(event: ProfileCreationEvent) {
        when (event) {
            is ProfileCreationEvent.OnSetProfileValues -> {
                setProfileValues()
            }
        }
    }

    private fun setProfileValues() {
        viewModelScope.launch(Dispatchers.IO) {
            prefsDataStore.setNickname(state.value.nickname.text.toString())
            prefsDataStore.setName(state.value.name.text.toString())
            prefsDataStore.setSurname(state.value.surname.text.toString())

        }
    }

}