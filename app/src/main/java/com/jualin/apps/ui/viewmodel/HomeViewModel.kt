package com.jualin.apps.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.jualin.apps.data.repositories.BusinessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val businessRepository: BusinessRepository
) : ViewModel() {
    fun getRecommendedBusiness() = businessRepository.getRecommendedBusiness()
}