package com.example.domain

import com.example.data.ApiResponse
import com.example.data.DishDao
import com.example.data.Recipe
import com.example.data.filters.FiltersDao
import com.example.data.recipeToDish
import com.google.gson.Gson

class DataDomainProvider(private val dishDao: DishDao, filtersDao: FiltersDao) {

    private val rqmaker = Requestmaker(filtersDao)

    private suspend fun getResipesData(): List<Recipe> {
        val jsonString = rqmaker.getJson()
        val gson = Gson()
        val apiResponse = gson.fromJson(jsonString, ApiResponse::class.java)
        return apiResponse.hits.map { it.recipe }
    }

    suspend fun fetchData() {
        val recipes = getResipesData()
        val dishes = recipes.map(::recipeToDish)
        dishDao.insertAllarr(dishes)
    }

    fun ddpPriviewList():List<SimpleDish>{
        val allDishes = dishDao.getAll()
        val allLabelsAndImages = mutableListOf<SimpleDish>()

        for (dish in allDishes) {
            val basicInfo = dishDao.getBasicInfo(dish.id)
            val label = basicInfo.label
            val image = basicInfo.image
            allLabelsAndImages.add(SimpleDish(image, label))
        }
        return allLabelsAndImages
    }
}