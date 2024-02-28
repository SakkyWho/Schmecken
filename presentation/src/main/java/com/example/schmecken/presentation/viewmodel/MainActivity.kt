package com.example.schmecken.presentation.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.schmecken.R
import com.example.schmecken.di.FragmentsPagerAdapter
import com.example.schmecken.di.SharedViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val sharedViewModel: SharedViewModel by viewModels()
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: FragmentsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager2 = findViewById(R.id.viewPager2)



        adapter = FragmentsPagerAdapter(viewPager2, supportFragmentManager, lifecycle)
        sharedViewModel.switchFragmentCommand.observe(this, Observer { position ->
            adapter.switchToFragment(position)
        })
        viewPager2.offscreenPageLimit = adapter.itemCount
        sharedViewModel.viewPager.value = viewPager2

        tabLayout = findViewById(R.id.tabLayout)

        tabLayout.addTab(tabLayout.newTab().setText("List"))
        tabLayout.addTab(tabLayout.newTab().setText("Info"))
        tabLayout.addTab(tabLayout.newTab().setText("Recipe"))
        tabLayout.addTab(tabLayout.newTab().setText("Diet"))

        viewPager2.adapter = adapter

        sharedViewModel.selectedItemId.observe(this, Observer { itemId ->
            Log.d("MainActivity", "Item selected: $itemId")
            viewPager2.currentItem = 1
        })


        tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager2.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }
}
