package com.example.duck.fastnotes.manager

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.duck.fastnotes.data.UserInfoData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PreferenceManager @Inject constructor(@ApplicationContext private val context: Context) {

    private companion object {
        private const val USER_INFO_NAME = "user_info"

        private const val USER_LOGIN_KEY = "USER_LOGIN_KEY"
        private const val USER_NAME_KEY = "USER_NAME_KEY"
        private const val USER_IMAGE_KEY = "USER_IMAGE_KEY"
        private const val USER_REGISTER_FROM_KEY = "USER_REGISTER_FROM_KEY"
        private const val USER_REGISTER_NOT_KEY = "USER_REGISTER_NOT_KEY"

        private val loginKey = stringPreferencesKey(USER_LOGIN_KEY)
        private val nameKey = stringPreferencesKey(USER_NAME_KEY)
        private val imageKey = stringPreferencesKey(USER_IMAGE_KEY)
        private val registeredSince = stringPreferencesKey(USER_REGISTER_FROM_KEY)
        private val notRegisteredKey = booleanPreferencesKey(USER_REGISTER_NOT_KEY)
    }

    private val Context.userDataStore by preferencesDataStore(name = USER_INFO_NAME)

    suspend fun getUserData(): UserInfoData {
        val preference = context.userDataStore.data.first()
        val login = preference[loginKey] ?: ""
        val name = preference[nameKey] ?: ""
        val imageUrl = preference[imageKey] ?: ""
        val registeredSince = preference[registeredSince] ?: ""
        val notRegistered = preference[notRegisteredKey] ?: false

        return UserInfoData(
            login = login,
            name = name,
            imageUrl = imageUrl,
            registeredSince = registeredSince,
            notRegistered
        )
    }

    suspend fun addLogin(login: String?) {
        context.userDataStore.edit { prefs ->
            prefs[loginKey] = login ?: ""
        }
    }

    suspend fun setContinuedWithoutRegistration() {
        context.userDataStore.edit { prefs ->
            prefs[notRegisteredKey] = true
        }
    }

    // TODO()
    suspend fun addName(name: String?) {

    }

    // TODO()
    suspend fun addImageUrl(imageUrl: String?) {

    }

    // TODO()
    suspend fun addRegisteredFrom(date: String?) {

    }

    suspend fun clearData() {
        context.userDataStore.edit { prefs ->
            prefs[loginKey] = ""
            prefs[nameKey] = ""
            prefs[imageKey] = ""
            prefs[registeredSince] = ""
        }
    }
}