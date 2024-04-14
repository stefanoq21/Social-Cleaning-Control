package com.stefanoq21.socialcleaningcontrol.presentation.component.navigation

import android.content.pm.ActivityInfo
import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationViewModel
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.ScreenEnum
import com.stefanoq21.socialcleaningcontrol.presentation.screen.welcome.WelcomeInitScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    windowSize: WindowSizeClass,
    navigationViewModel: NavigationViewModel = koinViewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity),
    setOrientationForScreen: (Int) -> Unit,
) {

    NavHost(
        navController = navigationViewModel.activityNavController,
        startDestination = ScreenEnum.Map.name,
        modifier = modifier
    ) {
        composable(ScreenEnum.Map.name) {
            LaunchedEffect(key1 = ScreenEnum.Map.name, block = {
                setOrientationForScreen(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR)
            })
            /* HomeInitScreen(
                 widthSizeClass = windowSize.widthSizeClass,
             )*/
        }

        composable(ScreenEnum.Profile.name) {
            /*  ProfileInitScreen(
                  widthSizeClass = windowSize.widthSizeClass,
              )*/
        }

        composable(ScreenEnum.Welcome.name) {
              WelcomeInitScreen(
                  widthSizeClass = windowSize.widthSizeClass,
              )
        }

        composable(ScreenEnum.ProfileCreation.name) {
            /*  ProfileInitScreen(
                  widthSizeClass = windowSize.widthSizeClass,
              )*/
        }

        composable(ScreenEnum.Permission.name) {
            /*  ProfileInitScreen(
                  widthSizeClass = windowSize.widthSizeClass,
              )*/
        }


    }
}

