package com.example.domain

import android.content.Context
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.DishDao
import com.example.data.filters.FiltersDao
import com.example.data.filters.Filtersdb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
/*
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModuleHilt {
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "dishes").build()
    }

    @Provides
    fun provideFiltersdb(@ApplicationContext context: Context): Filtersdb {
        return Room.databaseBuilder(context, Filtersdb::class.java, "filters").build()
    }

    @Provides
    fun provideDishDao(database: AppDatabase): DishDao {
        return database.dishDao()
    }

    @Provides
    fun provideFiltersDao(database: Filtersdb): FiltersDao {
        return database.filtersDao()
    }
}

 */
