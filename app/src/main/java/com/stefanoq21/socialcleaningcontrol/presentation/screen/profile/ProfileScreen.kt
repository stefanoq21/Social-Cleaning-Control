@file:OptIn(ExperimentalPermissionsApi::class)

package com.stefanoq21.socialcleaningcontrol.presentation.screen.profile

import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.stefanoq21.socialcleaningcontrol.presentation.component.Loader
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationEvent
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationViewModel
import com.stefanoq21.socialcleaningcontrol.presentation.screen.model.UIStateForScreen
import com.stefanoq21.socialcleaningcontrol.presentation.theme.SocialCleaningControlTheme
import org.koin.androidx.compose.koinViewModel
import java.util.concurrent.TimeUnit

@Composable
fun ProfileInitScreen(
    widthSizeClass: WindowWidthSizeClass,
    profileViewModel: ProfileViewModel = koinViewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity),
    navigationViewModel: NavigationViewModel = koinViewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity),
) {
    val state by profileViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit, block = {
        profileViewModel.onEvent(ProfileEvent.OnScreenLaunch)
    })

    ProfileScreen(
        onNavigationEvent = navigationViewModel::onEvent,
        onEvent = profileViewModel::onEvent,
        state = state,
        widthSizeClass = widthSizeClass,
    )


}


@Composable
fun ProfileScreen(
    widthSizeClass: WindowWidthSizeClass,
    onNavigationEvent: (NavigationEvent) -> Unit,
    onEvent: (ProfileEvent) -> Unit,
    state: ProfileState
) {
    Column(Modifier.fillMaxSize()) {

        when (state.uiState) {
            UIStateForScreen.WaitingState -> {

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
    SocialCleaningControlTheme {
        BoxWithConstraints {
            Surface(color = MaterialTheme.colorScheme.background) {
                ProfileScreen(
                    widthSizeClass = WindowSizeClass.calculateFromSize(
                        DpSize(
                            maxWidth,
                            maxHeight
                        )
                    ).widthSizeClass,
                    onNavigationEvent = {},
                    onEvent = {},
                    state = ProfileState(
                        uiState = UIStateForScreen.WaitingState,
                        nickname = "steq21"
                    )
                )
            }
        }
    }

}



