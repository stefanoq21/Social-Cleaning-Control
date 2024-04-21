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

package com.stefanoq21.socialcleaningcontrol.presentation.screen.profile

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stefanoq21.socialcleaningcontrol.R
import com.stefanoq21.socialcleaningcontrol.presentation.component.Loader
import com.stefanoq21.socialcleaningcontrol.presentation.component.SavableTextField
import com.stefanoq21.socialcleaningcontrol.presentation.component.profile.CounterElement
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationEvent
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationViewModel
import com.stefanoq21.socialcleaningcontrol.presentation.screen.model.UIStateForScreen
import com.stefanoq21.socialcleaningcontrol.presentation.theme.AppTheme
import com.stefanoq21.socialcleaningcontrol.presentation.theme.LocalExColorScheme
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


@OptIn(ExperimentalFoundationApi::class)
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
                        tint = MaterialTheme.colorScheme.surfaceContainer,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(4.dp, MaterialTheme.colorScheme.surfaceContainer, CircleShape)
                    )
                    Text(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .testTag("ProfileNickname"),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.primary,
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
                            iconColor = LocalExColorScheme.current.uncleaned.onColorContainer,
                            circleColor = LocalExColorScheme.current.uncleaned.colorContainer,
                            text = state.uncleanedLocations.toString()

                        )
                        CounterElement(
                            modifier = Modifier.weight(1f),
                            imageVector = Icons.Default.WorkspacePremium,
                            iconColor = MaterialTheme.colorScheme.onPrimaryContainer,//LocalExColorScheme.current.points.onColorContainer,
                            circleColor = MaterialTheme.colorScheme.primaryContainer,//LocalExColorScheme.current.points.colorContainer,
                            text = state.uncleanedLocations.toString()

                        )
                        CounterElement(
                            modifier = Modifier.weight(1f),
                            imageVector = Icons.Default.CleaningServices,
                            iconColor = LocalExColorScheme.current.cleaned.onColorContainer,
                            circleColor = LocalExColorScheme.current.cleaned.colorContainer,
                            text = state.uncleanedLocations.toString()

                        )
                        Spacer(modifier = Modifier.size(32.dp))
                    }

                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        text = stringResource(R.string.textfield_label_name)
                    )

                    SavableTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        state = state.name,
                        enabled = state.lastNameSaved != state.name.text.toString()
                                && state.name.text.length > 2,
                        onClickSave = {
                            onEvent(
                                ProfileEvent.OnSetNameValue
                            )
                        }
                    )


                    Spacer(modifier = Modifier.size(16.dp))


                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        text = stringResource(R.string.textfield_label_surname)
                    )

                    SavableTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        state = state.surname,
                        enabled = state.lastSurnameSaved != state.surname.text.toString()
                                && state.surname.text.length > 2,
                        onClickSave = {
                            onEvent(
                                ProfileEvent.OnSetSurnameValue
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
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
@PreviewLightDark
@PreviewDynamicColors
@PreviewScreenSizes
@Composable
private fun WaitingStatePreview() {
    AppTheme {
        BoxWithConstraints {
            Surface(color = MaterialTheme.colorScheme.background) {
                ProfileScreen(widthSizeClass = WindowSizeClass.calculateFromSize(
                    DpSize(
                        maxWidth, maxHeight
                    )
                ).widthSizeClass, onNavigationEvent = {}, onEvent = {}, state = ProfileState(
                    uiState = UIStateForScreen.WaitingState, nickname = "steq21"
                )
                )
            }
        }
    }

}



