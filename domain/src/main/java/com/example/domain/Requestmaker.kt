package com.example.domain

import com.example.data.RecipeApi
import com.example.schmecken.presentation.viewmodel.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class Requestmaker {
val urlBuilder = urlBuilder(MainActivity.context)
    internal suspend fun getJson(): String {
        urlBuilder
        val url = urlBuilder.build()
        return suspendCoroutine { continuation ->
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val recipeApi = RecipeApi()
                    val jsonString = recipeApi.getJson(url)
                    continuation.resume(jsonString)
                } catch (e: Exception) {
                    continuation.resume("ex")
                }
            }
        }
    }

}