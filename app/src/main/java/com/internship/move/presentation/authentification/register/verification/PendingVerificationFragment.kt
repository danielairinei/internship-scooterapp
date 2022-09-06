package com.internship.move.presentation.authentification.register.verification

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R

class PendingVerificationFragment : Fragment(R.layout.fragment_pending_verification) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                findNavController().navigate(PendingVerificationFragmentDirections.actionPendingVerificationFragmentToSuccessValidationFragment())
            },
            PENDING_DELAY
        )
    }

    companion object {
        private const val PENDING_DELAY = 10000L
    }
}