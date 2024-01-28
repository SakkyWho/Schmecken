package com.example.data

import android.health.connect.datatypes.MealType
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dish(
    @PrimaryKey val id : Int,
    @ColumnInfo(name = "Uri") val Uri: String?,
    @ColumnInfo(name = "label") val label: String?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "images") val images: Array<Array<String>>?,
    @ColumnInfo(name = "source") val source: String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "shareas") val shareas: String?,
    @ColumnInfo(name = "yield") val yield: Int?,
    @ColumnInfo(name = "dietLabels") val dietLabels: Array<String>?,
    @ColumnInfo(name = "healthLabels") val healthLabels: Array<String>?,
    @ColumnInfo(name = "cautions") val cautions: Array<String>?,
    @ColumnInfo(name = "ingredientLines") val ingredientLines: Array<String>?,
    @ColumnInfo(name = "ingredient") val ingredient: Array<Array<String>>?,
    @ColumnInfo(name = "calories") val calories: Double?,
    @ColumnInfo(name = "totalWeight") val totalWeight: Double?,
    @ColumnInfo(name = "totalTime") val totalTime: Int?,
    @ColumnInfo(name = "cousinType") val cousinType: Array<String>?,
    @ColumnInfo(name = "mealType") val mealType: Array<String>?,
    @ColumnInfo(name = "dishType") val dishType: Array<String>?,
    @ColumnInfo(name = "totalNutrients") val totalNutrients: Array<Array<Triple<String, Double, String>>>?,
    @ColumnInfo(name = "totalDaily") val totalDaily: Array<Array<Triple<String, Double, String>>>?,
    @ColumnInfo(name = "digest") val digest: Array<Array<String>>?
    ) {


}