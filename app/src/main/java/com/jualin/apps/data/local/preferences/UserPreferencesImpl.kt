package com.jualin.apps.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jualin.apps.data.local.entity.User
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
                preferences[USER_NAME_KEY] ?: "",
                preferences[STATE_KEY] ?: false
            )
        }
    }

    override suspend fun getToken(): String {
        return context.dataStore.data.map { preferences ->
            preferences[TOKEN_KEY] ?: ""
        }.first()
    }

    override suspend fun login(user: User) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = user.name
            preferences[STATE_KEY] = true
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
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
        private val STATE_KEY = booleanPreferencesKey("state")
        private val TOKEN_KEY = stringPreferencesKey("token")
    }
}