package com.example.data

import com.example.data.filters.FiltersDao
import com.example.data.filters.Filters

class MyRepository(private val dishDao: DishDao, private val filterDao: FiltersDao) {
    fun getDishes(): List<Dish> {
        return dishDao.getAll()
    }

    fun getFilters(): List<Filters> {
        return filterDao.getAll()
    }
}
