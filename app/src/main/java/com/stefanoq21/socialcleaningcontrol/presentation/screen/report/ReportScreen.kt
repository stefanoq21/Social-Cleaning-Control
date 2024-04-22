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
package com.stefanoq21.socialcleaningcontrol.presentation.screen.report

import android.location.Geocoder
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.InputTransformation
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.maxLengthInChars
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.LatLng
import com.stefanoq21.socialcleaningcontrol.R
import com.stefanoq21.socialcleaningcontrol.presentation.component.Loader
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationEvent
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationViewModel
import com.stefanoq21.socialcleaningcontrol.presentation.screen.model.UIStateForScreen
import com.stefanoq21.socialcleaningcontrol.presentation.theme.AppTheme
import org.koin.androidx.compose.koinViewModel
import java.util.Locale


@Composable
fun ReportInitScreen(
    widthSizeClass: WindowWidthSizeClass,
    latLng: LatLng,
    reportViewModel: ReportViewModel = koinViewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity),
    navigationViewModel: NavigationViewModel = koinViewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity),
) {
    val state by reportViewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit, block = {
        reportViewModel.onEvent(
            ReportEvent.OnScreenLaunch(
                latLng,
                Geocoder(context, Locale.getDefault())
            )
        )
    })

    ReportScreen(
        onNavigationEvent = navigationViewModel::onEvent,
        onEvent = reportViewModel::onEvent,
        state = state,
        widthSizeClass = widthSizeClass,
    )


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReportScreen(
    widthSizeClass: WindowWidthSizeClass,
    onNavigationEvent: (NavigationEvent) -> Unit,
    onEvent: (ReportEvent) -> Unit,
    state: ReportState,
) {
    val coroutineScope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize()) {

        when (state.uiState) {
            UIStateForScreen.WaitingState -> {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .verticalScroll(rememberScrollState()),
                ) {

                    IconButton(onClick = {
                        onNavigationEvent(
                            NavigationEvent.OnBack
                        )
                    }) {
                        Icon(
                            modifier = Modifier,
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                        )
                    }


                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.primary,
                        text = stringResource(R.string.report_title),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.size(16.dp))

                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(R.string.report_address_title),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        text = state.address
                    )

                    Spacer(modifier = Modifier.size(16.dp))


                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        text = stringResource(R.string.report_description_title),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(R.string.report_description_text)
                    )
                    val scrollState = rememberScrollState()
                    val maxChars = 200

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .border(
                                2.dp,
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.shapes.small
                            )
                            .padding(12.dp)
                    ) {
                        BasicTextField2(
                            modifier = Modifier.fillMaxWidth(),
                            state = state.description,
                            scrollState = scrollState,
                            lineLimits = TextFieldLineLimits.MultiLine(8, 8),
                            inputTransformation = InputTransformation.maxLengthInChars(maxChars)
                        )


                        Text(
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(top = 2.dp),
                            text = stringResource(
                                R.string.description_count,
                                state.description.text.length,
                                maxChars
                            ),
                            style = MaterialTheme.typography.labelSmall
                        )


                    }
                    Spacer(modifier = Modifier.size(16.dp))

                    Button(
                        onClick = {
                            //todo
                        }) {
                        Text(text = "Add photos")
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
@PreviewLightDark
@PreviewDynamicColors
@PreviewScreenSizes
@Composable
private fun WaitingStatePreview() {
    AppTheme {
        BoxWithConstraints {
            Surface(color = MaterialTheme.colorScheme.background) {
                ReportScreen(
                    widthSizeClass = WindowSizeClass.calculateFromSize(
                        DpSize(
                            maxWidth,
                            maxHeight
                        )
                    ).widthSizeClass,
                    onNavigationEvent = {},
                    onEvent = {},
                    state = ReportState(
                        uiState = UIStateForScreen.WaitingState,
                        address = "street 554, root 5"
                    ),
                )
            }
        }
    }

}



