package com.stefanoq21.socialcleaningcontrol.presentation.screen.profile

import androidx.activity.ComponentActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.rounded.AccountCircle
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stefanoq21.socialcleaningcontrol.presentation.component.Loader
import com.stefanoq21.socialcleaningcontrol.presentation.component.profile.CounterElement
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationEvent
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationViewModel
import com.stefanoq21.socialcleaningcontrol.presentation.screen.model.UIStateForScreen
import com.stefanoq21.socialcleaningcontrol.presentation.theme.SocialCleaningControlTheme
import org.koin.androidx.compose.koinViewModel

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
    Box(Modifier.fillMaxSize()) {
        when (state.uiState) {
            UIStateForScreen.WaitingState -> {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.size(64.dp))
                    Icon(
                        imageVector = Icons.Rounded.AccountCircle,
                        contentDescription = "avatar",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(4.dp, Color.Gray, CircleShape)
                    )
                    Text(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .testTag("ProfileNickname"),
                        style = MaterialTheme.typography.headlineMedium,
                        text = state.nickname,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.size(32.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Spacer(modifier = Modifier.size(32.dp))
                        CounterElement(
                            modifier = Modifier.weight(1f),
                            imageVector = Icons.Default.DeleteForever,
                            iconColor = Color.DarkGray,
                            circleColor = Color.Gray,
                            text = state.uncleanedLocations.toString()

                        )
                        CounterElement(
                            modifier = Modifier.weight(1f),
                            imageVector = Icons.Default.WorkspacePremium,
                            iconColor = Color.DarkGray,
                            circleColor = Color.Gray,
                            text = state.uncleanedLocations.toString()

                        )
                        CounterElement(
                            modifier = Modifier.weight(1f),
                            imageVector = Icons.Default.CleaningServices,
                            iconColor = Color.DarkGray,
                            circleColor = Color.Gray,
                            text = state.uncleanedLocations.toString()

                        )
                        Spacer(modifier = Modifier.size(32.dp))
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



