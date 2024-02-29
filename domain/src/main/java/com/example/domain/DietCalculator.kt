package com.example.domain

import com.example.data.Dish

class DietCalculator(
    private val age: Int,
    private val gender: String,
    private val weight: Double,
    private val height: Double
) {
    fun calculateBMR(): Double {
        return when (gender.toLowerCase()) {
            "male" -> 66 + (6.23 * weight) + (12.7 * height) - (6.8 * age)
            "female" -> 655 + (4.35 * weight) + (4.7 * height) - (4.7 * age)
            else -> 0.0
        }
    }

    fun recommendDiet(dishes: List<Dish>): List<Dish> {
        val bmr = calculateBMR()
        // Сортировка блюд по калорийности, начиная с наименее калорийных
        return dishes.sortedBy { it.nutrition?.calories ?: 0.0 }
            .filter { it.nutrition?.calories ?: 0.0 <= bmr }
    }
}
