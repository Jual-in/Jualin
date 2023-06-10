package com.jualin.apps.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.jualin.apps.data.Result
import com.jualin.apps.data.local.entity.User
import com.jualin.apps.data.local.preferences.UserPreferences
import com.jualin.apps.data.remote.response.auth.LoginResponse
import com.jualin.apps.data.remote.response.auth.RegisterResponse
import com.jualin.apps.data.remote.response.umkm.AddUMKMResponse
import com.jualin.apps.data.remote.response.user.UpdateUserResponse
import com.jualin.apps.data.remote.response.user.UploadPhotoUserResponse
import com.jualin.apps.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
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
            userPreferences.login(response)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("login", e.message.toString())
            emit(Result.Error(e.toString()))
        }
    }

    fun register(
        name: String,
        email: String,
        password: String,
        role: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(name, email, password, role)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("register", e.message.toString())
            emit(Result.Error(e.toString()))
        }
    }

    fun getDetailUser(): LiveData<Result<User>> {
        return liveData {
            emit(Result.Loading)
            val currentUser = userPreferences.getUser().first()
            try {
                val response = apiService.getDetailUser(currentUser.id)
                val newUser = currentUser.copy(
                    name = response.name,
                    email = response.email,
                    role = response.role,
                    alamat = response.address,
                    photoUrl = response.photoUrl,
                    businessId = response.businessId
                )
                emit(Result.Success(newUser))
            } catch (e: Exception) {
                Log.d("getDetailUser", e.message.toString())
                emit(Result.Error(e.toString()))
            }
        }
    }

    fun updaterUser(
        name: String,
        email: String,
        password: String,
        alamat: String
    ): LiveData<Result<UpdateUserResponse>> {
        return liveData {
            val currentUser = userPreferences.getUser().first()
            emit(Result.Loading)
            try {
                val response = apiService.updateUser(currentUser.id, name, email, password, alamat)
                emit(Result.Success(response))
            } catch (e: Exception) {
                Log.d("updateUser", e.message.toString())
                emit(Result.Error(e.toString()))
            }
        }
    }

    fun addPhotoUser(
        photoUrl: MultipartBody.Part
    ): LiveData<Result<UploadPhotoUserResponse>> {
        return liveData {
            val currentUser = userPreferences.getUser().first()
            emit(Result.Loading)
            try {
                val response = apiService.uploadPhoto(currentUser.id, photoUrl)
                emit(Result.Success(response))
            } catch (e: Exception) {
                Log.d("addPhotoUser", e.message.toString())
                emit(Result.Error(e.toString()))
            }
        }
    }

    fun addUMKM(
        name: String,
        kategori: String,
        noTelp: String,
        Deskripsi: String,
        latitude: Double?,
        longitude: Double?,
    ): LiveData<Result<AddUMKMResponse>> {
        return liveData {
            val currentUser = userPreferences.getUser().first()
            emit(Result.Loading)
            try {
                val response = apiService.addUMKM(
                    currentUser.id,
                    name,
                    kategori,
                    noTelp,
                    Deskripsi,
                    latitude,
                    longitude
                )
                emit(Result.Success(response))
            } catch (e: Exception) {
                Log.d("addUMKM", e.message.toString())
                emit(Result.Error(e.toString()))
            }
        }
    }

    fun getUser(): Flow<User> = userPreferences.getUser()

    suspend fun logout() {
        userPreferences.logout()
    }

    fun editBusiness(
        nama: String,
        kategori: String,
        noTelp: String,
        deskripsi: String,
        lat: Double,
        long: Double
    ): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            val currentUser = userPreferences.getUser().first()
            val response = apiService.editBusiness(
                currentUser.id,
                nama,
                deskripsi,
                kategori,
                noTelp,
                lat,
                long
            )
            emit(Result.Success(response.message))
        } catch (e: Exception) {
            emit(Result.Error(e.toString()))
        }
    }

}