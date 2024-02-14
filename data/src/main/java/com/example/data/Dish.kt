package com.example.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "dishes")
data class Dish(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @ColumnInfo(name = "basicinfo") val basicinfo :BasicInfo,
    @ColumnInfo(name = "otherinfo") val otherinfo: OtherInfo,
    @ColumnInfo(name = "nutrition") val nutrition: Nutrition
    )
data class BasicInfo(
    @SerializedName("uri")
    val uri: String?,
    @SerializedName("label")
    val label: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("images")
    val images: Map<String,dishImage>?,
    @SerializedName("dietLabels")
    val dietLabels: List<String>?,
    @SerializedName("healthlabels")
    val healthLabels: List<String>?,
    @SerializedName("cautions")
    val cautions: List<String>?,
    @SerializedName("cuisineType")
    val cuisinType: List<String>?,
    @SerializedName("totalTime")
    val totalTime: Int?,
    @SerializedName("mealType")
    val mealType: List<String>?,
    @SerializedName("dishType")
    val dishType: List<String>?,
)
data class OtherInfo(
    @SerializedName("source")
    val source: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("shareAs")
    val shareas: String?,
    @SerializedName("yield")
    val yield: Int?,
    @SerializedName("ingredientLines")
    val ingredientLines: List<String>?,
    @SerializedName("ingredients")
    val ingredients: List<dishIngredient>?,
    @SerializedName("totalTime")
    val totalTime: Int?,
    @SerializedName("digest")
    val digest: List<dishDigest>?,
    )

data class dishSub(
    val daily: Double?,
    val hasRDI: Boolean?,
    val label: String?,
    val schemaOrgTag: String?,
    val tag: String?,
    val total: Double?,
    val unit: String?
)


data class dishDigest(
    val label : String?,
    val tag: String?,
    val schemaOrgTag: String?,
    val total: Double?,
    val hasRDI: Boolean?,
    val daily: Double?,
    val unit: String?,
    val sub: List<dishSub>?
)

data class dishIngredient(
    val foodCategory: String,
    val foodId: String,
    val image: String,
    val text: String,
    val weight: Double
)


data class dishImage(
    val url: String,
    val width: Int,
    val height: Int
)

data class Nutrition(
    @SerializedName("calories")
    val calories: Double?,
    @SerializedName("totalWeight")
    val totalWeight: Double?,
    @SerializedName("totalDaily")
    val totalDaily: Map<String, Nutrient>,
    @SerializedName("totalNutrients")
    val nutrients: Map<String,Nutrient>,
)

data class Nutrient(
    val name: String,
    val quantity: Double,
    val unit: String
)

