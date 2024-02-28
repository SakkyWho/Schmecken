package com.example.domain

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.data.AppBitBase
import com.example.data.AppDatabase
import com.example.data.BitmapDao
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
    fun provideBitBase(@ApplicationContext context: Context): AppBitBase {
        return Room.databaseBuilder(context, AppBitBase::class.java, "bitmaps").build()
    }

    @Provides
    fun provideDishDao(database: AppDatabase): DishDao {
        return database.dishDao()
    }
    @Provides
    fun provideBitDao(database: AppBitBase): BitmapDao {
        return database.bitmapDao()
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
    private val dishDao: DishDao,
    private val bitDao: BitmapDao
) {
    private val DDprovider = DataDomainProvider(dishDao,bitDao)
    suspend fun getPreviewList(): List<SimpleDish> = withContext(Dispatchers.IO) {
        DDprovider.fetchData()
        return@withContext DDprovider.ddpPriviewList()
    }
    suspend fun ddpbasicallinfo(id: Int): secondinfo = withContext(Dispatchers.IO) {
        val basicInfo = dishDao.getBasicInfo(id)
        if (basicInfo != null) {
            Log.d("SecondInfo", "Fetched info for item id $id: $basicInfo")
            return@withContext secondinfo(
                uri = basicInfo.uri ?: "",
                label = basicInfo.label ?: "",
                image = basicInfo.image ?: "",
                images = basicInfo.images ?: emptyMap(),
                dietLabels = basicInfo.dietLabels ?: emptyList(),
                healthLabels = basicInfo.healthLabels ?: emptyList(),
                cautions = basicInfo.cautions ?: emptyList(),
                cuisinType = basicInfo.cuisinType ?: emptyList(),
                totalTime = basicInfo.totalTime ?: 0,
                mealType = basicInfo.mealType ?: emptyList(),
                dishType = basicInfo.dishType ?: emptyList()
            )
        } else {
            Log.d("SecondInfo", "No info found for item id $id")
            return@withContext secondinfo("", "", "", emptyMap(), emptyList(), emptyList(), emptyList(), emptyList(), 0, emptyList(), emptyList())
        }
    }
    suspend fun getbitmaplist(): List<bitmapdata>{
       return DDprovider.convertSimpleDishListToBitmapDataList(
           DDprovider.ddpPriviewList(),
           DDprovider.convertToBitmapdataList(bitDao.getAll())
           )
    }


}

