package com.jualin.apps.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.jualin.apps.data.Result
import com.jualin.apps.data.local.entity.Business
import com.jualin.apps.data.local.entity.Product
import com.jualin.apps.data.remote.retrofit.ApiService
import com.jualin.apps.utils.FakeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BusinessRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun getBusinessById(id: Int): Flow<Business> = flow {
        emit(FakeData.business.first { it.id==id })
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
                )
            }
            emit(Result.Success(products))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }
}
