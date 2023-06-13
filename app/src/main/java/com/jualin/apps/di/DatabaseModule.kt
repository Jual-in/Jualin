package com.jualin.apps.di

import android.app.Application
import androidx.room.Room
import com.jualin.apps.data.local.room.CategoryDao
import com.jualin.apps.data.local.room.JualinDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        application: Application,
        provider: Provider<CategoryDao>
    ): JualinDatabase {
        return Room.databaseBuilder(application, JualinDatabase::class.java, "jualin.db")
            .fallbackToDestructiveMigration()
            .addCallback(JualinDatabaseCallback(provider))
            .build()
    }

    @Provides
    fun provideCategoryDao(database: JualinDatabase) = database.categoryDao()

}