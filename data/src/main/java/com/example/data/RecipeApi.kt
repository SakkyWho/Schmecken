package com.example.data

import android.content.Context
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject


class RecipeApi {
    private val client = OkHttpClient()

    fun fetchRecipe(): String {
        val request = Request.Builder()
            //TODO нужен url builder
            .url("https://edamam-recipe-search.p.rapidapi.com/api/recipes/v2?type=public&co2EmissionsClass=A%2B&field%5B0%5D=uri&beta=true&random=true&cuisineType%5B0%5D=American&imageSize%5B0%5D=LARGE&mealType%5B0%5D=Breakfast&health%5B0%5D=alcohol-cocktail&diet%5B0%5D=balanced&dishType%5B0%5D=Biscuits%20and%20cookies")
            .get()
            .addHeader("Accept-Language", "en")
            .addHeader("X-RapidAPI-Key", "96c1521583msh4af0535795a21b9p116135jsn1ac75cb7c088")
            .addHeader("X-RapidAPI-Host", "edamam-recipe-search.p.rapidapi.com")
            .build()

        val response = client.newCall(request).execute()
        return response.body?.string() ?: ""
    }
}
class RecipeRepository(private val context: Context) {
    private val recipeApi = RecipeApi()
    private val databaseProvider = DatabaseProvider.getInstance(context)
    private val dishDao = databaseProvider.database.dishDao()

    suspend fun fetchAndSaveRecipes() {

        var recipesJsonString = recipeApi.fetchRecipe()

        val data = Json.parseToJsonElement(recipesJsonString) as JsonObject

        val hitsArray = data["hits"]!!.jsonArray

        val recipesArray = hitsArray.map { (it.jsonObject["recipe"]!!) }

        recipesJsonString = Json.encodeToString(recipesArray)

        val recipes = parseJsonToDishes(recipesJsonString)

        dishDao.insertAll(*recipes.toTypedArray())
    }
}
fun parseJsonToDishes(json: String): List<Dish> {
    val listType = object : TypeToken<List<Dish>>() {}.type
    return Gson().fromJson(json, listType)
}
