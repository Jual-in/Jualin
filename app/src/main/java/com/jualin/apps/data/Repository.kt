package com.jualin.apps.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.jualin.apps.data.remote.response.LoginResponse
import com.jualin.apps.data.remote.response.RegisterResponse
import com.jualin.apps.data.remote.retrofit.ApiService


class Repository constructor(
    private val apiService: ApiService
    ){

    fun login(
        email: String,
        password: String
    ): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)
            emit(Result.Success(response))
        } catch(e : Exception) {
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
        } catch(e : Exception) {
            Log.d("register", e.message.toString())
            emit(Result.Error(e.toString()))
        }
    }


}