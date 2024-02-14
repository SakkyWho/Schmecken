package com.example.data.filters

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filters")
data class Filters(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @ColumnInfo(name = "q") var q: String?,
    @ColumnInfo(name = "time") var time: String?,
    @ColumnInfo(name = "cuisineType") var cuisineType: String?,
    @ColumnInfo(name = "mealType") var mealType: String?,
    @ColumnInfo(name = "calories") var calories: String?,
    @ColumnInfo(name = "health") var health: String?,
    @ColumnInfo(name = "diet") var diet: String?,
    @ColumnInfo(name = "dishType") var dishType: String?
)