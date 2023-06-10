package com.jualin.apps.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jualin.apps.data.Result
import com.jualin.apps.data.remote.response.nearby.NearbyUmkmResponseItem
import com.jualin.apps.data.repositories.BusinessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NearbyViewModel @Inject constructor(
    private val businessRepository: BusinessRepository,
): ViewModel() {

    fun getNearbyUmkm(
        lat: Double?,
        lng: Double?
    ): LiveData<Result<List<NearbyUmkmResponseItem>>> {
        return businessRepository.getUmkmNearby(lat, lng)
    }

}