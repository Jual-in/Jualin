package com.jualin.apps.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jualin.apps.data.Result
import com.jualin.apps.data.remote.response.umkm.AddUMKMResponse
import com.jualin.apps.data.repositories.BusinessRepository
import com.jualin.apps.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UmkmViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val businessRepository: BusinessRepository
) : ViewModel() {

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

}