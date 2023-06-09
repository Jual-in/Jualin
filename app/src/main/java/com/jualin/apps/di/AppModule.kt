package com.jualin.apps.di

import android.content.Context
import com.jualin.apps.data.local.preferences.UserPreferencesImpl
import com.jualin.apps.data.local.room.CategoryDao
import com.jualin.apps.data.remote.retrofit.ApiService
import com.jualin.apps.data.repositories.BusinessRepository
import com.jualin.apps.data.repositories.CategoryRepository
import com.jualin.apps.data.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideUserPreferencesDataStore(@ApplicationContext context: Context): UserPreferencesImpl {
        return UserPreferencesImpl(context)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        apiService: ApiService,
        userPreferences: UserPreferencesImpl
    ): UserRepository {
        return UserRepository(apiService, userPreferences)
    }

    @Singleton
    @Provides
    fun provideBusinessRepository(
        apiService: ApiService,
        categoryDao: CategoryDao
    ): BusinessRepository {
        return BusinessRepository(apiService, categoryDao)
    }

    @Singleton
    @Provides
    fun provideCategoryRepository(
        categoryDao: CategoryDao
    ): CategoryRepository {
        return CategoryRepository(categoryDao)
    }
}