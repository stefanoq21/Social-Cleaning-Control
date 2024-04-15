package com.stefanoq21.socialcleaningcontrol.presentation.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.navigation.NavHostController

sealed interface NavigationEvent {
    data class OnSetContent(
        val activityNavController: NavHostController,
        val currentScreen: ScreenEnum,
        val onBackPressed: () -> Unit
    ) : NavigationEvent

    data object OnBack : NavigationEvent
    data class OnNavigateToScreen(val screen: ScreenEnum) : NavigationEvent
    data class OnNavigateBottomBar(val screen: ScreenEnum) : NavigationEvent

    data class OnNavigateSingleTop(val screen: ScreenEnum) : NavigationEvent

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