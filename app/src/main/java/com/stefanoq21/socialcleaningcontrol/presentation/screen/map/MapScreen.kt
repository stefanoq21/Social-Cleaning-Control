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
@file:OptIn(ExperimentalPermissionsApi::class, MapsComposeExperimentalApi::class)

package com.stefanoq21.socialcleaningcontrol.presentation.screen.map

import android.Manifest
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.outlined.CleaningServices
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraMoveStartedReason
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.stefanoq21.socialcleaningcontrol.R
import com.stefanoq21.socialcleaningcontrol.presentation.component.Loader
import com.stefanoq21.socialcleaningcontrol.presentation.component.map.LocationUpdatesEffect
import com.stefanoq21.socialcleaningcontrol.presentation.component.map.LocationsCluster
import com.stefanoq21.socialcleaningcontrol.presentation.component.map.MapLayersButtonWithMenu
import com.stefanoq21.socialcleaningcontrol.presentation.component.map.PointsEarnedDialog
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationEvent
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationViewModel
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.ScreenEnum
import com.stefanoq21.socialcleaningcontrol.presentation.screen.model.UIStateForScreen
import com.stefanoq21.socialcleaningcontrol.presentation.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import java.util.concurrent.TimeUnit


@Composable
fun MapInitScreen(
    widthSizeClass: WindowWidthSizeClass,
    mapViewModel: MapViewModel = koinViewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity),
    navigationViewModel: NavigationViewModel = koinViewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity),
) {
    val state by mapViewModel.state.collectAsStateWithLifecycle()

    val multiplePermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    LaunchedEffect(key1 = Unit, block = {
        mapViewModel.onEvent(MapEvent.OnScreenLaunch(
            multiplePermissionState = multiplePermissionState,
            onRestart = {
                navigationViewModel.onEvent(
                    NavigationEvent.OnNavigateSingleTop(ScreenEnum.Welcome)
                )
            },
            onPermissionMissed = {
                navigationViewModel.onEvent(
                    NavigationEvent.OnNavigateSingleTop(ScreenEnum.Permission)
                )
            }
        ))
    })

    MapScreen(
        onNavigationEvent = navigationViewModel::onEvent,
        onEvent = mapViewModel::onEvent,
        state = state,
        widthSizeClass = widthSizeClass,
        haveLocationPermissions = multiplePermissionState.allPermissionsGranted
    )


}


