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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun MainUIWithNavigation(
    windowSize: WindowSizeClass,
    setOrientationForScreen: (Int) -> Unit,
    navigationViewModel: NavigationViewModel = koinViewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity),

    ) {

    Scaffold(
        bottomBar = {
            if (windowSize.widthSizeClass == WindowWidthSizeClass.Compact) {
                if (navigationViewModel.shouldShowBottomBar) {
                    MBottomBar()

                }
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = navigationViewModel.snackBarHostState)
        }
    ) { innerPadding ->


        if (windowSize.widthSizeClass > WindowWidthSizeClass.Compact) {
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (navigationViewModel.shouldShowBottomBar) {
                    MRailBar()
                }
                MainNavHost(
                    modifier = Modifier,
                    windowSize = windowSize,
                    setOrientationForScreen = setOrientationForScreen
                )
            }
        } else {


            MainNavHost(
                modifier = Modifier.padding(innerPadding),
                windowSize = windowSize,
                setOrientationForScreen = setOrientationForScreen
            )
        }

    }

}
