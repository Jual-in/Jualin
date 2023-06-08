package com.jualin.apps.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jualin.apps.data.Result
import com.jualin.apps.data.local.entity.User
import com.jualin.apps.data.remote.response.user.UpdateUserResponse
import com.jualin.apps.data.remote.response.user.UploadPhotoUserResponse
import com.jualin.apps.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

    fun login(email: String, password: String) = userRepository.login(email, password)

    fun register(name: String, email: String, password: String, role: String) =
        userRepository.register(name, email, password, role)

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }

    fun checkLogin() {
        viewModelScope.launch {
            userRepository.getUser().collect {
                _isLoggedIn.value = it.isLogin
            }
        }
    }

    fun getUserDetail(): LiveData<Result<User>> {
        return userRepository.getDetailUser()
    }

    fun updateUser(name: String, email: String, password: String, alamat: String, )
    : LiveData<Result<UpdateUserResponse>> {
        return userRepository.updaterUser(name, email, password, alamat)
    }

    fun uploadPhotoUser(file: MultipartBody.Part)
    : LiveData<Result<UploadPhotoUserResponse>> {
        return userRepository.addPhotoUser(file)
    }

}