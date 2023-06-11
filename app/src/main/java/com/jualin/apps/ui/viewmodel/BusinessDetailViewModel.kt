package com.jualin.apps.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.jualin.apps.data.repositories.BusinessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BusinessDetailViewModel @Inject constructor(
    private val repository: BusinessRepository
) : ViewModel() {
    fun getBusinessById(id: Int) = repository.getBusinessById(id)

    fun getProductsByBusinessId(id: Int) = repository.getProductsByBusinessId(id)
    fun getServicesByBusinessId(id: Int) = repository.getServicesByBusinessId(id)

}