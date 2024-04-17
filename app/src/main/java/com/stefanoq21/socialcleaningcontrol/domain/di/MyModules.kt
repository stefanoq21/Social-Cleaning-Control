package com.stefanoq21.socialcleaningcontrol.domain.di

import com.stefanoq21.socialcleaningcontrol.data.database.LocationDatabase
import com.stefanoq21.socialcleaningcontrol.data.preference.PrefsDataStore
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationViewModel
import com.stefanoq21.socialcleaningcontrol.presentation.screen.map.MapViewModel
import com.stefanoq21.socialcleaningcontrol.presentation.screen.permission.PermissionViewModel
import com.stefanoq21.socialcleaningcontrol.presentation.screen.profileCreation.ProfileCreationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModules = module {
    single { PrefsDataStore(get()) }
    single { LocationDatabase.getInstance(get()) }
    factory { DatabaseRepository(get()) }

    viewModel {
        NavigationViewModel()
    }
    viewModel {
        ProfileCreationViewModel(get())
    }
    viewModel {
       MapViewModel(get())
    }
    viewModel {
       PermissionViewModel()
    }
}