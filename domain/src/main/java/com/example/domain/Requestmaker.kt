package com.example.domain

import android.util.Log
import com.example.data.RecipeApi
import com.example.data.filters.FiltersDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class Requestmaker(filtersDao: FiltersDao) {
private val urlBuilder = UrlBuilder(filtersDao)
    internal suspend fun getJson(): String {
        try {
        return suspendCoroutine { continuation ->
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val recipeApi = RecipeApi()
                    val jsonString = recipeApi.getJson()
                    continuation.resume(jsonString)
                    Log.d("MyTag", "Сообщение 1")
                } catch (e: Exception) {
                    continuation.resume("ex")
                    Log.d("MyTag", "ошибка 1")
                }
            }
        }
        }catch (e:Exception){
           return "help"
        }
    }
}