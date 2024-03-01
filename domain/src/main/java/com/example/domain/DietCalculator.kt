package com.example.domain

import androidx.lifecycle.LiveData
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

    fun recommendDiet(dishes: List<FourthInfo>): List<FourthInfo> {
        val bmr = calculateBMR()
        val eligibleDishes = dishes.filter { it.calories >= 10 }
        var recommendedDishes = listOf<FourthInfo>()
        var totalCalories = 0.0

        while (recommendedDishes.size < 5) {
            recommendedDishes = eligibleDishes.shuffled().take(5)
            totalCalories = recommendedDishes.sumByDouble { it.calories }

            if (totalCalories > bmr + 100 || totalCalories < bmr - 100) {
                recommendedDishes = listOf()
            } else {
                val threeDishCombos = recommendedDishes.combinations(3)
                for (combo in threeDishCombos) {
                    val comboCalories = combo.sumByDouble { it.calories }
                    if (comboCalories >= bmr - 100 && comboCalories <= bmr + 100) {
                        return combo
                    }
                }
            }
        }

        return recommendedDishes
    }

}
fun <T> List<T>.combinations(n: Int): List<List<T>> {
    if (n == 0) return listOf(emptyList())
    if (isEmpty()) return emptyList()
    val element = first()
    val rest = drop(1)
    val withoutElement = rest.combinations(n)
    val withElement = rest.combinations(n - 1).map { combination -> combination + element }
    return withoutElement + withElement
}
