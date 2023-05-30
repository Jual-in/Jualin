package com.jualin.apps.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.jualin.apps.data.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)
}