package com.stefanoq21.socialcleaningcontrol.presentation.screen.profileCreation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefanoq21.socialcleaningcontrol.data.preference.PrefsDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class ProfileCreationViewModel(
    private val prefsDataStore: PrefsDataStore,
) : ViewModel() {

    private var _nicknameFlow = prefsDataStore.getNickname()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")

    private var _nameFlow = prefsDataStore.getName()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")

    private var _surnameFlow = prefsDataStore.getSurname()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")

    private val _state = MutableStateFlow(ProfileCreationState())
    val state = combine(
        _state,
        _nicknameFlow,
        _nameFlow,
        _surnameFlow
    ) { state, nicknameFlow, nameFlow, surnameFlow ->
        state.copy(
            nickname = nicknameFlow,
            name = nameFlow,
            surname = surnameFlow
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProfileCreationState())


    fun onEvent(event: ProfileCreationEvent) {
        when (event) {

            is ProfileCreationEvent.OnNicknameChange -> setNickname(event.value)
            is ProfileCreationEvent.OnNameChange -> setName(event.value)
            is ProfileCreationEvent.OnSurnameChange -> setSurname(event.value)

        }
    }

    private fun setNickname(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            prefsDataStore.setNickname(value)
        }
    }

    private fun setName(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            prefsDataStore.setName(value)
        }
    }
    private fun setSurname(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            prefsDataStore.setSurname(value)
        }
    }
}