package com.internship.move.presentation.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentMenuAccountBinding
import com.internship.move.presentation.menu.viewmodel.MenuViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuAccountFragment : Fragment(R.layout.fragment_menu_account) {

    private val binding by viewBinding(FragmentMenuAccountBinding::bind)
    private val viewModel by viewModel<MenuViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserRequest()

        initListeners()
        initObserver()

    }

    private fun initListeners() {
        binding.logoutIV.setOnClickListener {
            viewModel.logout()
        }
        binding.logoutTV.setOnClickListener {
            viewModel.logout()
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(MenuAccountFragmentDirections.actionMenuAccountFragmentToMenuFragment())
        }
    }

    private fun initObserver() {
        viewModel.userData.observe(viewLifecycleOwner) {
            binding.usernameTIET.setText(it.name)
            binding.emailTIET.setText(it.email)
        }
        viewModel.loggedOut.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(MenuAccountFragmentDirections.actionMenuAccountFragmentToSplashGraph())
            }
        }
    }
}