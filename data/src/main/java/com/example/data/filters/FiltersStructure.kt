package com.example.data.filters

data class Filters(
    val id : Int = 0,
    var q: String?,
    var time: String?,
    var cuisineType: String?,
    var mealType: String?,
    var calories: String?,
    var health: String?,
    var diet: String?,
    var dishType: String?
)