@Composable
fun MapScreen(
    widthSizeClass: WindowWidthSizeClass,
    onNavigationEvent: (NavigationEvent) -> Unit,
    onEvent: (MapEvent) -> Unit,
    state: MapState,
    haveLocationPermissions: Boolean
) {
    Column(Modifier.fillMaxSize()) {

        when (state.uiState) {
            UIStateForScreen.WaitingState -> {

                val zoom = 20f

                val locationRequest by remember {
                    mutableStateOf(
                        LocationRequest.Builder(
                            Priority.PRIORITY_HIGH_ACCURACY,
                            TimeUnit.SECONDS.toMillis(3)
                        ).build()
                    )
                }

                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(state.currentLocation, zoom)
                }

                var cameraMovedByUser by remember {
                    mutableStateOf(false)
                }

                LaunchedEffect(key1 = cameraPositionState.position) {
                    if (cameraPositionState.isMoving
                        && cameraPositionState.cameraMoveStartedReason == CameraMoveStartedReason.GESTURE
                    ) {
                        cameraMovedByUser = true
                    }
                }

                var mapType by remember {
                    mutableStateOf(MapType.NORMAL)
                }

                LocationUpdatesEffect(
                    locationRequest,
                    haveLocationPermissions
                ) { result ->
                    for (currentLocation in result.locations) {
                        val locationUpdates =
                            LatLng(currentLocation.latitude, currentLocation.longitude)

                        onEvent(
                            MapEvent.OnCurrentLocationChange(
                                locationUpdates
                            )
                        )

                        if (!cameraMovedByUser) {
                            cameraPositionState.position =
                                CameraPosition.fromLatLngZoom(locationUpdates, zoom)
                        }
                    }
                }

                if (state.currentLocation.latitude != 0.0 || state.currentLocation.longitude != 0.0) {
                    Box(Modifier.fillMaxSize()) {

                        GoogleMap(
                            cameraPositionState = cameraPositionState,
                            properties = MapProperties(mapType = mapType),
                            uiSettings = MapUiSettings(
                                mapToolbarEnabled = false,
                                zoomControlsEnabled = false
                            )
                        ) {

                            LocationsCluster(state.locations)

                            Marker(
                                state = MarkerState(
                                    position = state.currentLocation
                                ),
                                zIndex = 0.1f
                            )

                        }

                        Box(
                            modifier = Modifier
                                .padding(6.dp)
                                .align(Alignment.TopEnd),
                        ) {

                            MapLayersButtonWithMenu(mapType) {
                                mapType = it
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                                .align(Alignment.BottomStart),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {


                            AnimatedContent(
                                modifier = Modifier,
                                targetState = cameraMovedByUser,
                                transitionSpec = {
                                    fadeIn(animationSpec = tween(2000)) togetherWith
                                            fadeOut(animationSpec = tween(2000))
                                }, label = ""
                            ) {
                                if (it) {
                                    FilledTonalButton(
                                        onClick = {
                                            cameraMovedByUser = false
                                            cameraPositionState.position =
                                                CameraPosition.fromLatLngZoom(
                                                    state.currentLocation,
                                                    zoom
                                                )
                                        }) {
                                        Text(text = stringResource(R.string.map_reposition_camera))
                                    }
                                }
                            }

                            AnimatedContent(
                                modifier = Modifier,
                                targetState = state.locationItemInTheArea == null || !state.locationItemInTheArea.cleaned,
                                transitionSpec = {
                                    fadeIn(animationSpec = tween(2000)) togetherWith
                                            fadeOut(animationSpec = tween(2000))
                                }, label = ""
                            ) {
                                if (it) {
                                    FloatingActionButton(
                                        modifier = Modifier,
                                        onClick = {
                                            if (state.locationItemInTheArea != null) {
                                                onEvent(MapEvent.OnMarkCleanedLocation)
                                            } else {
                                                onNavigationEvent(
                                                    NavigationEvent.OnNavigateToReport(state.currentLocation)
                                                )
                                            }
                                        }) {
                                        Icon(
                                            modifier = Modifier,
                                            imageVector =
                                            if (state.locationItemInTheArea != null)
                                                Icons.Outlined.CleaningServices
                                            else
                                                Icons.Default.DeleteForever,
                                            contentDescription = null,
                                        )
                                    }
                                }
                            }

                        }


                    }

                    if (state.showPointsDialog) {
                        PointsEarnedDialog(
                            pointsEarned = state.pointsForPointsDialog,
                            isFirstTime = state.isFirstTimeEarnPoints,
                            onDismiss = {
                                onEvent(MapEvent.OnResetPointsDialog)
                            }
                        )
                    }


                } else {
                    Box(Modifier.fillMaxSize()) {
                        Loader(Modifier.align(Alignment.Center))
                    }
                }
            }

            UIStateForScreen.OnLoadingState -> {
                Box(Modifier.fillMaxSize()) {
                    Loader(Modifier.align(Alignment.Center))
                }
            }

            UIStateForScreen.OnNetworkErrorState, UIStateForScreen.OnGeneralErrorState -> {

            }
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
            Surface(color = MaterialTheme.colorScheme.background) {
                MapScreen(
                    widthSizeClass = WindowSizeClass.calculateFromSize(
                        DpSize(
                            maxWidth,
                            maxHeight
                        )
                    ).widthSizeClass,
                    onNavigationEvent = {},
                    onEvent = {},
                    state = MapState(
                        uiState = UIStateForScreen.WaitingState,
                        currentLocation = LatLng(1.1, 1.1)
                    ),
                    haveLocationPermissions = true
                )
            }
        }
    }

}



