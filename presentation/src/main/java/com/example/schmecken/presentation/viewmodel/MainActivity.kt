package com.example.schmecken.presentation.viewmodel

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.data.AppDatabase
import com.example.data.filters.Filtersdb
import com.example.domain.DBsInitialization
import com.example.domain.SharedViewModel
import com.example.schmecken.R
import com.example.schmecken.di.FragmentsPagerAdapter
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: FragmentsPagerAdapter
    private lateinit var dishDb: AppDatabase
    private lateinit var filtersDb: Filtersdb


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbsInitialization = DBsInitialization(this)
        dishDb = dbsInitialization.makeDishDB()
        filtersDb = dbsInitialization.makeFiltersDB()


        adapter = FragmentsPagerAdapter(supportFragmentManager, lifecycle)


        tabLayout = findViewById(R.id.tabLayout)
        viewPager2 = findViewById(R.id.viewPager2)


        tabLayout.addTab(tabLayout.newTab().setText("First"))
        tabLayout.addTab(tabLayout.newTab().setText("Second"))
        tabLayout.addTab(tabLayout.newTab().setText("Third"))
        tabLayout.addTab(tabLayout.newTab().setText("Fourth"))

        viewPager2.adapter = adapter





        tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager2.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

    }

}