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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.LatLng
import com.stefanoq21.socialcleaningcontrol.R
import com.stefanoq21.socialcleaningcontrol.presentation.component.Loader
import com.stefanoq21.socialcleaningcontrol.presentation.component.LongTextTextField
import com.stefanoq21.socialcleaningcontrol.presentation.component.StandardDialog
import com.stefanoq21.socialcleaningcontrol.presentation.component.report.PhotoPicker
import com.stefanoq21.socialcleaningcontrol.presentation.component.report.ReportImage
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationEvent
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationViewModel
import com.stefanoq21.socialcleaningcontrol.presentation.screen.model.UIStateForScreen
import com.stefanoq21.socialcleaningcontrol.presentation.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
                latLng, Geocoder(context, Locale.getDefault())
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
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

    var reportSentDialogVisible by remember { mutableStateOf(false) }

    if (reportSentDialogVisible) {
        StandardDialog(title = stringResource(R.string.report_sent_dialog_title),
            text = stringResource(R.string.report_sent_dialog_text),
            buttonText = stringResource(R.string.report_sent_dialog_button_positive),
            cancelButtonText = stringResource(R.string.report_sent_dialog_button_negative),
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            cancelButtonVisible = true,
            onCancelButtonClick = {
                reportSentDialogVisible = false
            },
            onClick = {
                onNavigationEvent(
                    NavigationEvent.OnBack
                )
                onEvent(ReportEvent.OnSaveReport)
            })
    }

    Column(Modifier.fillMaxSize()) {

        when (state.uiState) {
            UIStateForScreen.WaitingState -> {
                Scaffold(topBar = {
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
                }, content = { padding ->
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(padding)
                            .padding(12.dp)
                            .verticalScroll(rememberScrollState()),
                    ) {

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
                            modifier = Modifier.fillMaxWidth(),
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
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(R.string.report_description_text)
                        )

                        LongTextTextField(state.description)

                        Spacer(modifier = Modifier.size(16.dp))

                        if (state.numberOfPhotos > 0) {
                            PhotoPicker(state.selectedImageUris.size) {
                                onEvent(
                                    ReportEvent.OnAddUris(it)
                                )
                            }
                        }

                        LazyRow {
                            items(state.selectedImageUris) {
                                ReportImage(it) {
                                    onEvent(
                                        ReportEvent.OnRemoveUri(it)
                                    )
                                }
                            }
                        }
                    }
                }, floatingActionButtonPosition = FabPosition.Center, floatingActionButton = {
                    ExtendedFloatingActionButton(text = {
                        Text(text = stringResource(R.string.report_send))
                    }, icon = {
                        Icon(
                            modifier = Modifier,
                            imageVector = Icons.Outlined.Email,
                            contentDescription = null,
                        )
                    }, onClick = {
                        focusManager.clearFocus()
                        onEvent(
                            ReportEvent.OnSendReport(
                                context,
                                onFail = {
                                    onNavigationEvent(
                                        NavigationEvent.OnShowSnackBar(
                                            context.getString(
                                                R.string.error_email_client_not_found
                                            )
                                        )
                                    )
                                },
                                onSuccess = {
                                    coroutineScope.launch {
                                        delay(1000)
                                        reportSentDialogVisible = true
                                    }
                                }
                            )
                        )
                    })
                })

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
                            maxWidth, maxHeight
                        )
                    ).widthSizeClass,
                    onNavigationEvent = {},
                    onEvent = {},
                    state = ReportState(
                        uiState = UIStateForScreen.WaitingState, address = "street 554, root 5"
                    ),
                )
            }
        }
    }

}



