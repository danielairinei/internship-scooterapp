package com.internship.move.feature.map

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentMapBinding
import com.internship.move.feature.splash.SplashFragment
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class MapFragment : Fragment(R.layout.fragment_map) {

    private val binding by viewBinding(FragmentMapBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        sharedPref.edit().putString(getString(R.string.saved_splash_next_fragment), SPLASH_TO_MAP).apply()

        binding.mapFragmentLogoutBtn.setOnClickListener {
            sharedPref.edit().putString(SplashFragment.SPLASH_NEXT_FRAGMENT, SPLASH_TO_ONBOARDING).apply()

            findNavController().navigate(MapFragmentDirections.actionMapFragmentToSplashGraph())
        }
    }

    companion object {
        const val SPLASH_TO_MAP = "SPLASH_TO_MAP"
        const val SPLASH_TO_ONBOARDING = "SPLASH_TO_ONBOARDING"
    }
}