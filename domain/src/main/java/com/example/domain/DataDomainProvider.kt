package com.example.domain

import android.graphics.Bitmap
import android.util.Log
import com.example.data.ApiResponse
import com.example.data.DishDao
import com.example.data.Recipe
import com.example.data.filters.Filters
import com.example.data.recipeToDish
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class DataDomainProvider(private val dishDao: DishDao) {

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
/*
    suspend fun saveBitmap(bitmapdata: Bitmapdata) {
        BitmapDao.insert(bitmapdata)
    }
    suspend fun loadBitmapDataWithPicasso(id: Int, url: String): bitmapdata {
        val bitmap = withContext(Dispatchers.IO) {
            var bitmap: Bitmap? = null
            try {
                bitmap = Picasso.get().load(url).get()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            bitmap
        }

        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()

        return bitmapdata(id = id, imageBitmap = byteArray)
    }
    suspend fun convertSimpleDishListToBitmapDataList(simpleDishList: List<SimpleDish>, existingBitmapDataList: List<bitmapdata>): List<bitmapdata> {
        val bitmapDataList = existingBitmapDataList.toMutableList()

        for (simpleDish in simpleDishList) {
            if (!bitmapDataList.any { it.id == simpleDish.id }) {
                simpleDish.image?.let { url ->
                    val bitmapData = loadBitmapDataWithPicasso(simpleDish.id, url)
                    bitmapDataList.add(bitmapData)
                }
            }
        }

        return bitmapDataList
    }

    fun convertToBitmapdataList(bitmapDataList: List<Bitmapdata>): List<bitmapdata> {
        val bitmapdataList = mutableListOf<bitmapdata>()

        for (bitmapData in bitmapDataList) {
            val imageBitmap = bitmapData.imageBitmap ?: ByteArray(0)
            val bitmapdata = bitmapdata(id = bitmapData.id, imageBitmap = imageBitmap)
            bitmapdataList.add(bitmapdata)
        }

        return bitmapdataList
    }

 */


}