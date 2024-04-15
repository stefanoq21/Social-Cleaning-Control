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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.InputTransformation
import androidx.compose.foundation.text2.input.maxLengthInChars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stefanoq21.socialcleaningcontrol.R
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationEvent
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationViewModel
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.ScreenEnum
import com.stefanoq21.socialcleaningcontrol.presentation.theme.SocialCleaningControlTheme
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
    ) {
        Text(
            modifier = Modifier
                .padding(12.dp)
                .testTag("ProfileCreationTitle"),
            style = MaterialTheme.typography.titleLarge,
            text = stringResource(R.string.profile_creation_title),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            text = stringResource(R.string.textfield_label_nickname)
        )

        BasicTextField2(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .border(2.dp, MaterialTheme.colorScheme.onBackground, MaterialTheme.shapes.small)
                .padding(12.dp)
               ,
            state = state.nickname

        )


        Spacer(modifier = Modifier.size(16.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal =12.dp),
            text = stringResource(R.string.textfield_label_name)
        )

        BasicTextField2(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .border(2.dp, MaterialTheme.colorScheme.onBackground, MaterialTheme.shapes.small)
                .padding(12.dp)
            ,
            state = state.name,
            //inputTransformation = InputTransformation.maxLengthInChars(6)

        )

        Spacer(modifier = Modifier.size(16.dp))


        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal =12.dp),
            text = stringResource(R.string.textfield_label_surname)
        )

        BasicTextField2(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .border(2.dp, MaterialTheme.colorScheme.onBackground, MaterialTheme.shapes.small)
                .padding(12.dp)
            ,
            state = state.surname

        )


        Spacer(modifier = Modifier.size(16.dp))

        IconButton(
            modifier = Modifier
                .padding(12.dp)
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
@Preview(device = Devices.PHONE)
@Preview(device = Devices.FOLDABLE)
@Preview(device = Devices.TABLET)
@Preview(device = Devices.DESKTOP)
@Composable
private fun WaitingStatePreview() {
    SocialCleaningControlTheme {
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
                    state=ProfileCreationState()
                )
            }
        }
    }

}



