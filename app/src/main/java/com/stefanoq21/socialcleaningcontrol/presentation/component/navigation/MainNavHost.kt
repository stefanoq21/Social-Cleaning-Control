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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.google.android.gms.maps.model.LatLng
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationViewModel
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.Screen
import com.stefanoq21.socialcleaningcontrol.presentation.screen.map.MapInitScreen
import com.stefanoq21.socialcleaningcontrol.presentation.screen.permission.PermissionInitScreen
import com.stefanoq21.socialcleaningcontrol.presentation.screen.profile.ProfileInitScreen
import com.stefanoq21.socialcleaningcontrol.presentation.screen.profileCreation.ProfileCreationInitScreen
import com.stefanoq21.socialcleaningcontrol.presentation.screen.report.ReportInitScreen
import com.stefanoq21.socialcleaningcontrol.presentation.screen.welcome.WelcomeInitScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navigationViewModel: NavigationViewModel = koinViewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity),
    setOrientationForScreen: (Int) -> Unit,
) {

    NavHost(
        navController = navigationViewModel.activityNavController,
        startDestination = Screen.Map,
        modifier = modifier
    ) {
        composable<Screen.Map> {
            /*  LaunchedEffect(key1 = ScreenEnum.Map.name, block = {
                  setOrientationForScreen(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR)
              })*/
            MapInitScreen()
        }

        composable<Screen.Profile> {
            ProfileInitScreen()
        }

        composable<Screen.Welcome> {
            WelcomeInitScreen()
        }

        composable<Screen.ProfileCreation> {
            ProfileCreationInitScreen()
        }

        composable<Screen.Permission> {
            PermissionInitScreen()
        }
        composable<Screen.Report> { backStackEntry ->
            val reportScreen = backStackEntry.toRoute<Screen.Report>()
            val latLng by remember {
                mutableStateOf(
                    LatLng(
                        reportScreen.latitude.toDouble(),
                        reportScreen.longitude.toDouble()
                    )
                )
            }

            ReportInitScreen(
                latLng = latLng,
            )


        }

    }
}

