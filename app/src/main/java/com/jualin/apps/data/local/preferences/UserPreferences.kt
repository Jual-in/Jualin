package com.jualin.apps.data.local.preferences

import com.jualin.apps.data.local.entity.User
import kotlinx.coroutines.flow.Flow

interface UserPreferences {
    fun getUser(): Flow<User>
    suspend fun getToken(): String
    suspend fun login(user: User)
    suspend fun logout()
    suspend fun storeToken(token: String)
}