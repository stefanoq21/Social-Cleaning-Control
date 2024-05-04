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

package com.stefanoq21.socialcleaningcontrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.stefanoq21.socialcleaningcontrol.presentation.component.navigation.MainUIWithNavigation
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationEvent
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationViewModel
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.ScreenSerializer.fromRoute
import com.stefanoq21.socialcleaningcontrol.presentation.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val windowSize = calculateWindowSizeClass(this)
                val navigationViewModel = koinViewModel<NavigationViewModel>()

                val navController = rememberNavController()
                val backstackEntry = navController.currentBackStackEntryAsState()

                navigationViewModel.onEvent(
                    NavigationEvent.OnSetContent(
                        activityNavController = navController,
                        currentScreen = backstackEntry.value.fromRoute()
                    ) { onBackPressedDispatcher.onBackPressed() })

                ChangeSystemBarsTheme(
                    lightTheme = !isSystemInDarkTheme(),
                    shouldShowBottomBar = navigationViewModel.shouldShowBottomBar,
                    windowSize = windowSize
                )

                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    MainUIWithNavigation(
                        windowSize = windowSize,
                        setOrientationForScreen = ::setOrientationForScreen,
                    )
                }
            }
        }
    }


    /**
     * ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
     * ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
     * ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
     * */
    private fun setOrientationForScreen(orientation: Int) {
        requestedOrientation = orientation
    }

    @Composable
    private fun ChangeSystemBarsTheme(
        lightTheme: Boolean,
        shouldShowBottomBar: Boolean,
        windowSize: WindowSizeClass,
    ) {
        val statusBarColor = MaterialTheme.colorScheme.background.toArgb()
        val navigationBarColor =
            if (shouldShowBottomBar && windowSize.widthSizeClass == WindowWidthSizeClass.Compact)
                (MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)).toArgb()
            else
                MaterialTheme.colorScheme.background.toArgb()


        LaunchedEffect(lightTheme, shouldShowBottomBar) {
            if (lightTheme) {

                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.light(
                        statusBarColor, statusBarColor,
                    ),
                    navigationBarStyle = SystemBarStyle.light(
                        navigationBarColor, navigationBarColor,
                    ),
                )
            } else {

                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.dark(
                        statusBarColor,
                    ),
                    navigationBarStyle = SystemBarStyle.dark(
                        navigationBarColor,
                    ),
                )
            }
        }
    }

}
