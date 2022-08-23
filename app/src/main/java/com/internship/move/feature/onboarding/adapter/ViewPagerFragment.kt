package com.internship.move.feature.onboarding.adapter

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.internship.move.R
import com.internship.move.databinding.ItemOnboardingPageBinding
import com.internship.move.feature.onboarding.model.ViewPagerItem
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class ViewPagerFragment : Fragment(R.layout.item_onboarding_page) {

    private val binding by viewBinding(ItemOnboardingPageBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagerItem: ViewPagerItem? = arguments?.getParcelable(KEY_ONBOARDING_PAGER_ITEM)

        if (pagerItem != null) {
            binding.onboardingIV.setImageDrawable(ContextCompat.getDrawable(requireContext(), pagerItem.photo))
            binding.onboardingHeadingTV.text = getText(pagerItem.heading)
            binding.onboardingContentTV.text = getText(pagerItem.content)
            binding.onboardingSkipTV.isVisible = !pagerItem.isLastItem
        }

        binding.onboardingSkipTV.setOnClickListener {
            (parentFragment as? OnSkipListener)?.onSkipClickListener()
        }
    }

    companion object {
        private const val KEY_ONBOARDING_PAGER_ITEM = "KEY_ONBOARDING_PAGER_ITEM"

        fun newInstance(pagerItem: ViewPagerItem): ViewPagerFragment = ViewPagerFragment().apply {
            arguments = Bundle().apply { putParcelable(KEY_ONBOARDING_PAGER_ITEM, pagerItem) }
        }
    }
}