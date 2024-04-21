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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

class NavigationViewModel : ViewModel() {
    private lateinit var onBackPressed: () -> Unit
    var currentScreen by mutableStateOf(ScreenEnum.Map)
        private set
    lateinit var activityNavController: NavHostController
        private set

    val snackBarHostState = SnackbarHostState()

    val bottomNavigationItems = listOf(
        BottomBarElement.Map,
        BottomBarElement.Profile,
    )

    val shouldShowBottomBar by derivedStateOf {
        bottomNavigationItems.any { it.screen == currentScreen }
    }

    private fun showSnackBar(
        message: String,
        actionLabel: String? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration = SnackbarDuration.Short,
        onDismiss: () -> Unit = {},
        onPerformAction: () -> Unit = {}
    ) {
        viewModelScope.launch {
            val snackBarResult = snackBarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                withDismissAction = withDismissAction,
                duration = duration
            )
            when (snackBarResult) {
                SnackbarResult.Dismissed -> onDismiss()
                SnackbarResult.ActionPerformed -> onPerformAction()
            }
        }
    }

    private fun navigateSingleTop(screen: ScreenEnum) {
        activityNavController.navigate(screen.name) {
            popUpTo(ScreenEnum.Map.name)
            launchSingleTop = true
        }
    }

    private fun navigateToHome() {
        activityNavController.navigate(ScreenEnum.Map.name) {
            popUpTo(ScreenEnum.Map.name) { inclusive = true }
        }
    }


    fun onEvent(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.OnSetContent -> {
                activityNavController = event.activityNavController
                currentScreen = event.currentScreen
                onBackPressed = event.onBackPressed
            }

            is NavigationEvent.OnBack -> onBackPressed()

            is NavigationEvent.OnNavigateToScreen -> {
                activityNavController.navigate(event.screen.name)
            }

            is NavigationEvent.OnShowSnackBar -> {
                showSnackBar(
                    message = event.message,
                    actionLabel = event.actionLabel,
                    withDismissAction = event.withDismissAction,
                    duration = event.duration,
                    onDismiss = event.onDismiss,
                    onPerformAction = event.onPerformAction
                )
            }

            is NavigationEvent.OnNavigateBottomBar -> {
                navigateSingleTop(event.screen)
            }

            is NavigationEvent.OnNavigateSingleTop -> {
                navigateSingleTop(event.screen)
            }

            is NavigationEvent.OnNavigateToHome -> {
                navigateToHome()
            }


        }
    }


}