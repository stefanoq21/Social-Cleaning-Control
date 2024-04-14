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