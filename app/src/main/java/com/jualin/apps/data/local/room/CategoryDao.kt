package com.jualin.apps.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jualin.apps.data.local.entity.Category

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: List<Category>)

    @Query("SELECT * FROM categories")
    suspend fun getAllCategory(): List<Category>

    @Query("SELECT name FROM categories WHERE isChecked = 1")
    suspend fun getSelectedCategory(): List<String>

    @Query("UPDATE categories SET isChecked = 1 WHERE id IN (:categoryIds)")
    suspend fun updateCategories(categoryIds: List<Int>)

    @Query("SELECT COUNT(*) FROM categories WHERE isChecked = 1")
    suspend fun hasCategory(): Int
}