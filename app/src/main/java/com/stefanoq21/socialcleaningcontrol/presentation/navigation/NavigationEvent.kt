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

package com.stefanoq21.socialcleaningcontrol.presentation.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.LatLng

sealed interface NavigationEvent {
    data class OnSetContent(
        val activityNavController: NavHostController,
        val currentScreen: Screen,
        val onBackPressed: () -> Unit
    ) : NavigationEvent

    data object OnBack : NavigationEvent
    data class OnNavigateToScreen(val screen: Screen) : NavigationEvent

    data class OnNavigateSingleTop(val screen: Screen) : NavigationEvent
    data class OnNavigateToReport(val latLng: LatLng) : NavigationEvent

    data object OnNavigateToHome : NavigationEvent

    data class OnShowSnackBar(
        val message: String,
        val actionLabel: String? = null,
        val withDismissAction: Boolean = false,
        val duration: SnackbarDuration = SnackbarDuration.Short,
        val onDismiss: () -> Unit = {},
        val onPerformAction: () -> Unit = {}
    ) : NavigationEvent
}