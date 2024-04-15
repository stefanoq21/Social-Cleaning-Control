package com.stefanoq21.socialcleaningcontrol.presentation.screen.permission

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefanoq21.socialcleaningcontrol.data.preference.PrefsDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class PermissionViewModel() : ViewModel() {
    fun onEvent(event: PermissionEvent) {
        when (event) {
           is PermissionEvent.OnRequestPermissions -> {

            }
        }
    }


}