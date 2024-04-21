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
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import com.stefanoq21.socialcleaningcontrol.R


sealed class BottomBarElement(
    val screen: ScreenEnum,
    val title: Int,
    val icon: @Composable () -> Unit
) {
    data object Map : BottomBarElement(
        screen = ScreenEnum.Map,
        title = R.string.bottom_bar_map,
        icon = {
            Icon(
                modifier = Modifier.scale(0.9f),
                imageVector = Icons.Rounded.Map,
                contentDescription = null,

                )
        }
    )

    data object Profile : BottomBarElement(
        screen = ScreenEnum.Profile,
        title = R.string.bottom_bar_profile,
        icon = {
            Icon(
                modifier = Modifier.scale(0.9f),
                imageVector = Icons.Rounded.AccountCircle,
                contentDescription = null,

                )
        }
    )

}