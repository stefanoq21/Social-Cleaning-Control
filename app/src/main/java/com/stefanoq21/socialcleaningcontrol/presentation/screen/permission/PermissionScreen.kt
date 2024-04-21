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

@file:OptIn(ExperimentalPermissionsApi::class)

package com.stefanoq21.socialcleaningcontrol.presentation.screen.permission

import android.Manifest
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.stefanoq21.socialcleaningcontrol.R
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationEvent
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationViewModel
import com.stefanoq21.socialcleaningcontrol.presentation.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun PermissionInitScreen(
    widthSizeClass: WindowWidthSizeClass,
    navigationViewModel: NavigationViewModel = koinViewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity),
    permissionViewModel: PermissionViewModel = koinViewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity),
) {
    val multiplePermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    val allPermissionsRevoked =
        multiplePermissionState.permissions.size ==
                multiplePermissionState.revokedPermissions.size

    LaunchedEffect(key1 = multiplePermissionState.allPermissionsGranted) {
        if (multiplePermissionState.allPermissionsGranted) {
            navigationViewModel.onEvent(
                NavigationEvent.OnNavigateToHome
            )
        }
    }





    PermissionScreen(
        widthSizeClass = widthSizeClass,
        onNavigationEvent = navigationViewModel::onEvent,
        onEvent = permissionViewModel::onEvent,
        allPermissionsRevoked = allPermissionsRevoked,
        onClick = {
            multiplePermissionState.launchMultiplePermissionRequest()
        }
    )
}


@Composable
fun PermissionScreen(
    widthSizeClass: WindowWidthSizeClass,
    onNavigationEvent: (NavigationEvent) -> Unit,
    onEvent: (PermissionEvent) -> Unit,
    allPermissionsRevoked: Boolean,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier.padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Text(
            text = stringResource(R.string.permission_screen_title),
            modifier = Modifier.padding(12.dp),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.permission_screen_text),
            modifier = Modifier.padding(12.dp),
            textAlign = TextAlign.Center
        )



        Button(
            onClick = onClick
        ) {
            Text(
                text = if (!allPermissionsRevoked) {
                    stringResource(R.string.permission_screen_button_precise)
                } else {
                    stringResource(R.string.permission_screen_button_enable)
                }
            )

        }


    }

}


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(device = Devices.PHONE)
@Preview(device = Devices.FOLDABLE)
@Preview(device = Devices.TABLET)
@Preview(device = Devices.DESKTOP)
@Composable
private fun WaitingStatePreview() {
    AppTheme {
        BoxWithConstraints {
            Surface(
                modifier=Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background) {
                PermissionScreen(
                    widthSizeClass = WindowSizeClass.calculateFromSize(
                        DpSize(
                            maxWidth,
                            maxHeight
                        )
                    ).widthSizeClass,
                    onNavigationEvent = {},
                    onEvent = {},
                    allPermissionsRevoked = true,
                    onClick = {}
                )
            }
        }
    }

}