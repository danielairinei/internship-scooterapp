package com.internship.move.presentation.menu

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentMenuBinding
import com.internship.move.presentation.menu.viewmodel.MenuViewModel
import com.internship.move.utils.constants.ERROR_TIME
import com.tapadoo.alerter.Alerter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val binding by viewBinding(FragmentMenuBinding::bind)
    private val viewModel by viewModel<MenuViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserRequest()

        initObserver()
        initListeners()
    }

    private fun initObserver() {
        viewModel.errorData.observe(viewLifecycleOwner) { errorResponse ->
            if (errorResponse != null) {
                Alerter.create(requireActivity())
                    .setTitle(errorResponse.message)
                    .setTitleAppearance(R.style.AlertTitleAppearance)
                    .setDuration(ERROR_TIME)
                    .setBackgroundColorRes(R.color.error_alerter_background)
                    .show()
            }
        }

        viewModel.userData.observe(viewLifecycleOwner) { user ->
            binding.topAppBar.title = getString(R.string.menu_appbar_heading) + " ${user.name}!"
        }
    }

    private fun initListeners() {
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToMapFragment())
        }

        binding.accountTV.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToMenuAccountFragment())
        }

        binding.historyCard.historyBtn.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToMenuHistoryFragment())
        }

        binding.changePwTV.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToMenuChangePasswordFragment())
        }

        binding.termsTV.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.key_terms_and_conditions), Toast.LENGTH_SHORT).show()
        }

        binding.privacyTV.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.key_privacy_policy), Toast.LENGTH_SHORT).show()
        }

        binding.rateUsTV.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.key_rate_us), Toast.LENGTH_SHORT).show()
        }
    }
}