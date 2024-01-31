package com.example.domain

import com.example.data.RecipeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Requestmaker {

    internal suspend fun getJson(): String {
        return suspendCoroutine { continuation ->
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val recipeApi = RecipeApi()
                    val jsonString = recipeApi.getJson()
                    continuation.resume(jsonString)
                } catch (e: Exception) {
                    continuation.resume("ex")
                }
            }
        }
    }

}