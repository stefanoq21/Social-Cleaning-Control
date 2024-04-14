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

    private fun navigateBottomBar(screen: ScreenEnum) {
        activityNavController.navigate(screen.name) {
            popUpTo(ScreenEnum.Map.name)
            launchSingleTop = true
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
                navigateBottomBar(event.screen)
            }


        }
    }


}