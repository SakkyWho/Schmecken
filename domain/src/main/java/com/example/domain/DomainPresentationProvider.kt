package com.example.domain

import com.example.data.DishDao
import com.example.data.filters.FiltersDao


class DomainPresentationProvider(dishDao: DishDao, filtersDao: FiltersDao) {
    private val DDprovider = DataDomainProvider(dishDao, filtersDao)

    suspend fun getPreviewList(): List<SimpleDish>{
        DDprovider.fetchData()
        return DDprovider.ddpPriviewList()
    }
}