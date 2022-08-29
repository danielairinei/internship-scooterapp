package com.internship.move.feature.authentification.register

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentRegisterBinding
import com.internship.move.feature.splash.SplashFragment
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding by viewBinding(FragmentRegisterBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        sharedPref.edit().putString(SplashFragment.SPLASH_NEXT_FRAGMENT, SPLASH_TO_REGISTER).apply()

        initButtons()
    }

    private fun initButtons() {
        binding.registerFragmentLoginBtn.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }

        binding.registerFragmentHomeBtn.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeGraph())
        }
    }

    companion object {
        const val SPLASH_TO_REGISTER = "SPLASH_TO_REGISTER"
    }
}