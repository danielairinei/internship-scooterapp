package com.internship.move.feature.authentification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentLoginBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginFragmentRegisterBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.loginFragmentHomeBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_home_graph)
        }
    }
}