package com.example.domain.logic

import android.util.Log
import com.example.data.api.ApiResponse
import com.example.data.database.bit.BitmapDao
import com.example.data.database.bit.Bitmapdata
import com.example.data.database.dish.DishDao
import com.example.data.api.Recipe
import com.example.data.converters.recipeToDish
import com.example.domain.dataclasses.SimpleDish
import com.example.domain.dataclasses.domeindata
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        val convertedBitmapdata = convertToBitmapData(domeindata)
        CoroutineScope(Dispatchers.IO).launch {
            bitdao.upsert(convertedBitmapdata)
        }
    }


    fun convertToBitmapData(domeindata: domeindata): Bitmapdata {
        return Bitmapdata(
            id = domeindata.id,
            imageBitmap = domeindata.imageBitmap,
            isLiked = domeindata.isLiked,
            label = domeindata.label
        )
    }

    fun convertToBitmapDomeinList(bitmapDataList: List<Bitmapdata>): List<domeindata> {
        return bitmapDataList.map { bitmapData ->
            val imageBitmap = bitmapData.imageBitmap ?: ByteArray(0)
            domeindata(id = bitmapData.id, imageBitmap = imageBitmap, isLiked = bitmapData.isLiked, label = bitmapData.label)
        }
    }




}