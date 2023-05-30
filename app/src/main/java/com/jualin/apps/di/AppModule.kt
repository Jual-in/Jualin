package com.jualin.apps.di

import com.jualin.apps.data.repositories.Repository
import com.jualin.apps.data.remote.retrofit.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        apiService: ApiService
    ): Repository {
        return Repository(apiService)
    }

}