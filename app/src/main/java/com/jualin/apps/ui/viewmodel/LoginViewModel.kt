package com.jualin.apps.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.jualin.apps.data.Repository

class LoginViewModel(private val Repository: Repository): ViewModel() {
    fun login(email: String, password: String) = Repository.login(email, password)
}