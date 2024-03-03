package com.example.domain

import android.util.Log
import com.example.data.ApiResponse
import com.example.data.BitmapDao
import com.example.data.Bitmapdata
import com.example.data.DishDao
import com.example.data.Recipe
import com.example.data.recipeToDish
import com.google.gson.Gson

class DataDomainProvider(private val dishDao: DishDao, private val bitdao: BitmapDao) {

    private val rqmaker = Requestmaker()

    private suspend fun getResipesData(): List<Recipe> {
        val jsonString = rqmaker.makeJson()
        val gson = Gson()
        val apiResponse = gson.fromJson(jsonString, ApiResponse::class.java)
        return apiResponse.hits.map { it.recipe }
    }

    suspend fun fetchData() {
        val recipes = getResipesData()
        Log.d("fetchData", "Recipes data: $recipes")
        val dishes = recipes.map(::recipeToDish)
        dishDao.insertAllarr(dishes)
    }

    fun ddpPriviewList(start: Int, count: Int): List<SimpleDish> {
        val allDishes = dishDao.getAll().subList(start, start + count)
        val allLabelsAndImages = mutableListOf<SimpleDish>()

        for (dish in allDishes) {
            val basicInfo = dishDao.getBasicInfo(dish.id)
            val label = basicInfo.label
            val image = basicInfo.image
            val id = dish.id
            allLabelsAndImages.add(SimpleDish(image, label, id))
        }
        return allLabelsAndImages
    }

    //bitmap block
    fun saveBitmapDB(domeindata: domeindata) {
        val convertedBitmapdata = Bitmapdata(
            id = domeindata.id,
            imageBitmap = domeindata.imageBitmap,
            isLiked = domeindata.isLiked
        )
        Log.d("toupdate3","${convertedBitmapdata}")
        bitdao.updateBitmap(convertedBitmapdata)
    }
    fun convertToBitmapData(domeindata: domeindata): Bitmapdata {
        return Bitmapdata(
            id = domeindata.id,
            imageBitmap = domeindata.imageBitmap,
            isLiked = domeindata.isLiked
        )
    }

    fun convertToBitmapDomeinList(bitmapDataList: List<Bitmapdata>): List<domeindata> {
        Log.d("toupdate4","${bitmapDataList}")
        val domeindataList = mutableListOf<domeindata>()

        for (bitmapData in bitmapDataList) {
            val imageBitmap = bitmapData.imageBitmap ?: ByteArray(0)
            val domeindata = domeindata(id = bitmapData.id, imageBitmap = imageBitmap)
            domeindataList.add(domeindata)
        }

        return domeindataList
    }


}