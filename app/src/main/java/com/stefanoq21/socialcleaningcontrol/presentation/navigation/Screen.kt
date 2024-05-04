package com.stefanoq21.socialcleaningcontrol.presentation.navigation

import androidx.navigation.NavBackStackEntry
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass


sealed interface Screen {

    @Serializable
    data object Map : Screen

    @Serializable
    data object Profile : Screen

    @Serializable
    data object Permission : Screen

    @Serializable
    data object ProfileCreation : Screen

    @Serializable
    data object Welcome : Screen

    @Serializable
    data class Report(val latitude: Float = 0f, val longitude: Float = 0f) : Screen

}

object ScreenSerializer {
    fun NavBackStackEntry?.fromRoute(): Screen {
        this?.destination?.route?.substringBefore("?")?.substringBefore("/")
            ?.substringAfterLast(".")?.let {
                when (it) {
                    Screen.Map::class.simpleName -> return Screen.Map
                    Screen.Profile::class.simpleName -> return Screen.Profile
                    Screen.Permission::class.simpleName -> return Screen.Permission
                    Screen.ProfileCreation::class.simpleName -> return Screen.ProfileCreation
                    Screen.Welcome::class.simpleName -> return Screen.Welcome
                    Screen.Report::class.simpleName -> return Screen.Report(0f, 0f)
                    else -> return Screen.Map
                }
            }
        return Screen.Map
    }

    fun Any.instanceOf(type: KClass<*>): Boolean = type.java.isInstance(this)
}
