package com.internship.move.feature.help

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.internship.move.R
import com.internship.move.databinding.FragmentHelpBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class HelpFragment : Fragment(R.layout.fragment_help) {

    private val binding by viewBinding(FragmentHelpBinding::bind)
    private val args: HelpFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.helpTV.text = args.argText
        binding.helpFragmentBackBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_helpFragment_to_onboardingFragment)
        }
    }
}