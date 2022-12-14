package com.internship.move.presentation.authentification.forgotpassword

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentForgotPasswordBinding
import com.internship.move.utils.extensions.InfoDialogFragment
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class ForgotPasswordFragment : Fragment(R.layout.fragment_forgot_password) {

    private val binding by viewBinding(FragmentForgotPasswordBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
        binding.backIV.setOnClickListener {
            findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment())
        }

        binding.sendResetBtn.setOnClickListener {
            showNoticeDialog()
        }

        binding.emailTIET.doOnTextChanged { _, _, _, _ ->
            changeBtnState(
                binding.emailTIET.text?.isNotEmpty() ?: false,
            )
        }
    }

    private fun showNoticeDialog() {
        val dialog = InfoDialogFragment.newInstance(
            getString(R.string.authentication_forgot_password_dialog_title),
            getString(R.string.authentication_forgot_password_dialog_content),
            getString(R.string.button_ok_text)
        )
        dialog.show(parentFragmentManager, KEY_FORGOT_PASSWORD_DIALOG)
    }


    private fun changeBtnState(isNotEmpty: Boolean) {
        binding.sendResetBtn.isEnabled = isNotEmpty
    }

    companion object{
        private const val KEY_FORGOT_PASSWORD_DIALOG = "KEY_FORGOT_PASSWORD_DIALOG"
    }
}