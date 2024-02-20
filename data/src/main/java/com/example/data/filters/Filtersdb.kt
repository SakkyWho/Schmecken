package com.example.data.filters

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Filters::class],version = 1)
abstract class Filtersdb : RoomDatabase() {
    abstract fun filtersDao() : FiltersDao
}