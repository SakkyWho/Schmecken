package com.example.data

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromBasicInfo(basicInfo: BasicInfo): String {
        return gson.toJson(basicInfo)
    }

    @TypeConverter
    fun toBasicInfo(data: String): BasicInfo {
        return gson.fromJson(data, BasicInfo::class.java)
    }

    @TypeConverter
    fun fromOtherInfo(otherInfo: OtherInfo): String {
        return gson.toJson(otherInfo)
    }

    @TypeConverter
    fun toOtherInfo(data: String): OtherInfo {
        return gson.fromJson(data, OtherInfo::class.java)
    }

    @TypeConverter
    fun fromNutrition(nutrition: Nutrition): String {
        return gson.toJson(nutrition)
    }

    @TypeConverter
    fun toNutrition(data: String): Nutrition {
        return gson.fromJson(data, Nutrition::class.java)
    }
}
