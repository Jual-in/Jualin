package com.jualin.apps.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.jualin.apps.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

}