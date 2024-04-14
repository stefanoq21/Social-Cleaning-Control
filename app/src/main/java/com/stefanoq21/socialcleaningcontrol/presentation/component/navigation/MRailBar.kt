package com.stefanoq21.socialcleaningcontrol.presentation.component.navigation

import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationEvent
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MRailBar(
    navigationViewModel: NavigationViewModel = koinViewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity),
) {

    NavigationRail(containerColor = MaterialTheme.colorScheme.surface) {
        navigationViewModel.bottomNavigationItems.forEach { bottomBarElement ->
            NavigationRailItem(
                icon = bottomBarElement.icon,
                selected = navigationViewModel.currentScreen == bottomBarElement.screen,
                alwaysShowLabel = true,
                label = {
                    Text(
                        text = stringResource(id = bottomBarElement.title),
                        style = MaterialTheme.typography.labelMedium.copy(
                            textAlign = TextAlign.Center
                        ),
                        maxLines = 1
                    )
                },
                onClick = {
                    if (navigationViewModel.currentScreen != bottomBarElement.screen) {
                        navigationViewModel.onEvent(
                            NavigationEvent.OnNavigateBottomBar(
                                bottomBarElement.screen
                            )
                        )
                    }
                }
            )
        }
    }
}

