package com.example.domain

import androidx.lifecycle.ViewModel
import com.example.data.MyRepository

class SharedViewModel(private val repository: MyRepository) : ViewModel() {
    val dishes = repository.getDishes()
    val filters = repository.getFilters()
}

