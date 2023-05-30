package com.jualin.apps.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jualin.apps.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

    fun login(email: String, password: String) = userRepository.login(email, password)

    fun register(name: String, email: String, password: String) =
        userRepository.register(name, email, password)

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
}