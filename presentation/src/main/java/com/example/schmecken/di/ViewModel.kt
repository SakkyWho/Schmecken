package com.example.schmecken.di

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2
import com.example.domain.bitmapdata
import com.example.domain.secondinfo
import javax.inject.Inject

class SharedViewModel @Inject constructor() : ViewModel() {
    val viewPager: MutableLiveData<ViewPager2> = MutableLiveData()
    val secondInfo: MutableLiveData<secondinfo> = MutableLiveData()
    val selectedItemId: MutableLiveData<Int> = MutableLiveData()
    val switchFragmentCommand: MutableLiveData<Int> = MutableLiveData()
    val bitmapData: MutableLiveData<List<bitmapdata>> = MutableLiveData()
    val bitmap: MutableLiveData<Bitmap> = MutableLiveData()
    suspend fun loadBitmap(id: Int): Bitmap? {
        val bitmapDataList = bitmapData.value ?: emptyList()
        val bitmapData = bitmapDataList.find { it.id == id }
        return bitmapData?.let {
            BitmapFactory.decodeByteArray(it.imageBitmap, 0, it.imageBitmap.size)
        }
    }

}





