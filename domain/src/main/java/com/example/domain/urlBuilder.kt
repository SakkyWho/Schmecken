package com.example.domain

import com.example.data.filters.Filters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UrlBuilder(private val filters: Filters) {
    private val baseUrl = "/api/recipes/v2?type=public&co2EmissionsClass=A%2B&beta=true&random=true"

    fun build(): String {
        val filllist = filters
        return  if (filllist != null){
            StringBuilder(baseUrl).apply {
                filllist.q?.let { append("&q=$it") }
                filllist.time?.let { append("&time=$it") }
                filllist.cuisineType?.let { append("&cuisineType%5B0%5D=$it") }
                filllist.mealType?.let { append("&mealType%5B0%5D=$it") }
                filllist.calories?.let { append("&calories=$it") }
                filllist.health?.let { append("&health%5B0%5D=$it") }
                filllist.diet?.let { append("&diet%5B0%5D=$it") }
                filllist.dishType?.let { append("&dishType%5B0%5D=$it") }
            }.toString()
        } else {
            baseUrl
        }
    }


}



