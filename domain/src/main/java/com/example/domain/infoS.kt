package com.example.domain

import com.example.data.dishImage

data class secondinfo(
    val uri: String,
    val label: String,
    val image: String,
    val images: Map<String, dishImage?>,
    val dietLabels: List<String>,
    val healthLabels: List<String>,
    val cautions: List<String>,
    val cuisinType: List<String>,
    val totalTime: Int,
    val mealType: List<String>,
    val dishType: List<String>,
)
data class RecycIngr(val imageUrl: String, val label: String, val weight: String)
data class DigIngr(val digest: String, val value: Double)

data class ThirdInfo(
    val recycIngrList: List<RecycIngr>,
    val digIngrList: List<DigIngr>
)
