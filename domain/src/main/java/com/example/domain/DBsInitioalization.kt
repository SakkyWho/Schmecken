package com.example.domain

import android.content.Context
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.filters.Filtersdb


class DBsInitialization(private val context: Context) {

    fun makeDishDB(): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "dishes"
        ).build()
    }

    fun makeFiltersDB(): Filtersdb {
        return Room.databaseBuilder(
            context,
            Filtersdb::class.java, "filters"
        ).build()
    }
}


