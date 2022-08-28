package com.internship.move.feature.authentification.forgotpassword

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentForgotPasswordBinding
import com.internship.move.util.enableButton
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class ForgotPasswordFragment : Fragment(R.layout.fragment_forgot_password) {
    private val binding by viewBinding(FragmentForgotPasswordBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
        binding.emailTIL.isEndIconVisible = false

        binding.chevronIV.setOnClickListener {
            findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment())
        }

        binding.emailTIL.setEndIconOnClickListener {
            binding.emailTIET.text?.clear()
        }

        binding.sendResetBtn.setOnClickListener {
            findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToResetPasswordFragment())
        }

        binding.emailTIET.doOnTextChanged { text, _, _, _ ->
            binding.emailTIL.isEndIconVisible = text?.isNotEmpty() == true
            changeBtnState(
                binding.emailTIET.text?.isNotEmpty() ?: false,
            )
        }
    }

    private fun changeBtnState(isNotEmpty: Boolean) {
        binding.sendResetBtn.isEnabled = isNotEmpty
        binding.sendResetBtn.enableButton()
    }
}