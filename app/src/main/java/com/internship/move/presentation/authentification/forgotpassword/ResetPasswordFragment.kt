package com.internship.move.presentation.authentification.forgotpassword

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.internship.move.R
import com.internship.move.databinding.FragmentResetPasswordBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class ResetPasswordFragment : Fragment(R.layout.fragment_reset_password) {

    private val binding by viewBinding(FragmentResetPasswordBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
        val args : ResetPasswordFragmentArgs by navArgs()

        binding.tokenTV.text = args.resettoken

        binding.backIV.setOnClickListener {
            findNavController().navigate(ResetPasswordFragmentDirections.actionResetPasswordFragmentToLoginFragment())
        }

        binding.resetPasswordBtn.setOnClickListener {
            if (binding.newPasswordTIET.text.toString() == binding.confirmPasswordTIET.text.toString()) {
                findNavController().navigate(ResetPasswordFragmentDirections.actionResetPasswordFragmentToLoginFragment())
            } else {
                binding.confirmPasswordTIL.error = getString(R.string.authentication_reset_password_error)
                binding.confirmPasswordTIET.doOnTextChanged { _, _, _, _ ->
                    if (binding.confirmPasswordTIL.isErrorEnabled) {
                        binding.confirmPasswordTIL.isErrorEnabled = false
                    }
                }

            }
        }

        binding.newPasswordTIET.doOnTextChanged { text, _, _, _ ->
            binding.newPasswordTIL.isEndIconVisible = !text.isNullOrEmpty()
            changeBtnState(
                binding.newPasswordTIET.text?.isNotEmpty() ?: false,
                binding.confirmPasswordTIET.text?.isNotEmpty() ?: false
            )
        }

        binding.confirmPasswordTIET.doOnTextChanged { text, _, _, _ ->
            binding.confirmPasswordTIL.isEndIconVisible = !text.isNullOrEmpty()
            changeBtnState(
                binding.newPasswordTIET.text?.isNotEmpty() ?: false,
                binding.confirmPasswordTIET.text?.isNotEmpty() ?: false
            )
        }
    }

    private fun changeBtnState(newPasswordIsNotEmpty: Boolean, confirmNewPasswordIsNotEmpty: Boolean) {
        binding.resetPasswordBtn.isEnabled = newPasswordIsNotEmpty && confirmNewPasswordIsNotEmpty
    }
}


