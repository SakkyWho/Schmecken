package com.example.schmecken.di

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2

import com.example.domain.secondinfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SharedViewModel @Inject constructor() : ViewModel() {
    val viewPager: MutableLiveData<ViewPager2> = MutableLiveData()
    val secondInfo: MutableLiveData<secondinfo> = MutableLiveData()
    val selectedItemId: MutableLiveData<Int> = MutableLiveData()
    val selectedItemLabel: MutableLiveData<String> = MutableLiveData()
    val switchFragmentCommand: MutableLiveData<Int> = MutableLiveData()
    private val _gender = MutableLiveData<String>()
    val gender: LiveData<String> get() = _gender

    private val _age = MutableLiveData<Int>()
    val age: LiveData<Int> get() = _age

    private val _height = MutableLiveData<Double>()
    val height: LiveData<Double> get() = _height

    private val _weight = MutableLiveData<Double>()
    val weight: LiveData<Double> get() = _weight

    fun setGender(gender: String) {
        _gender.value = gender
    }

    fun setAge(age: Int) {
        _age.value = age
    }

    fun setHeight(height: Double) {
        _height.value = height
    }

    fun setWeight(weight: Double) {
        _weight.value = weight
    }
}





