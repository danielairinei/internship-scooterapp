package com.internship.move.presentation.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentMapBinding
import com.internship.move.presentation.map.viewmodel.MapViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapFragment : Fragment(R.layout.fragment_map) {

    private val binding by viewBinding(FragmentMapBinding::bind)
    private val viewModel by viewModel<MapViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mapFragmentLogoutBtn.setOnClickListener {
            viewModel.logOut()
            findNavController().navigate(MapFragmentDirections.actionMapFragmentToSplashGraph())
        }

        binding.clearAppBtn.setOnClickListener {
            viewModel.clearApp()
            findNavController().navigate(MapFragmentDirections.actionMapFragmentToSplashGraph())
        }
    }

    companion object {
        const val KEY_IS_USER_LOGGED_IN = "KEY_IS_USER_LOGGED_IN"
    }
}