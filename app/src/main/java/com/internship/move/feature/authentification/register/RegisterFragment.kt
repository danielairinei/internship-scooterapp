package com.internship.move.feature.authentification.register

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentRegisterBinding
import com.internship.move.feature.splash.SplashFragment
import com.internship.move.util.enableButton
import com.internship.move.util.makeLinks
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding by viewBinding(FragmentRegisterBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFragment()
    }

    private fun initFragment() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        sharedPref.edit().putString(SplashFragment.SPLASH_NEXT_FRAGMENT, SPLASH_TO_REGISTER).apply()

        initClickableTV()
        initListeners()
    }

    private fun initListeners() {
        binding.emailTIL.isEndIconVisible = false
        binding.passwordTIL.isEndIconVisible = false

        binding.emailTIL.setEndIconOnClickListener {
            binding.emailTIET.text?.clear()
        }

        binding.getStartedBtn.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeGraph())
        }

        binding.emailTIET.doOnTextChanged { text, _, _, _ ->
            binding.emailTIL.isEndIconVisible = text?.isNotEmpty() == true
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
        binding.termsTV.makeLinks(getString(R.string.terms_and_conditions_key), action = {
            Toast.makeText(context, getString(R.string.terms_and_conditions_key), Toast.LENGTH_SHORT).show()
        })
        binding.termsTV.makeLinks(getString(R.string.privacy_policy_key), action = {
            Toast.makeText(context, getString(R.string.privacy_policy_key), Toast.LENGTH_SHORT).show()
        })
        binding.registerToLoginTV.makeLinks(getString(R.string.register_to_login_key), action = {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        })
    }

    private fun changeBtnState(emailIsNotEmpty: Boolean, usernameIsNotEmpty: Boolean, passwordIsNotEmpty: Boolean) {
        binding.getStartedBtn.isEnabled = emailIsNotEmpty && usernameIsNotEmpty && passwordIsNotEmpty
        binding.getStartedBtn.enableButton()
    }

    companion object {
        const val SPLASH_TO_REGISTER = "SPLASH_TO_REGISTER"
    }
}