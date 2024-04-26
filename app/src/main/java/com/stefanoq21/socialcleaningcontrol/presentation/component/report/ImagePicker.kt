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

package com.stefanoq21.socialcleaningcontrol.presentation.component.report

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import com.stefanoq21.socialcleaningcontrol.R

@Composable
fun ImagePicker(
    numberOfImages: Int,
    addUris: (List<Uri>) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    if (numberOfImages > 1) {
        val multiplePhotoPickerLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.PickMultipleVisualMedia(
                numberOfImages
            ),
                onResult = { uris ->
                    addUris(
                        uris
                    )
                })

        Button(onClick = {
            focusManager.clearFocus()
            multiplePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }) {
            Text(text = stringResource(R.string.report_add_photos))
        }
    } else if (numberOfImages == 1) {
        val singlePhotoPickerLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
                onResult = { uri ->
                    if (uri != null) {
                        addUris(
                            listOf(uri)
                        )
                    }
                })
        Button(onClick = {
            focusManager.clearFocus()
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }) {
            Text(text = stringResource(R.string.report_add_photo))
        }
    }
}