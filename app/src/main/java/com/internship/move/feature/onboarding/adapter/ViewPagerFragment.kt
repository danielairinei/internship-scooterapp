package com.internship.move.feature.onboarding.adapter

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.internship.move.R
import com.internship.move.databinding.ItemOnboardingPageBinding
import com.internship.move.feature.onboarding.model.ViewPagerItem
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class ViewPagerFragment(private val pagerItem : ViewPagerItem) : Fragment(R.layout.item_onboarding_page) {

    private val binding by viewBinding(ItemOnboardingPageBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.onboardingHeadingTV.text = getText(pagerItem.heading)
        binding.onboardingIV.setImageDrawable(context?.let { ContextCompat.getDrawable(it,pagerItem.photoUrl) })
        binding.onboardingContentTV.text = getText(pagerItem.content)
    }
}