package com.internship.move.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentOnboardingBinding
import com.internship.move.presentation.onboarding.adapter.OnSkipListener
import com.internship.move.presentation.onboarding.adapter.ViewPagerAdapter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import com.internship.move.presentation.onboarding.adapter.OnboardingViewPagerItem

class OnboardingFragment : Fragment(R.layout.fragment_onboarding), OnSkipListener {

    private val binding by viewBinding(FragmentOnboardingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()
    }

    private fun initViewPager() {
        val adapter = ViewPagerAdapter(getList(), this)
        binding.onboardingVP2.adapter = adapter
        binding.onboardingVP2.isUserInputEnabled = false
        binding.dotsIndicator.attachTo(binding.onboardingVP2)
        binding.onboardingNextBtn.setOnClickListener {
            if (binding.onboardingVP2.currentItem == adapter.itemCount - 1) {
                findNavController().navigate(OnboardingFragmentDirections.actionOnboardingFragmentToAuthentificationGraph())
            } else {
                binding.onboardingVP2.currentItem++
                if (binding.onboardingVP2.currentItem == adapter.itemCount - 1) {
                    binding.onboardingNextBtn.text = resources.getText(R.string.get_started_button)
                }
            }
        }
    }

    override fun onSkipClickListener() {
        findNavController().navigate(OnboardingFragmentDirections.actionOnboardingFragmentToAuthentificationGraph())
    }

    private fun getList(): List<OnboardingViewPagerItem> = listOf(
        OnboardingViewPagerItem(
            R.drawable.ic_onboarding_safety,
            R.string.onboarding_safety_title,
            R.string.onboarding_safety_tutorial
        ),
        OnboardingViewPagerItem(
            R.drawable.ic_onboarding_scan,
            R.string.onboarding_scan_title,
            R.string.onboarding_scan_tutorial
        ),
        OnboardingViewPagerItem(
            R.drawable.ic_onboarding_ride,
            R.string.onboarding_ride_title,
            R.string.onboarding_ride_tutorial
        ),
        OnboardingViewPagerItem(
            R.drawable.ic_onboarding_parking,
            R.string.onboarding_parking_title,
            R.string.onboarding_parking_tutorial
        ),
        OnboardingViewPagerItem(
            R.drawable.ic_onboarding_rules,
            R.string.onboarding_rules_title,
            R.string.onboarding_rules_tutorial,
            true
        )
    )
}