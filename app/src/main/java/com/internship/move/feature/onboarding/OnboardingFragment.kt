package com.internship.move.feature.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentOnboardingBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {

    private val binding by viewBinding(FragmentOnboardingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.onboardingBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_onboardingFragment_to_authentification_graph)
        }

        binding.onboardingHelpBtn.setOnClickListener {
            navigateAndPassData(view)
        }
    }

    private fun navigateAndPassData(view: View){
        val argText = binding.onboardingET.text.toString()
        view.findNavController().navigate(OnboardingFragmentDirections.actionOnboardingFragmentToHelpFragment(argText))
    }
}