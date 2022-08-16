package com.internship.move.feature.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R

class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed(
            { findNavController().navigate(R.id.action_splashFragment_to_onboarding_graph) },
            SPLASH_FRAGMENT_DELAY
        )
    }

    companion object {
        private const val SPLASH_FRAGMENT_DELAY = 2000L
    }
}