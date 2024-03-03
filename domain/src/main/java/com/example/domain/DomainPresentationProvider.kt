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
    private val DDprovider = DataDomainProvider(dishDao, bitDao)
    suspend fun getPreviewList(start: Int, count: Int): List<SimpleDish> = withContext(Dispatchers.IO) {
        val allDishes = dishDao.getAll()
        if (allDishes.size <= start + count) {
            DDprovider.fetchData()
        }
        return@withContext DDprovider.ddpPriviewList(start, count)
    }

    suspend fun ddpbasicallinfo(id: Int): secondinfo = withContext(Dispatchers.IO) {
        val basicInfo = dishDao.getBasicInfo(id)
        if (basicInfo != null) {
            val secondInfo = secondinfo(
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
            return@withContext secondInfo
        } else {
            return@withContext secondinfo("", "", "", emptyMap(), emptyList(), emptyList(), emptyList(), emptyList(), 0, emptyList(), emptyList())
        }
    }
    suspend fun upsertBitBase(domeindata: domeindata) {
        val bitmapData = DDprovider.convertToBitmapData(domeindata)
        bitDao.upsert(bitmapData)
    }

    suspend fun getBitmapList(): List<domeindata> {
        val bitmapDataList = bitDao.getAll()
        Log.d("???","${bitmapDataList}")
        return DDprovider.convertToBitmapDomeinList(bitmapDataList)
    }



    suspend fun getThirdInfo(id: Int): ThirdInfo = withContext(Dispatchers.IO) {
        val otherInfo = dishDao.getOtherInfo(id)
        if (otherInfo != null) {
            val recycIngrList = otherInfo.ingredients?.map { dishIngredient ->
                RecycIngr(
                    imageUrl = dishIngredient?.image ?: "",
                    label = dishIngredient?.text ?: "",
                    weight = dishIngredient?.weight?.toString() ?: ""
                )
            } ?: emptyList()

            val digIngrList = otherInfo.digest?.map { dishDigest ->
                DigIngr(
                    digest = dishDigest?.label ?: "",
                    value = dishDigest?.total ?: 0.0
                )
            } ?: emptyList()

            return@withContext ThirdInfo(
                recycIngrList = recycIngrList,
                digIngrList = digIngrList
            )
        } else {
            return@withContext ThirdInfo(emptyList(), emptyList())
        }
    }

    suspend fun getdiet(age : Int, gendger: String, weight: Double, height: Double): List<FourthInfo> {
        val dietclass = DietCalculator(age, gendger, weight, height)

        val allDishes = dishDao.getAll()

        val fourthInfoDataList = allDishes.map { dish ->
            FourthInfo(
                Dishid = dish.id,
                imageUrl = dish.basicinfo.image ?: "",
                label = dish.basicinfo.label ?: "",
                calories = dish.nutrition.calories ?: 0.0
            )
        }

        val result = dietclass.recommendDiet(fourthInfoDataList)

        return result
    }



}

