package com.jualin.apps.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jualin.apps.data.Result
import com.jualin.apps.data.local.entity.Category
import com.jualin.apps.data.local.entity.Product
import com.jualin.apps.data.local.entity.Service
import com.jualin.apps.data.remote.response.umkm.AddUMKMResponse
import com.jualin.apps.data.repositories.BusinessRepository
import com.jualin.apps.data.repositories.CategoryRepository
import com.jualin.apps.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UmkmViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val businessRepository: BusinessRepository,
    private val categoryRepository: CategoryRepository
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

    fun addUMKM(
        nama: String,
        kategori: String,
        noTelp: String,
        deskripsi: String,
        lat: Double?,
        lng: Double?,
    ): LiveData<Result<AddUMKMResponse>> {
        return userRepository.addUMKM(nama, kategori, noTelp, deskripsi, lat, lng)
    }

    fun editBusiness(
        nama: String,
        kategori: String,
        noTelp: String,
        deskripsi: String,
        lat: Double?,
        long: Double?
    ): LiveData<Result<String>> {
        return userRepository.editBusiness(nama, kategori, noTelp, deskripsi, lat, long)
    }

    fun getBusinessById(businessId: Int) = businessRepository.getBusinessById(businessId)

    fun getAllCategories(callback: (List<Category>) -> Unit) {
        categoryRepository.getAllCategories().observeForever {
            if (it is Result.Success) {
                callback(it.data)
            }
        }
    }

    fun addProduct(businessId: Int, name: String, price: Int, discount: Int, photo: File) =
        businessRepository.addProduct(businessId, name, price, discount, photo)

    fun editProduct(
        productId: Int,
        name: String,
        price: Int,
        discount: Int,
        photo: File?
    ) = businessRepository.editProduct(productId, name, price, discount, photo)

    fun deleteProductById(productId: Int, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val res = businessRepository.deleteProductById(productId)
            callback(res)
        }
    }

    fun addService(businessId: Int, name: String, price: Int, discount: Int) =
        businessRepository.addService(businessId, name, price, discount)

    fun editService(
        serviceId: Int,
        name: String,
        price: Int,
        discount: Int,
    ) = businessRepository.editService(serviceId, name, price, discount)

    fun deleteServiceById(serviceId: Int, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val res = businessRepository.deleteServiceById(serviceId)
            callback(res)
        }
    }
}
