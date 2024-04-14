package com.stefanoq21.socialcleaningcontrol.data.preference

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val PREFERENCES_FILE = "data_store_file"

class PrefsDataStore(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = PREFERENCES_FILE)

    private val booleanTestPref = booleanPreferencesKey("booleanTestPref")
    private val booleanShowWelcomeDialog = booleanPreferencesKey("booleanShowWelcomeDialog")

    suspend fun setBooleanTestPref(value: Boolean) {
        context.dataStore.edit {
            it[booleanTestPref] = value
        }
    }

    fun getBooleanTestPref(): Flow<Boolean> =
        context.dataStore.data/*.catch {  *//*emit(emptyPreferences())*//* throw it }*/.map {
            it[booleanTestPref] ?: true
        }

    suspend fun getBooleanTestPrefDirect(): Boolean =
        context.dataStore.data.first()[booleanTestPref] ?: true


    /*suspend fun setLanguagesSelected(value: String?) {
        context.dataStore.edit {
            if (value == null) {
                it.remove(stringLanguagesSelected)
            } else {
                it[stringLanguagesSelected] = value
            }
        }
    }

    fun getLanguagesSelected(): Flow<String?> =
        context.dataStore.data.map {
            it[stringLanguagesSelected]
        }

    suspend fun getLanguagesSelectedDirect(): String? =
        context.dataStore.data.first()[stringLanguagesSelected]
*/

    suspend fun setShowWelcomeDialog(value: Boolean) {
        context.dataStore.edit {
            it[booleanShowWelcomeDialog] = value
        }
    }

    fun getShowWelcomeDialog(): Flow<Boolean> =
        context.dataStore.data.map {
            it[booleanShowWelcomeDialog] ?: true
        }


}