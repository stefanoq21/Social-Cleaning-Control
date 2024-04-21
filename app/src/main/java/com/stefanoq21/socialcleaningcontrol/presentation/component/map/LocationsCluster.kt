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

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering
import com.stefanoq21.socialcleaningcontrol.data.database.location.LocationItem
import com.stefanoq21.socialcleaningcontrol.presentation.theme.LocalExColorScheme


@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun LocationsCluster(locations: List<LocationItem>) {
    val localExColorScheme = LocalExColorScheme.current

    Clustering(
        items = locations,
        clusterItemContent = {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .background(
                        if (it.cleaned)
                            localExColorScheme.cleaned.colorContainer
                        else
                            localExColorScheme.uncleaned.colorContainer,
                        CircleShape
                    )
                    .border(1.dp, MaterialTheme.colorScheme.background, CircleShape)
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    imageVector = if (it.cleaned) Icons.Default.CleaningServices else Icons.Default.DeleteForever,
                    contentDescription = "",
                    tint = if (it.cleaned) localExColorScheme.cleaned.onColorContainer else localExColorScheme.uncleaned.onColorContainer,
                )
            }
        }
    )
}