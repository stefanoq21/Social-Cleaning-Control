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

package com.stefanoq21.socialcleaningcontrol.domain.di

import com.stefanoq21.socialcleaningcontrol.data.database.DatabaseRepository
import com.stefanoq21.socialcleaningcontrol.data.database.LocationDatabase
import com.stefanoq21.socialcleaningcontrol.data.preference.PrefsDataStore
import com.stefanoq21.socialcleaningcontrol.presentation.navigation.NavigationViewModel
import com.stefanoq21.socialcleaningcontrol.presentation.screen.map.MapViewModel
import com.stefanoq21.socialcleaningcontrol.presentation.screen.permission.PermissionViewModel
import com.stefanoq21.socialcleaningcontrol.presentation.screen.profile.ProfileViewModel
import com.stefanoq21.socialcleaningcontrol.presentation.screen.profileCreation.ProfileCreationViewModel
import com.stefanoq21.socialcleaningcontrol.presentation.screen.report.ReportViewModel
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
        MapViewModel(get(), get())
    }
    viewModel {
        PermissionViewModel()
    }
    viewModel {
        ProfileViewModel(get(), get())
    }
    viewModel {
        ReportViewModel(get(), get())
    }
}