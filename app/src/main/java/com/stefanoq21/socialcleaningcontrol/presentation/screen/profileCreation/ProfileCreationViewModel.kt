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