package com.example.domain

import com.example.data.AppDatabase
import com.example.data.DishDao
import com.example.data.filters.FiltersDao
import com.example.data.filters.Filtersdb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

//куда это вставить?
/* @Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideDishDao(database: AppDatabase): DishDao {
        return database.dishDao()
    }

    @Provides
    fun provideFiltersDao(database: Filtersdb): FiltersDao {
        return database.filtersDao()
    }
}

class DomainPresentationProvider @Inject constructor(
    private val dishDao: DishDao,
    private val filtersDao: FiltersDao
) {
    private val DDprovider = DataDomainProvider(dishDao, filtersDao)

    suspend fun getPreviewList(): List<SimpleDish>{
        DDprovider.fetchData()
        return DDprovider.ddpPriviewList()
    }
}

 */