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
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.window.core.layout.WindowWidthSizeClass
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationEvent
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationViewModel
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.ScreenSerializer.instanceOf
import org.koin.androidx.compose.koinViewModel


@Composable
fun MainUIWithNavigation(
    setOrientationForScreen: (Int) -> Unit,
    navigationViewModel: NavigationViewModel = koinViewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity),

    ) {

    val adaptiveInfo = currentWindowAdaptiveInfo()
    val customNavSuiteType = with(adaptiveInfo) {
        if (!navigationViewModel.shouldShowBottomBar) {
            NavigationSuiteType.None
        } else {
            if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) {
                NavigationSuiteType.NavigationRail
            } else {
                NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
            }
        }
    }

    NavigationSuiteScaffold(
        modifier = Modifier,
        layoutType = customNavSuiteType,
        navigationSuiteItems = {
            navigationViewModel.bottomNavigationItems.forEach { bottomBarElement ->

                val selected =
                    navigationViewModel.currentScreen.instanceOf(bottomBarElement.screen::class)

                item(
                    icon =
                    if (selected)
                        bottomBarElement.iconSelected
                    else
                        bottomBarElement.icon,
                    selected = selected,
                    alwaysShowLabel = true,
                    label = {
                        Text(
                            text = stringResource(id = bottomBarElement.title),
                            style = MaterialTheme.typography.labelMedium.copy(
                                textAlign = TextAlign.Center,
                                //fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                            ),
                            maxLines = 1
                        )
                    },
                    onClick = {
                        if (!selected) {
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

    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = navigationViewModel.snackBarHostState)
            }
        ) { innerPadding ->
            MainNavHost(
                modifier = Modifier.padding(innerPadding),
                setOrientationForScreen = setOrientationForScreen
            )
        }
    }
}