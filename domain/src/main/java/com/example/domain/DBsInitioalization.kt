package com.example.domain

import android.content.Context
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.DishDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*
class DBsInitialization(private val context: Context) {

    fun makeDishDB(): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "dishes"
        ).build()
    }


fun getDishDao(database: AppDatabase): DishDao {
    return database.dishDao()
}

    private val DDprovider = DataDomainProvider(getDishDao(makeDishDB()), filters)

    suspend fun getPreviewList(): List<SimpleDish>{
        return withContext(Dispatchers.IO) {
            DDprovider.fetchData()
            DDprovider.ddpPriviewList()
        }
    }
}

 */


