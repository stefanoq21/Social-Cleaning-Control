package com.stefanoq21.socialcleaningcontrol.domain.di

import com.stefanoq21.socialcleaningcontrol.data.preference.PrefsDataStore
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationViewModel
import com.stefanoq21.socialcleaningcontrol.presentation.screen.profileCreation.ProfileCreationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModules = module {
    single { PrefsDataStore(get()) }

    viewModel {
        NavigationViewModel()
    }
    viewModel {
        ProfileCreationViewModel(get())
    }
}