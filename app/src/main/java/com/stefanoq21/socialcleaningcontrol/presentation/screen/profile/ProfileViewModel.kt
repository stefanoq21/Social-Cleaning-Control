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