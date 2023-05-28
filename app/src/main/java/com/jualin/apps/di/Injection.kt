package com.jualin.apps.di

import android.content.Context
import com.jualin.apps.data.Repository
import com.jualin.apps.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(Context: Context) : Repository {
        val apiService = ApiConfig.getApiService()
        return Repository(apiService)
    }
}