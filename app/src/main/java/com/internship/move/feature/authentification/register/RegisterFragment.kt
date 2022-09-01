package com.internship.move.feature.authentification.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentRegisterBinding
import com.internship.move.feature.authentification.viewmodel.AuthenticationViewModel
import com.internship.move.util.makeLinks
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding by viewBinding(FragmentRegisterBinding::bind)
    private val viewModel by lazy { ViewModelProvider(this)[AuthenticationViewModel::class.java] }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFragment()
    }

    private fun initFragment() {
        viewModel.setUserHasCompletedOnboarding(true)

        initClickableTV()
        initListeners()
    }

    private fun initListeners() {
        binding.passwordTIL.isEndIconVisible = false

        binding.getStartedBtn.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeGraph())
        }

        binding.emailTIET.doOnTextChanged { _, _, _, _ ->
            changeBtnState(
                binding.emailTIET.text?.isNotEmpty() ?: false,
                binding.usernameTIET.text?.isNotEmpty() ?: false,
                binding.passwordTIET.text?.isNotEmpty() ?: false
            )
        }

        binding.usernameTIET.doOnTextChanged { _, _, _, _ ->
            changeBtnState(
                binding.emailTIET.text?.isNotEmpty() ?: false,
                binding.usernameTIET.text?.isNotEmpty() ?: false,
                binding.passwordTIET.text?.isNotEmpty() ?: false
            )
        }

        binding.passwordTIET.doOnTextChanged { text, _, _, _ ->
            binding.passwordTIL.isEndIconVisible = text?.isNotEmpty() == true
            changeBtnState(
                binding.emailTIET.text?.isNotEmpty() ?: false,
                binding.usernameTIET.text?.isNotEmpty() ?: false,
                binding.passwordTIET.text?.isNotEmpty() ?: false
            )
        }
    }

    private fun initClickableTV() {
        binding.termsTV.makeLinks(getString(R.string.key_terms_and_conditions), action = {
            Toast.makeText(context, getString(R.string.key_terms_and_conditions), Toast.LENGTH_SHORT).show()
        })
        binding.termsTV.makeLinks(getString(R.string.key_privacy_policy), action = {
            Toast.makeText(context, getString(R.string.key_privacy_policy), Toast.LENGTH_SHORT).show()
        })
        binding.registerToLoginTV.makeLinks(getString(R.string.key_register_to_login), action = {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        })
    }

    private fun changeBtnState(emailIsNotEmpty: Boolean, usernameIsNotEmpty: Boolean, passwordIsNotEmpty: Boolean) {
        binding.getStartedBtn.isEnabled = emailIsNotEmpty && usernameIsNotEmpty && passwordIsNotEmpty
    }

    companion object {
        const val HAS_USER_COMPLETED_ONBOARDING_KEY = "HAS_USER_COMPLETED_ONBOARDING"
    }
}