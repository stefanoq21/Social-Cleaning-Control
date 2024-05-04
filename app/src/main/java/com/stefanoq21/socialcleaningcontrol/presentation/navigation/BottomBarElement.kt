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

package com.stefanoq21.socialcleaningcontrol.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import com.stefanoq21.socialcleaningcontrol.R


sealed class BottomBarElement(
    val screen: Screen,
    val title: Int,
    val icon: @Composable () -> Unit,
    val iconSelected: @Composable () -> Unit
) {
    data object Map : BottomBarElement(
        screen = Screen.Map,
        title = R.string.bottom_bar_map,
        icon = {
            Icon(
                modifier = Modifier.scale(0.9f),
                imageVector = Icons.Outlined.Map,
                contentDescription = null,

                )
        },
        iconSelected = {
            Icon(
                modifier = Modifier.scale(0.9f),
                imageVector = Icons.Default.Map,
                contentDescription = null,

                )
        }
    )

    data object Profile : BottomBarElement(
        screen = Screen.Profile,
        title = R.string.bottom_bar_profile,
        icon = {
            Icon(
                modifier = Modifier.scale(0.9f),
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = null,

                )
        },
        iconSelected = {
            Icon(
                modifier = Modifier.scale(0.9f),
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,

                )
        }
    )

}