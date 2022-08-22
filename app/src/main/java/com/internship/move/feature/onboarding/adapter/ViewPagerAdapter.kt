package com.internship.move.feature.onboarding.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.internship.move.feature.onboarding.model.ViewPagerItem

class ViewPagerAdapter(private val currentList: ArrayList<ViewPagerItem>, fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment = ViewPagerFragment.newInstance(currentList[position])

    override fun getItemCount(): Int = currentList.size
}