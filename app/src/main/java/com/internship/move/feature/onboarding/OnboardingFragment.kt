package com.internship.move.feature.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.internship.move.R
import com.internship.move.databinding.FragmentOnboardingBinding
import com.internship.move.feature.onboarding.adapter.ViewPagerAdapter
import com.internship.move.feature.onboarding.model.ViewPagerItem
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {

    private val binding by viewBinding(FragmentOnboardingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()

//        binding.onboardingBtn.setOnClickListener {
//            findNavController().navigate(OnboardingFragmentDirections.actionOnboardingFragmentToAuthentificationGraph())
//        }
//
//        binding.onboardingHelpBtn.setOnClickListener {
//            findNavController().navigate(OnboardingFragmentDirections.actionOnboardingFragmentToHelpFragment(binding.onboardingET.text.toString()))
//        }
    }

    private fun initViewPager() {
        val adapter = ViewPagerAdapter(getList(), this)
        binding.onboardingVP2.adapter = adapter
        binding.onboardingVP2.isUserInputEnabled = false
        binding.onboardingNextBtn.setOnClickListener{
            binding.onboardingVP2.setCurrentItem(binding.onboardingVP2.currentItem+1,true)
        }
    }

    private fun getList(): ArrayList<ViewPagerItem> {
        val list = arrayListOf<ViewPagerItem>()

        list.add(
            ViewPagerItem(
                1,
                R.drawable.ic_onboarding_safety,
                R.string.onboarding_safety_title,
                R.string.onboarding_safety_tutorial
            )
        )
        list.add(
            ViewPagerItem(
                2,
                R.drawable.ic_onboarding_scan,
                R.string.onboarding_scan_title,
                R.string.onboarding_scan_tutorial
            )
        )
        list.add(
            ViewPagerItem(
                3,
                R.drawable.ic_onboarding_parking,
                R.string.onboarding_parking_title,
                R.string.onboarding_parking_tutorial
            )
        )
        list.add(ViewPagerItem(
            4,
            R.drawable.ic_onboarding_rules,
            R.string.onboarding_rules_title,
            R.string.onboarding_rules_tutorial
        ))

        return list
    }
}