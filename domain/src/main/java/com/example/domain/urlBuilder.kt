package com.example.domain

import android.util.Log
import com.example.data.filters.Filters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
//интересная не реализованная идея
class UrlBuilder(private val filters: Filters) {
    private val baseUrl = "/api/recipes/v2?type=public&co2EmissionsClass=A%2B&beta=true&random=true"

    fun build(): String {
        return StringBuilder(baseUrl).apply {
            filters.q?.let { append("&q=$it") }
            filters.time?.let { append("&time=$it") }
            filters.cuisineType?.let { append("&cuisineType%5B0%5D=$it") }
            filters.mealType?.let { append("&mealType%5B0%5D=$it") }
            filters.calories?.let { append("&calories=$it") }
            filters.health?.let { append("&health%5B0%5D=$it") }
            filters.diet?.let { append("&diet%5B0%5D=$it") }
            filters.dishType?.let { append("&dishType%5B0%5D=$it") }
        }.toString()
    }
}




