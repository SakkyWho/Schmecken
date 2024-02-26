package com.example.domain

import android.content.Context
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.DishDao
import com.example.data.filters.Filters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

//куда это вставить?
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "dishes").build()
    }

    @Provides
    fun provideDishDao(database: AppDatabase): DishDao {
        return database.dishDao()
    }
    @Provides
    fun provideFilters(): Filters {
        return Filters(
            q = null,
            time = null,
            cuisineType = null,
            mealType = null,
            calories = null,
            health = null,
            diet = null,
            dishType = null
        )
    }
}

class DomainPresentationProvider @Inject constructor(
    private val dishDao: DishDao
) {
    private val DDprovider = DataDomainProvider(dishDao)

    suspend fun getPreviewList(): List<SimpleDish> = withContext(Dispatchers.IO) {
        DDprovider.fetchData()
        return@withContext DDprovider.ddpPriviewList()
    }
}

