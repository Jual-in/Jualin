package com.jualin.apps.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jualin.apps.data.local.entity.Category
import javax.inject.Singleton

@Singleton
@Database(
    entities = [Category::class],
    version = 1,
    exportSchema = false
)
abstract class JualinDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}