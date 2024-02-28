package com.example.schmecken.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.schmecken.presentation.ui.FirstFragment
import com.example.schmecken.presentation.ui.FourthFragment
import com.example.schmecken.presentation.ui.SecondFragment
import com.example.schmecken.presentation.ui.ThirdFragment

class FragmentsPagerAdapter(
    private val viewPager: ViewPager2,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FirstFragment()
            1 -> SecondFragment()
            2 -> ThirdFragment()
            3 -> FourthFragment()
            else -> throw IllegalStateException("err")
        }
    }

    fun switchToFragment(position: Int) {
        viewPager.currentItem = position
    }
}

