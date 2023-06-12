package com.jualin.apps.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.jualin.apps.data.Result
import com.jualin.apps.data.local.entity.Category
import com.jualin.apps.data.local.room.CategoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {

    fun hasCategories(): Flow<Boolean> = flow {
        try {
            val count = withContext(Dispatchers.IO) {
                categoryDao.hasCategory()
            }
            if (count < 3) {
                emit(false)
            } else {
                emit(true)
            }
        } catch (e: Exception) {
            Log.d("hasCategories", e.message.toString())
            emit(false)
        }
    }

    fun getAllCategories(): LiveData<Result<List<Category>>> = liveData {
        emit(Result.Loading)
        try {
            val categories = withContext(Dispatchers.IO) {
                categoryDao.getAllCategory()
            }
            emit(Result.Success(categories))
        } catch (e: Exception) {
            Log.d("CategoryRepository", "getAllCategories: ${e.message}")
            emit(Result.Error(e.message))
        }
    }

    fun selectCategories(categoryIds: List<Int>): LiveData<Result<Boolean>> = liveData {
        emit(Result.Loading)
        try {
            Log.d("TAG", "selectCategories: $categoryIds")
            withContext(Dispatchers.IO) {
                categoryDao.updateCategories(categoryIds)
            }
            emit(Result.Success(true))
        } catch (e: Exception) {
            Log.d("CategoryRepository", "selectCategories: ${e.message}")
            emit(Result.Error(e.message))
        }
    }

}