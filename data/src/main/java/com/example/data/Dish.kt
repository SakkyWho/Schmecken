package com.example.data

import android.health.connect.datatypes.MealType
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dish(
    @PrimaryKey val Uri: Int,
    @ColumnInfo(name = "label") val label: String?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "source") val source: String?,
    @ColumnInfo(name = "Url") val Url: String?,
    @ColumnInfo(name = "shareas") val shareas: String?,
    @ColumnInfo(name = "dietLabels") val dietLabels: Array<String>?,
    @ColumnInfo(name = "healthLabels") val healthLabels: Array<String>?,
    @ColumnInfo(name = "cautions") val cautions: String?,
    @ColumnInfo(name = "ingredientLines") val ingredientLines: Array<String>?,
    @ColumnInfo(name = "ingredient") val ingredient: Array<String>?,
    @ColumnInfo(name = "calories") val calories: Int?,
    @ColumnInfo(name = "totalWeight") val totalWeight: Int?,
    @ColumnInfo(name = "Totaltime") val Totaltime: String?,
    @ColumnInfo(name = "cousinType") val cousinType: String?,
    @ColumnInfo(name = "mealType") val mealType: String?,
    @ColumnInfo(name = "dishType") val dishType: String?,
    @ColumnInfo(name = "totalNutrients") val totalNutrients: Array<String>?,
    @ColumnInfo(name = "totalDaily") val totalDaily: Array<String>?,
    @ColumnInfo(name = "digest") val digest: String?
    ) {


}