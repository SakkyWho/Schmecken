package com.example.schmecken.di

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import com.example.domain.logic.DomainPresentationProvider
import com.example.domain.dataclasses.FourthInfo
import com.example.domain.dataclasses.SimpleDish
import com.example.domain.dataclasses.ThirdInfo
import com.example.domain.dataclasses.domeindata

import com.example.domain.dataclasses.secondinfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class SharedViewModel @Inject constructor(private val dbs: DomainPresentationProvider) : ViewModel() {
    val viewPager: MutableLiveData<ViewPager2> = MutableLiveData()
    val secondInfo: MutableLiveData<secondinfo> = MutableLiveData()
    val selectedItemId: MutableLiveData<Int> = MutableLiveData()
    val selectedItemLabel: MutableLiveData<String> = MutableLiveData()
    val switchFragmentCommand: MutableLiveData<Int> = MutableLiveData()
    val isSwitchChecked = MutableLiveData<Boolean>()
    private val bitmapDataList: MutableLiveData<List<domeindata>> = MutableLiveData()
    val itemList: MutableLiveData<List<SimpleDish>> = MutableLiveData()
    private var currentPage = 0
    private val itemsPerPage = 15
    var isLoading = false


    fun removeUnlikedItems() {
        val list = bitmapDataList.value ?: emptyList()
        val updatedList = list.filter { it.isLiked }
        bitmapDataList.value = updatedList
    }

    fun getBitmapDataList(): LiveData<List<domeindata>> {
        return bitmapDataList
    }
    fun loadtodb() {
        viewModelScope.launch {
            val bitmapDataList = bitmapDataList.value ?: emptyList()
            dbs.upsertBitBase(bitmapDataList)
        }
    }


    fun addBitmapData(newBitmapData: domeindata) {
        val list = bitmapDataList.value ?: emptyList()
        val existingItemIndex = list.indexOfFirst { it.id == newBitmapData.id }
        if (existingItemIndex >= 0) {
            val updatedList = list.toMutableList()
            updatedList[existingItemIndex] = newBitmapData
            bitmapDataList.value = updatedList
        } else {
            bitmapDataList.value = list + newBitmapData
        }
    }


    fun loadItems() {
        if (!isLoading) {
            isLoading = true
            viewModelScope.launch {
                try {
                    val items = withContext(Dispatchers.IO) {
                        dbs.getPreviewList(currentPage * itemsPerPage, itemsPerPage)
                    }
                    val list = itemList.value ?: emptyList()
                    itemList.value = list + items
                    currentPage++
                    isLoading = false
                } catch (e: Exception) {
                    // Обработка ошибки
                }
            }
        }
    }
    fun loadLikedItems() {
        if (!isLoading) {
            isLoading = true
            viewModelScope.launch {
                try {
                    val items = withContext(Dispatchers.IO) {
                        dbs.getBitmapList()
                    }
                    val list = bitmapDataList.value ?: emptyList()
                    bitmapDataList.value = list + items
                    currentPage++
                    isLoading = false
                } catch (e: Exception) {
                }
            }
        }
    }
    fun loadBitmapDataListFromDB() {
        viewModelScope.launch {
            try {
                val bitmapDataListFromDB = withContext(Dispatchers.IO) {
                    dbs.getBitmapList()
                }
                bitmapDataList.value = bitmapDataListFromDB
            } catch (e: Exception) {
            }
        }
    }
    suspend fun getdietinfo(age : Int, gender : String, weight : Double, height : Double): List<FourthInfo>{
        return dbs.getdiet(age, gender, weight, height)
    }
    suspend fun getsecondinfo(id : Int) : secondinfo {
        return dbs.ddpbasicallinfo(id)
    }
    suspend fun getthirdinfo(id : Int) : ThirdInfo {
        return dbs.getThirdInfo(id)
    }

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





