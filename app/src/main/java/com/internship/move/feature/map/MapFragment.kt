package com.internship.move.feature.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentMapBinding
import com.internship.move.feature.map.viewmodel.MapViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class MapFragment : Fragment(R.layout.fragment_map) {

    private val binding by viewBinding(FragmentMapBinding::bind)
    private val viewModel by lazy { ViewModelProvider(this)[MapViewModel::class.java] }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setIsUserLoggedIn(true)

        binding.mapFragmentLogoutBtn.setOnClickListener {
            viewModel.setIsUserLoggedIn(false)
            findNavController().navigate(MapFragmentDirections.actionMapFragmentToSplashGraph())
        }

        binding.clearAppBtn.setOnClickListener {
            viewModel.setIsUserLoggedIn(false)
            viewModel.setUserHasCompletedOnboarding(false)
            findNavController().navigate(MapFragmentDirections.actionMapFragmentToSplashGraph())
        }
    }

    companion object {
        const val IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN"
    }
}