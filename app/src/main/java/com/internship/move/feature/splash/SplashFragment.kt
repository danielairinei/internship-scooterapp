package com.internship.move.feature.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.feature.splash.viewmodel.SplashViewModel

class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        Handler(Looper.getMainLooper()).postDelayed(
            {
                when {
                    viewModel.getIsUserLoggedIn() -> findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeGraph())
                    viewModel.getHasUserCompletedOnboarding() && !viewModel.getIsUserLoggedIn() -> findNavController().navigate(
                        SplashFragmentDirections.actionSplashFragmentToAuthentificationGraph()
                    )
                    else -> findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToOnboardingGraph())
                }
            },
            SPLASH_FRAGMENT_DELAY
        )
    }

    companion object {
        private const val SPLASH_FRAGMENT_DELAY = 2000L
    }
}