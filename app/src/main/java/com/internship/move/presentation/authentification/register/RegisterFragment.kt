package com.internship.move.presentation.authentification.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.data.dto.user.UserRegisterRequestDto
import com.internship.move.databinding.FragmentRegisterBinding
import com.internship.move.presentation.authentification.viewmodel.AuthenticationViewModel
import com.internship.move.utils.constants.ERROR_TIME
import com.internship.move.utils.extensions.makeLinks
import com.tapadoo.alerter.Alerter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding by viewBinding(FragmentRegisterBinding::bind)
    private val viewModel by viewModel<AuthenticationViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFragment()
    }

    private fun initFragment() {
        viewModel.notifyUserHasCompletedOnboarding()

        initClickableTV()
        initListeners()
        initObserver()
    }

    private fun initListeners() {
        binding.passwordTIL.isEndIconVisible = false

        binding.getStartedBtn.setOnClickListener {
            viewModel.register(
                UserRegisterRequestDto(
                    binding.emailTIET.text.toString(),
                    binding.usernameTIET.text.toString(),
                    binding.passwordTIET.text.toString()
                )
            )
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
        binding.registerToLoginTV.makeLinks(getString(R.string.key_register_to_login)) {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
    }

    private fun changeBtnState(emailIsNotEmpty: Boolean, usernameIsNotEmpty: Boolean, passwordIsNotEmpty: Boolean) {
        binding.getStartedBtn.isEnabled = emailIsNotEmpty && usernameIsNotEmpty && passwordIsNotEmpty
    }

    private fun initObserver() {
        viewModel.errorData.observe(viewLifecycleOwner) { errorResponse ->
            if (errorResponse == null) {
                viewModel.userRegisterData.observe(viewLifecycleOwner) {
                    findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                }
            } else {
                Alerter.create(requireActivity())
                    .setTitle(errorResponse.message)
                    .setTitleAppearance(R.style.AlertTitleAppearance)
                    .setDuration(ERROR_TIME)
                    .setBackgroundColorRes(R.color.error_alerter_background)
                    .show()
            }
        }
    }
}