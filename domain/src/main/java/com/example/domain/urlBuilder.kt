package com.example.domain

import android.content.Context
import androidx.room.Room
import com.example.data.filters.Filters
import com.example.data.filters.Filtersdb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class urlBuilder(context: Context) {
    private val baseUrl = "https://edamam-recipe-search.p.rapidapi.com/api/recipes/v2?type=public&co2EmissionsClass=A%2B&beta=true&random=true"

    fun build(): String {
        filllist = db.filtersDao().getAll()
        return StringBuilder(baseUrl).apply {
            filllist!![0].q?.let { append("&q=$it") }
            filllist!![0].time?.let { append("&time=$it") }
            filllist!![0].cuisineType?.let { append("&cuisineType%5B0%5D=$it") }
            filllist!![0].mealType?.let { append("&mealType%5B0%5D=$it") }
            filllist!![0].calories?.let { append("&calories=$it") }
            filllist!![0].health?.let { append("&health%5B0%5D=$it") }
            filllist!![0].diet?.let { append("&diet%5B0%5D=$it") }
            filllist!![0].dishType?.let { append("&dishType%5B0%5D=$it") }
        }.toString()
    }

    private val db: Filtersdb = Room.databaseBuilder(
        context.applicationContext,
        Filtersdb::class.java, "filters_database"
    ).build()

    private val filtersDao = db.filtersDao()
    var filllist : List<Filters>? = null

    suspend fun getAllFilters(): List<Filters> {
        return withContext(Dispatchers.IO) {
            filtersDao.getAll()
        }
    }

    suspend fun insertAllFilters(filters: List<Filters>) {
        withContext(Dispatchers.IO) {
            filtersDao.insertAllarr(filters)
        }
    }
}


