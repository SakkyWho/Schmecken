package com.example.domain

import android.content.Context
import androidx.room.Room
import com.example.data.ApiResponse
import com.example.data.AppDatabase
import com.example.data.Dish
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DataDomainProvider(context: Context) {

    private val rqmaker = Requestmaker()
    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "dishes"
    ).build()

    private suspend fun getDishes(): List<Dish> {
        val jsonString = rqmaker.getJson()
        val gson = Gson()
        val apiResponse = gson.fromJson(jsonString, ApiResponse::class.java)
        return apiResponse.hits.map { it.recipe }
    }

    fun fetchData() {
        GlobalScope.launch {
            db.dishDao().insertAllarr(getDishes())
        }
    }
}