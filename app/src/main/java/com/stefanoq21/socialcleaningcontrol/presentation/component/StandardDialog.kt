package com.stefanoq21.socialcleaningcontrol.presentation.component


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.stefanoq21.socialcleaningcontrol.R

@Composable
fun StandardDialog(
    title: String?,
    text: String?,
    buttonText: String? = null,
    onClick: () -> Unit = {},
    onDismiss: () -> Unit = {},
    properties: DialogProperties = DialogProperties(),
    cancelButtonVisible: Boolean = false,
    onCancelButtonClick: () -> Unit = onDismiss,
    cancelButtonText: String = stringResource(id = R.string.cancel)
) {
    Dialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
            onDismiss()
        }, properties = properties
    ) {
        Card(
            modifier = Modifier
                .testTag("StandardDialog"),
        ) {
            Column {

                if (properties.dismissOnBackPress && properties.dismissOnClickOutside) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = { onDismiss() }) {
                            Icon(
                                modifier = Modifier
                                    .scale(1.5f),
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Dialog exit",
                            )

                        }

                    }
                }

                title?.let {
                    Row(
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 12.dp)
                            .fillMaxWidth(), horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Start,
                        )
                    }
                }


                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = text ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Start,
                    )
                }


                if (buttonText != null) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {

                        if (cancelButtonVisible) {
                            OutlinedButton(modifier = Modifier
                                .padding(end = 12.dp)
                                .testTag("dismissButton")
                                .semantics {
                                    contentDescription = "button dismiss"
                                },
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                                onClick = { onCancelButtonClick() }

                            ) {
                                Text(
                                    text = cancelButtonText
                                )
                            }
                        }



                        Button(modifier = Modifier
                            .testTag("actionButton")
                            .semantics { contentDescription = "button dialog" },
                            onClick = { onClick() }

                        ) {
                            Text(
                                text = buttonText
                            )
                        }

                    }
                }
            }
        }


    }

}