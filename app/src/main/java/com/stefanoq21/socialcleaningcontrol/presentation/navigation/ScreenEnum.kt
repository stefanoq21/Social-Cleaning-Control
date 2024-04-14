package com.stefanoq21.socialcleaningcontrol.presentation.navigation

enum class ScreenEnum {
    Map,
    Profile,
    Permission,
    ProfileCreation,
    Welcome
    ;

    companion object {
        fun fromRoute(route: String?): ScreenEnum {
            route?.substringBefore("?")?.substringBefore("/")?.let {
                if (entries.contains(ScreenEnum.valueOf(it))) {
                    return ScreenEnum.valueOf(it)
                }
            }
            return Map
        }
    }
}


