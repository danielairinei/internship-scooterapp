package com.internship.move.presentation.authentification.login

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentLoginBinding
import com.internship.move.presentation.authentification.viewmodel.AuthenticationViewModel
import com.internship.move.utils.extensions.makeLinks
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Observer

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel by viewModel<AuthenticationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickableTV()
        initListeners()
        initObserver()
    }

    private fun initListeners() {
        binding.passwordTIL.isEndIconVisible = false

        binding.loginBtn.setOnClickListener {
            viewModel.login(binding.emailTIET.text.toString(),binding.passwordTIET.text.toString() )
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
        binding.forgotPasswordTV.makeLinks(getString(R.string.authentication_login_forgot_password)) {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
        }
        binding.loginToRegisterTV.makeLinks(getString(R.string.key_login_to_register)) {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }

    private fun changeBtnState(emailIsNotEmpty: Boolean, passwordIsNotEmpty: Boolean) {
        binding.loginBtn.isEnabled = emailIsNotEmpty && passwordIsNotEmpty
    }

    private fun initObserver(){
        viewModel.userLoginData.observe(viewLifecycleOwner){
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeGraph())
        }
    }
}