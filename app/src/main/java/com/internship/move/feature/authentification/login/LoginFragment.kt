package com.internship.move.feature.authentification.login

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentLoginBinding
import com.internship.move.util.makeLinks
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickableTV()
        initListeners()
    }

    private fun initListeners() {
        binding.passwordTIL.isEndIconVisible = false

        binding.loginBtn.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeGraph())
        }

        binding.emailTIET.doOnTextChanged { _, _, _, _ ->
            changeBtnState(
                binding.emailTIET.text?.isNotEmpty() ?: false,
                binding.passwordTIET.text?.isNotEmpty() ?: false
            )
        }

        binding.passwordTIET.doOnTextChanged { text, _, _, _ ->
            binding.passwordTIL.isEndIconVisible = !text.isNullOrEmpty()
            changeBtnState(
                binding.emailTIET.text?.isNotEmpty() ?: false,
                binding.passwordTIET.text?.isNotEmpty() ?: false
            )
        }
    }

    private fun initClickableTV() {
        binding.forgotPasswordTV.makeLinks(getString(R.string.authentication_login_forgot_password), action = {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
        })
        binding.loginToRegisterTV.makeLinks(getString(R.string.key_login_to_register), action = {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        })
    }

    private fun changeBtnState(emailIsNotEmpty: Boolean, passwordIsNotEmpty: Boolean) {
        binding.loginBtn.isEnabled = emailIsNotEmpty && passwordIsNotEmpty
    }
}