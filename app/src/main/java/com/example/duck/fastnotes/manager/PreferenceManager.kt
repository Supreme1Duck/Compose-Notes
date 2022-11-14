package com.example.duck.fastnotes.manager

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferenceManager @Inject constructor(@ApplicationContext private val context: Context) {

    private companion object {
        // store keys
        private const val SETTINGS_KEY = "settings_key"
        private const val USER_KEY = "user_key"

        // preference keys
        private const val REGISTER_KEY = "register_key"
        private val REGIS = booleanPreferencesKey(REGISTER_KEY)
    }

    private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS_KEY)
    private val Context.protoStore: DataStore<Preferences> by preferencesDataStore(name = USER_KEY)

    fun getRegistration(): Flow<Boolean> {
        return context.settingsDataStore.data
            .catch {
                Log.d("DDebug", "Preference Manager: $it")
            }
            .map {
                it[REGIS] ?: false
            }
    }

    suspend fun setRegistered(registered: Boolean) {
        context.settingsDataStore.edit { prefs ->
            prefs[REGIS] = registered
        }
    }
}