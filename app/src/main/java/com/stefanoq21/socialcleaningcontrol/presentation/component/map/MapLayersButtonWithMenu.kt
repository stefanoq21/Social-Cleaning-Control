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

package com.stefanoq21.socialcleaningcontrol.presentation.component.map

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.SatelliteAlt
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.maps.android.compose.MapType


@Composable
fun MapLayersButtonWithMenu(
    mapType: MapType,
    onClick: (MapType) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    FilledTonalIconButton(
        onClick = {
            expanded = !expanded
        }) {
        Icon(
            modifier = Modifier,
            imageVector = Icons.Outlined.Layers,
            contentDescription = null,
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {

        DropdownMenuItem(
            text = { Text("Default") },
            onClick = {
                onClick(MapType.NORMAL)
                expanded = false
            },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Map,
                    contentDescription = null
                )
            },
            enabled = mapType == MapType.SATELLITE

        )

        DropdownMenuItem(
            text = { Text("Satellite") },
            onClick = {
                onClick(MapType.SATELLITE)
                expanded = false
            },
            leadingIcon = {
                Icon(
                    Icons.Outlined.SatelliteAlt,
                    contentDescription = null
                )
            },
            enabled = mapType == MapType.NORMAL
        )

    }
}