package com.example.domain.logic

import android.util.Log
import com.example.data.api.RecipeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class Requestmaker() {
    internal suspend fun makeJson(): String {
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