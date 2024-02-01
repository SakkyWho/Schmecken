package com.example.data.filters

import androidx.room.Insert
import androidx.room.Query
import com.example.data.Dish

interface FiltersDao {
    @Query(value = "SELECT * FROM filters")
    fun getAll(): List<Filters>

    @Insert
    fun insertAll(vararg filters: Filters)
    @Insert
    fun insertAllarr(filters: List<Filters>)
}