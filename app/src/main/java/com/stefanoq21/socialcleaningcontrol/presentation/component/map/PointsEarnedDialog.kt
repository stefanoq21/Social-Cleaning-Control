package com.stefanoq21.socialcleaningcontrol.presentation.component.map


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.stefanoq21.socialcleaningcontrol.R
import com.stefanoq21.socialcleaningcontrol.presentation.theme.AppTheme

@Composable
fun PointsEarnedDialog(
    pointsEarned: Int,
    isFirstTime: Boolean = false,
    onDismiss: () -> Unit = {},
    properties: DialogProperties = DialogProperties(),
) {
    Dialog(
        onDismissRequest = {
            onDismiss()
        }, properties = properties
    ) {
        Card(
            modifier = Modifier,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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

                Text(
                    text = "\uD83C\uDF89",
                    modifier = Modifier.padding(12.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 48.sp
                )

                Text(
                    text = stringResource(R.string.point_dialog_title),
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                if (pointsEarned > 0) {
                    Text(
                        text = stringResource(R.string.point_dialog_point_earned, pointsEarned),
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }


                if (isFirstTime) {
                    Text(
                        text = stringResource(R.string.point_dialog_first_time_text),
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))


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
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            PointsEarnedDialog(20, true)
        }
    }

}