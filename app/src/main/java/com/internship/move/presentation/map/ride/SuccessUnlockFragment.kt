package com.internship.move.presentation.map.ride

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R

class SuccessUnlockFragment : Fragment(R.layout.fragment_success_unlock) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                findNavController().navigate(SuccessUnlockFragmentDirections.actionSuccessUnlockFragmentToMapFragment())
            },
            NAVIGATION_DELAY
        )
    }

    companion object {
        private const val NAVIGATION_DELAY = 3000L
    }
}