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

@file:OptIn(ExperimentalFoundationApi::class)

package com.stefanoq21.socialcleaningcontrol.presentation.screen.profileCreation

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.InputTransformation
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.maxLengthInChars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stefanoq21.socialcleaningcontrol.R
import com.stefanoq21.socialcleaningcontrol.domain.utils.FirstCharCapsTransformation
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationEvent
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationViewModel
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.ScreenEnum
import com.stefanoq21.socialcleaningcontrol.presentation.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileCreationInitScreen(
    widthSizeClass: WindowWidthSizeClass,
    profileCreationViewModel: ProfileCreationViewModel = koinViewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity),
    navigationViewModel: NavigationViewModel = koinViewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity),
) {
    val state by profileCreationViewModel.state.collectAsStateWithLifecycle()
    /*
        LaunchedEffect(key1 = id, block = {
            profileCreationViewModel.onEvent(ProfileCreationEvent.OnScreenLaunch)
        })*/


    ProfileCreationScreen(
        onNavigationEvent = navigationViewModel::onEvent,
        onEvent = profileCreationViewModel::onEvent,
        state = state,
        widthSizeClass = widthSizeClass,
    )


}


@Composable
fun ProfileCreationScreen(
    widthSizeClass: WindowWidthSizeClass,
    onNavigationEvent: (NavigationEvent) -> Unit,
    onEvent: (ProfileCreationEvent) -> Unit,
    state: ProfileCreationState
) {

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {

        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary,
            text = stringResource(R.string.profile_creation_title),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(16.dp))




        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.textfield_label_nickname)
        )
        BasicTextField2(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                .padding(12.dp),
            state = state.nickname,
            lineLimits = TextFieldLineLimits.SingleLine,
            inputTransformation = InputTransformation.maxLengthInChars(20),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
        )


        Spacer(modifier = Modifier.size(16.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.textfield_label_name)
        )

        BasicTextField2(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                .padding(12.dp),
            state = state.name,
            lineLimits = TextFieldLineLimits.SingleLine,
            inputTransformation = FirstCharCapsTransformation(Locale.current),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
            //inputTransformation = InputTransformation.maxLengthInChars(6)

        )

        Spacer(modifier = Modifier.size(16.dp))


        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.textfield_label_surname)
        )

        BasicTextField2(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                .padding(12.dp),
            state = state.surname,
            lineLimits = TextFieldLineLimits.SingleLine,
            inputTransformation = FirstCharCapsTransformation(Locale.current),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)

        )


        Spacer(modifier = Modifier.size(16.dp))

        FilledIconButton(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .align(Alignment.End),
            enabled = state.nickname.text.length > 2
                    && state.name.text.length > 2
                    && state.surname.text.length > 2,
            onClick = {
                onEvent(
                    ProfileCreationEvent.OnSetProfileValues
                )

                onNavigationEvent(
                    NavigationEvent.OnNavigateSingleTop(ScreenEnum.Permission)
                )
            }) {
            Icon(
                modifier = Modifier,
                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                contentDescription = null,
            )
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
                ProfileCreationScreen(
                    widthSizeClass = WindowSizeClass.calculateFromSize(
                        DpSize(
                            maxWidth,
                            maxHeight
                        )
                    ).widthSizeClass,
                    onNavigationEvent = {},
                    onEvent = {},
                    state = ProfileCreationState(
                        name = TextFieldState("aa"),
                        nickname = TextFieldState("aaaa"),
                        surname = TextFieldState("aaaa"),
                    )
                )
            }
        }
    }

}



