package com.internship.move.feature.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentMapBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class MapFragment : Fragment(R.layout.fragment_map) {

    private val binding by viewBinding(FragmentMapBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mapFragmentLogoutBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_mapFragment_to_splash_graph)
        }
    }
}