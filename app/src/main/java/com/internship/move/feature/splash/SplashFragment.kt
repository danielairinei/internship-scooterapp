package com.internship.move.feature.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.feature.authentification.register.RegisterFragment
import com.internship.move.feature.map.MapFragment

class SplashFragment : Fragment(R.layout.fragment_splash) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                when (sharedPref?.getString(
                    getString(R.string.saved_splash_next_fragment),
                    getString(R.string.shared_pref_default_value)
                )
                ) {
                    MapFragment.SPLASH_TO_MAP -> findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeGraph())
                    RegisterFragment.SPLASH_TO_REGISTER -> findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToAuthentificationGraph())
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