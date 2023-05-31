package com.jualin.apps.data.repositories

import com.jualin.apps.data.local.entity.Business
import com.jualin.apps.utils.FakeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Singleton
class BusinessRepository {

    fun getBusinessById(id: Int): Flow<Business> = flow {
        emit(FakeData.business.first { it.id==id })
    }

}