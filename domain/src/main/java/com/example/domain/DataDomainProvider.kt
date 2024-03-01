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
    fun saveBitmapDB(bitmapdata: bitmapdata) {
        val convertedBitmapdata = Bitmapdata(
            id = bitmapdata.id,
            imageBitmap = bitmapdata.imageBitmap,
            isLiked = bitmapdata.isLiked
        )
        bitdao.updateBitmap(convertedBitmapdata)
    }

    fun convertToBitmapDomeinList(bitmapDataList: List<Bitmapdata>): List<bitmapdata> {
        val bitmapdataList = mutableListOf<bitmapdata>()

        for (bitmapData in bitmapDataList) {
            val imageBitmap = bitmapData.imageBitmap ?: ByteArray(0)
            val bitmapdata = bitmapdata(id = bitmapData.id, imageBitmap = imageBitmap)
            bitmapdataList.add(bitmapdata)
        }

        return bitmapdataList
    }


}