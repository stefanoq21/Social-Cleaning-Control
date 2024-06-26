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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
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
    onNavigationEvent: (NavigationEvent) -> Unit,
    onEvent: (PermissionEvent) -> Unit,
    allPermissionsRevoked: Boolean,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier.padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary,
            text = stringResource(R.string.permission_screen_title),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = stringResource(R.string.permission_screen_text),
            modifier = Modifier.padding(12.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(16.dp))


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


@PreviewLightDark
@PreviewDynamicColors
@PreviewScreenSizes
@Composable
private fun WaitingStatePreview() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            PermissionScreen(
                onNavigationEvent = {},
                onEvent = {},
                allPermissionsRevoked = true,
                onClick = {}
            )
        }
    }

}