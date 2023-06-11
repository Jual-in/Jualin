package com.jualin.apps.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jualin.apps.data.Result
import com.jualin.apps.data.local.entity.Product
import com.jualin.apps.data.local.entity.Service
import com.jualin.apps.data.repositories.BusinessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BusinessContentViewModel @Inject constructor(
    private val businessRepository: BusinessRepository
) : ViewModel() {

    private val _products: MutableLiveData<Result<List<Product>>> = MutableLiveData()
    val products: LiveData<Result<List<Product>>> = _products

    private val _service: MutableLiveData<Result<List<Service>>> = MutableLiveData()
    val services: LiveData<Result<List<Service>>> = _service

    fun getProductsByBusinessId(businessId: Int) {
        _products.value = Result.Loading
        businessRepository.getProductsByBusinessId(businessId).observeForever {
            _products.value = it
        }
    }

    fun getServicesByBusinessId(businessId: Int) {
        _service.value = Result.Loading
        businessRepository.getServicesByBusinessId(businessId).observeForever {
            _service.value = it
        }
    }

}