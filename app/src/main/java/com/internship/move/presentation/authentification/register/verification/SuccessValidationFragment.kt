package com.internship.move.presentation.authentification.register.verification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentSuccessValidationBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class SuccessValidationFragment : Fragment(R.layout.fragment_success_validation) {

    private val binding by viewBinding(FragmentSuccessValidationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.findScooterBtn.setOnClickListener {
            findNavController().navigate(SuccessValidationFragmentDirections.actionSuccessValidationFragmentToHomeGraph())
        }
    }
}