package com.example.domain

import com.example.data.filters.Filters
import com.example.data.filters.FiltersDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UrlBuilder(private val filtersDao: FiltersDao) {
    private val baseUrl = "/api/recipes/v2?type=public&co2EmissionsClass=A%2B&beta=true&random=true"

    fun build(): String {
        val filllist = filtersDao.getAll()
        return if (filllist.isNotEmpty()) {
            StringBuilder(baseUrl).apply {
                filllist[0].q?.let { append("&q=$it") }
                filllist[0].time?.let { append("&time=$it") }
                filllist[0].cuisineType?.let { append("&cuisineType%5B0%5D=$it") }
                filllist[0].mealType?.let { append("&mealType%5B0%5D=$it") }
                filllist[0].calories?.let { append("&calories=$it") }
                filllist[0].health?.let { append("&health%5B0%5D=$it") }
                filllist[0].diet?.let { append("&diet%5B0%5D=$it") }
                filllist[0].dishType?.let { append("&dishType%5B0%5D=$it") }
            }.toString()
        } else {
            baseUrl
        }
    }

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



