package com.stefanoq21.socialcleaningcontrol.data.preference

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
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
            it[name]?:""
        }

    suspend fun setSurname(value: String) {
        context.dataStore.edit {
            it[surname] = value
        }
    }

    fun getSurname(): Flow<String> =
        context.dataStore.data.map {
            it[surname]?:""
        }

}