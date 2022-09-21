package com.internship.move.presentation.authentification.register.verification

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.internship.move.R
import com.internship.move.presentation.authentification.viewmodel.AuthenticationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class PendingVerificationFragment : Fragment(R.layout.fragment_pending_verification) {

    private val viewModel by viewModel<AuthenticationViewModel>()
    private val args: PendingVerificationFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                viewModel.licenseVerification(File(args.photoUri.toUri().path.toString()))
                viewModel.licenseData.observe(viewLifecycleOwner) {
                    if (it != "") {
                        findNavController().navigate(PendingVerificationFragmentDirections.actionPendingVerificationFragmentToSuccessValidationFragment())
                    }
                }
            },
            PENDING_DELAY
        )
    }

    companion object {
        private const val PENDING_DELAY = 5000L
    }
}