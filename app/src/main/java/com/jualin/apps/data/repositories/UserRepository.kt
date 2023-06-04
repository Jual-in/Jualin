package com.jualin.apps.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.jualin.apps.data.Result
import com.jualin.apps.data.local.entity.User
import com.jualin.apps.data.local.preferences.UserPreferences
import com.jualin.apps.data.remote.response.LoginResponse
import com.jualin.apps.data.remote.response.RegisterResponse
import com.jualin.apps.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences,
) {

    fun login(
        email: String,
        password: String
    ): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)
            userPreferences.storeToken(response.token)
            userPreferences.login(User(response.name, true))
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("login", e.message.toString())
            emit(Result.Error(e.toString()))
        }
    }

    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(name, email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("register", e.message.toString())
            emit(Result.Error(e.toString()))
        }
    }

    fun getUser(): Flow<User> = userPreferences.getUser()

    suspend fun logout() {
        userPreferences.logout()
    }

}