package com.example.domain

import android.content.Context
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.DishDao
import com.example.data.filters.FiltersDao
import com.example.data.filters.Filtersdb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


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

    fun makeFiltersDB(): Filtersdb {
        return Room.databaseBuilder(
            context,
            Filtersdb::class.java, "filters"
        ).build()
    }
    fun getFiltersDao(database: Filtersdb): FiltersDao {
        return database.filtersDao()
    }

    private val DDprovider = DataDomainProvider(getDishDao(makeDishDB()), getFiltersDao(makeFiltersDB()))

    suspend fun getPreviewList(): List<SimpleDish>{
        return withContext(Dispatchers.IO) {
            DDprovider.fetchData()
            DDprovider.ddpPriviewList()
        }
    }
}


