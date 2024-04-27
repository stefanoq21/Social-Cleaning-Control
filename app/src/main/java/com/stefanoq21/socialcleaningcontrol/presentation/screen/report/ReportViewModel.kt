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

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.stefanoq21.socialcleaningcontrol.R
import com.stefanoq21.socialcleaningcontrol.data.Constants
import com.stefanoq21.socialcleaningcontrol.data.database.DatabaseRepository
import com.stefanoq21.socialcleaningcontrol.data.preference.PrefsDataStore
import com.stefanoq21.socialcleaningcontrol.presentation.screen.model.UIStateForScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.Date

@OptIn(ExperimentalFoundationApi::class)
class ReportViewModel(
    private val prefsDataStore: PrefsDataStore,
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {

    private val _nameFlow =
        prefsDataStore.getName().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")

    private val _surnameFlow =
        prefsDataStore.getSurname().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")


    private val _state = MutableStateFlow(ReportState())

    val state = combine(
        _state,
        _nameFlow,
        _surnameFlow,
    ) { state, nameFlow, surnameFlow ->
        state.copy(
            name = nameFlow, surname = surnameFlow
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ReportState())


    fun onEvent(event: ReportEvent) {
        when (event) {
            is ReportEvent.OnScreenLaunch -> {
                performOnScreenStart(event.latLng, event.geocoder)
            }

            is ReportEvent.OnAddUris -> {
                _state.update {
                    val list = it.selectedImageUris.toMutableList()
                    list.addAll(event.uris)
                    it.copy(
                        selectedImageUris = list,
                        numberOfPhotos = Constants.nPhotoIntheReport - list.size
                    )
                }
            }

            is ReportEvent.OnRemoveUri -> {
                _state.update {
                    val list = it.selectedImageUris.toMutableList()
                    list.remove(event.uri)
                    it.copy(
                        selectedImageUris = list,
                        numberOfPhotos = Constants.nPhotoIntheReport - list.size
                    )
                }
            }

            is ReportEvent.OnSendReport -> {
                sendReportEmail(event.ctx, event.onFail, event.onSuccess)
            }

            ReportEvent.OnSaveReport -> {
                saveReport()
            }
        }
    }


    private fun performOnScreenStart(latLng: LatLng, geocoder: Geocoder) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                ReportState(
                    uiState = UIStateForScreen.OnLoadingState, latLng = latLng
                )
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(latLng.latitude,
                    latLng.longitude,
                    1,
                    object : Geocoder.GeocodeListener {
                        override fun onGeocode(addresses: MutableList<Address>) {
                            _state.update { state ->
                                state.copy(
                                    uiState = UIStateForScreen.WaitingState,
                                    address = addresses[0].getAddressLine(0)
                                )
                            }

                        }

                        override fun onError(errorMessage: String?) {
                            super.onError(errorMessage)

                            _state.update { state ->
                                state.copy(
                                    uiState = UIStateForScreen.WaitingState,
                                    address = "${latLng.latitude} / ${latLng.longitude}"
                                )
                            }


                        }
                    })
            } else {
                val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                _state.update { state ->
                    state.copy(
                        uiState = UIStateForScreen.WaitingState,
                        address = addresses?.get(0)?.getAddressLine(0) ?: ""
                    )
                }
            }


        }
    }


    private fun sendReportEmail(context: Context, onFail: () -> Unit, onSuccess: () -> Unit) {
        //val emailIntent = Intent(Intent.ACTION_SEND_MULTIPLE)
        val emailIntent = Intent(Intent.ACTION_SENDTO)

        emailIntent.putExtra(Intent.EXTRA_EMAIL, "")
        emailIntent.putExtra(Intent.EXTRA_CC, "")
        emailIntent.putExtra(
            Intent.EXTRA_SUBJECT, context.getString(R.string.email_subject, state.value.address)
        )

        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        val list = arrayListOf<Uri>()
        state.value.selectedImageUris.forEachIndexed { index, uri ->
            saveTempImage(context, uri, index)?.let { list.add(it) }
        }

        /* emailIntent.putParcelableArrayListExtra(
             Intent.EXTRA_STREAM,
             list
         )*/
        if (list.isNotEmpty()) {
            emailIntent.putExtra(
                Intent.EXTRA_STREAM, list.first()
            )
        }


        val emailBody = StringBuilder()
        emailBody.append(context.getString(R.string.email_text_header, state.value.address))
        if (state.value.description.text.isNotBlank()) {
            emailBody.append(context.getString(
                R.string.email_text_description,
                state.value.name,
                state.value.surname,
                state.value.description.text
            ))
        }
        if (list.isNotEmpty()) {
            emailBody.append(context.getString(R.string.email_text_photo))
        }
        emailBody.append(context.getString(
            R.string.email_text_footer,
            state.value.name,
            state.value.surname
        ))

        emailIntent.putExtra(
            Intent.EXTRA_TEXT,
            emailBody.toString()
        )

        // emailIntent.type="image/jpg"
        emailIntent.data = Uri.parse("mailto:")
        try {
            context.startActivity(
                Intent.createChooser(
                    emailIntent, context.getString(
                        R.string.report_send
                    )
                )
            )
            onSuccess()
        } catch (e: Exception) {
            e.printStackTrace()
            onFail()
        }
    }

    private fun saveTempImage(context: Context, uri: Uri, index: Int): Uri? {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri) ?: return null

        val tempFileName = "temp_image${index}.jpg"
        val tempFile = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            tempFileName
        )

        val outputStream = FileOutputStream(tempFile)
        val buffer = ByteArray(1024)
        var readBytes: Int

        while (inputStream.read(buffer).also { readBytes = it } > 0) {
            outputStream.write(buffer, 0, readBytes)
        }

        inputStream.close()
        outputStream.close()

        return Uri.fromFile(tempFile)

    }


    private fun saveReport(
    ) {
        state.value.latLng?.let {
            viewModelScope.launch(Dispatchers.IO) {
                databaseRepository.insertLocation(
                    date = Date(),
                    latLng = it,
                    cleaned = false,
                    description = state.value.description.text.toString()
                )

                prefsDataStore.increasePointsAndShowDialog(Constants.pointsDirtyArea)
            }
        }


    }


}

