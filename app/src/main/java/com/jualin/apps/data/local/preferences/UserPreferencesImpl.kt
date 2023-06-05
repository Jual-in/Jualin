package com.jualin.apps.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jualin.apps.data.local.entity.User
import com.jualin.apps.data.remote.response.LoginResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : UserPreferences {

    private val Context.dataStore by preferencesDataStore(name = "preferences")

    override fun getUser(): Flow<User> {
        return context.dataStore.data.map { preferences ->
            User(
                preferences[STATE_KEY] ?: false,
                preferences[USER_ID_KEY] ?: 0
            )
        }
    }

    override suspend fun getToken(): String {
        return context.dataStore.data.map { preferences ->
            preferences[TOKEN_KEY] ?: ""
        }.first()
    }

    override suspend fun login(response: LoginResponse) {
        context.dataStore.edit { preferences ->
            preferences[STATE_KEY] = true
            preferences[USER_ID_KEY] = response.id
        }
    }

    override suspend fun logout() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    override suspend fun storeToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    companion object {
        private val USER_ID_KEY = intPreferencesKey("user_id")

        private val STATE_KEY = booleanPreferencesKey("state")
        private val TOKEN_KEY = stringPreferencesKey("token")
    }
}