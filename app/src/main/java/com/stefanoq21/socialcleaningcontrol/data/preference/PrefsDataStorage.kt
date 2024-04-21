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

package com.stefanoq21.socialcleaningcontrol.data.preference

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val PREFERENCES_FILE = "data_store_file"

class PrefsDataStore(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = PREFERENCES_FILE)

    private val nickname = stringPreferencesKey("nickname")
    private val name = stringPreferencesKey("name")
    private val surname = stringPreferencesKey("surname")
    private val points = intPreferencesKey("points")

    suspend fun setNickname(value: String) {
        context.dataStore.edit {
            it[nickname] = value
        }
    }

    fun getNickname(): Flow<String?> =
        context.dataStore.data.map {
            it[nickname]
        }

    suspend fun getNicknameDirect(): String? =
        context.dataStore.data.first()[nickname]


    suspend fun setName(value: String) {
        context.dataStore.edit {
            it[name] = value
        }
    }

    fun getName(): Flow<String> =
        context.dataStore.data.map {
            it[name] ?: ""
        }

    suspend fun setSurname(value: String) {
        context.dataStore.edit {
            it[surname] = value
        }
    }

    fun getSurname(): Flow<String> =
        context.dataStore.data.map {
            it[surname] ?: ""
        }

    suspend fun setPoints(value: Int) {
        context.dataStore.edit {
            it[points] = value
        }
    }

    fun getPoints(): Flow<Int> =
        context.dataStore.data.map {
            it[points] ?: 0
        }

}