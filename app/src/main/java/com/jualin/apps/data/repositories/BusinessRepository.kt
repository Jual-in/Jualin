package com.jualin.apps.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.jualin.apps.data.Result
import com.jualin.apps.data.local.entity.Business
import com.jualin.apps.data.local.entity.Product
import com.jualin.apps.data.local.entity.Service
import com.jualin.apps.data.local.room.CategoryDao
import com.jualin.apps.data.remote.retrofit.ApiService
import com.jualin.apps.utils.reduceFileImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BusinessRepository @Inject constructor(
    private val apiService: ApiService,
    private val categoryDao: CategoryDao,
) {

    fun getBusinessById(id: Int): LiveData<Result<Business>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getBusinessById(id)
            val business = Business(
                id = response.id,
                name = response.name,
                description = response.description,
                category = response.category,
                phone = response.phone,
                latitude = response.latitude,
                longitude = response.longitude,
            )
            emit(Result.Success(business))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    fun getRecommendedBusiness(): LiveData<Result<List<Business>>> = liveData {
        emit(Result.Loading)
        try {
            val selectedCategory = categoryDao.getSelectedCategory().joinToString(separator = ",")
            val response = apiService.getRecommendedBusiness(selectedCategory)
            val businesses = response.map {
                Business(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    category = it.category,
                    phone = it.phone,
                    latitude = it.latitude,
                    longitude = it.longitude,
                )
            }
            emit(Result.Success(businesses))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    fun searchProduct(query: String): LiveData<Result<List<Product>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.searchProduct(query)
            val products = response.map {
                Product(
                    id = it.id,
                    name = it.name,
                    price = it.price,
                    discount = it.discount,
                    photoUrl = it.photoUrl ?: "",
                    businessId = it.businessId,
                )
            }
            emit(Result.Success(products))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    fun searchService(query: String): LiveData<Result<List<Service>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.searchService(query)
            val services = response.map {
                Service(
                    id = it.id,
                    name = it.name,
                    price = it.price,
                    discount = it.discount,
                    businessId = it.businessId,
                )
            }
            emit(Result.Success(services))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    fun predictImage(image: File): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            val reducedImage = reduceFileImage(image)
            val requestImageFile = reducedImage.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "imagefile",
                image.name,
                requestImageFile
            )

            val response = apiService.predictImage(imageMultipart)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    fun getUmkmNearby(
        latitude: Double?,
        longitude: Double?,
    ): LiveData<Result<List<Business>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getNearbyUMKM(latitude, longitude)
            val businesses = response.map {
                Business(
                    id = it.id,
                    name = it.namaUsaha,
                    description = it.deskripsi,
                    category = it.kategori,
                    phone = it.noHp,
                    latitude = it.latitude,
                    longitude = it.longitude,
                )
            }
            emit(Result.Success(businesses))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    fun getProductsByBusinessId(businessId: Int): LiveData<Result<List<Product>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getProductsByBusinessId(businessId)
            val products = response.map {
                Product(
                    id = it.id,
                    name = it.name,
                    price = it.price,
                    discount = it.discount,
                    photoUrl = it.photoUrl ?: "",
                )
            }
            emit(Result.Success(products))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    fun getServicesByBusinessId(businessId: Int): LiveData<Result<List<Service>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getServicesByBusinessId(businessId)
            val services = response.map {
                Service(
                    id = it.id,
                    name = it.name,
                    price = it.price,
                    discount = it.discount,
                )
            }
            emit(Result.Success(services))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    fun addProduct(
        businessId: Int,
        name: String,
        price: Int,
        discount: Int,
        photo: File,
    ): LiveData<Result<Boolean>> = liveData {
        emit(Result.Loading)
        try {
            val requestImageFile = photo.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "Photo",
                photo.name,
                requestImageFile
            )
            val rbName = name.toRequestBody("text/plain".toMediaTypeOrNull())

            apiService.addProduct(
                businessId = businessId,
                name = rbName,
                price = price,
                discount = discount,
                photo = imageMultipart,
            )
            emit(Result.Success(true))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    fun editProduct(
        productId: Int,
        name: String,
        price: Int,
        discount: Int,
        photo: File?,
    ): LiveData<Result<Boolean>> = liveData {
        emit(Result.Loading)
        try {
            val rbName = name.toRequestBody("text/plain".toMediaTypeOrNull())

            if (photo!=null) {
                val requestImageFile = photo.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "Photo",
                    photo.name,
                    requestImageFile
                )
                apiService.editProduct(
                    productId = productId,
                    name = rbName,
                    price = price,
                    discount = discount,
                    photo = imageMultipart,
                )
            } else {
                apiService.editProduct(
                    productId = productId,
                    name = rbName,
                    price = price,
                    discount = discount,
                )
            }
            emit(Result.Success(true))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    suspend fun deleteProductById(productId: Int): Boolean {
        return try {
            apiService.deleteProduct(productId)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun addService(
        businessId: Int,
        name: String,
        price: Int,
        discount: Int,
    ): LiveData<Result<Boolean>> = liveData {
        emit(Result.Loading)
        try {
            apiService.addService(
                businessId = businessId,
                name = name,
                price = price,
                discount = discount,
            )
            emit(Result.Success(true))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    fun editService(
        serviceId: Int,
        name: String,
        price: Int,
        discount: Int,
    ): LiveData<Result<Boolean>> = liveData {
        emit(Result.Loading)
        try {
            apiService.editService(
                serviceId = serviceId,
                name = name,
                price = price,
                discount = discount,
            )
            emit(Result.Success(true))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    suspend fun deleteServiceById(serviceId: Int): Boolean {
        return try {
            apiService.deleteService(serviceId)
            true
        } catch (e: Exception) {
            false
        }
    }
}
