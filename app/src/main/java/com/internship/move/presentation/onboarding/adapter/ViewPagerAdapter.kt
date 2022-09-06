package com.internship.move.presentation.onboarding.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(private val currentList: List<OnboardingViewPagerItem>, fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment = ViewPagerFragment.newInstance(currentList[position])

    override fun getItemCount(): Int = currentList.size
}