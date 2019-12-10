package com.pabji.myfridge.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainViewPagerAdapter(activity: FragmentActivity, private val list: List<Fragment>) :
    FragmentStateAdapter(activity) {

    override fun getItemCount() = list.size

    override fun createFragment(position: Int) = list[position]

}