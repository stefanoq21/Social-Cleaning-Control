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

package com.stefanoq21.socialcleaningcontrol.presentation.component.navigation

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationEvent
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MBottomBar(
    navigationViewModel: NavigationViewModel = koinViewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity),
) {

    NavigationBar(
        windowInsets = WindowInsets.systemBars
            .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom), //with this the navigationBar don't go on the keyboard
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        navigationViewModel.bottomNavigationItems.forEach { bottomBarElement ->
            NavigationBarItem(
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
                            NavigationEvent.OnNavigateSingleTop(
                                bottomBarElement.screen
                            )
                        )
                    }
                }
            )
        }
    }
}

