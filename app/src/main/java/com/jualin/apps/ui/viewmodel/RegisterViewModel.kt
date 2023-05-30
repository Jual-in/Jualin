package com.jualin.apps.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.jualin.apps.data.repositories.Repository
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    fun register(name: String, email: String, password: String) =
        repository.register(name, email, password)
}