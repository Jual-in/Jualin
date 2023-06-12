package com.jualin.apps.di

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jualin.apps.data.local.entity.Category
import com.jualin.apps.data.local.room.CategoryDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider

class JualinDatabaseCallback(
    private val provider: Provider<CategoryDao>
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        val categories: List<Category> = listOf(
            Category(1, "Desain"),
            Category(2, "Elektronik"),
            Category(3, "Fashion"),
            Category(4, "Fotografi"),
            Category(5, "Kecantikan"),
            Category(6, "Kelontong"),
            Category(7, "Kesehatan"),
            Category(8, "Kuliner"),
            Category(9, "Lain-Lain"),
            Category(10, "Otomotif"),
            Category(11, "Penerbitan"),
            Category(12, "Percetakan"),
            Category(13, "Perikanan"),
            Category(14, "Periklanan"),
            Category(15, "Pertambangan"),
            Category(16, "Pertanian"),
            Category(17, "Peternakan"),
            Category(18, "Properti"),
            Category(19, "Seni"),
            Category(20, "Teknologi"),
            Category(21, "Travel")
        )

        CoroutineScope(Dispatchers.IO).launch {
            provider.get().insertCategory(categories)
        }
    }

}