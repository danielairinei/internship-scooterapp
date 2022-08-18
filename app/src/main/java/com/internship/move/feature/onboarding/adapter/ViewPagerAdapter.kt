package com.internship.move.feature.onboarding.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.internship.move.feature.onboarding.OnboardingFragment
import com.internship.move.feature.onboarding.model.ViewPagerItem

class ViewPagerAdapter(val currentList : ArrayList<ViewPagerItem>, fragment : Fragment) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return ViewPagerFragment(currentList[position])
    }

    override fun getItemCount(): Int {
        return PAGE_NUM
    }

    companion object{
        private const val PAGE_NUM = 4
    }
}