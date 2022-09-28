package com.internship.move.presentation.menu

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
            viewModel.errorData.observe(viewLifecycleOwner) { errorResponse ->
                if (errorResponse.message.isEmpty()) {
                    viewModel.loggedOut.observe(viewLifecycleOwner) { isLoggedOut ->
                        if (isLoggedOut) {
                            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToSplashGraph())
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), errorResponse.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}