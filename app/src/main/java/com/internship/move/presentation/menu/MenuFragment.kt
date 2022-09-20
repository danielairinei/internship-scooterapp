package com.internship.move.presentation.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentMenuBinding
import com.internship.move.presentation.menu.viewmodel.MenuViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : Fragment(R.layout.fragment_menu) {
    private val binding by viewBinding(FragmentMenuBinding::bind)
    private val viewModel by viewModel<MenuViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()

        binding.clearApp.setOnClickListener {
            viewModel.clearApp()
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToSplashGraph())
        }
    }

    private fun initObserver() {
        binding.logoutBtn.setOnClickListener {
            viewModel.logout(viewModel.getLoginToken())
            viewModel.loggedOut.observe(viewLifecycleOwner) {
                if (it) {
                    findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToSplashGraph())
                }
            }
        }
    }

    companion object {
        const val KEY_IS_USER_LOGGED_IN = "KEY_IS_USER_LOGGED_IN"
    }
}