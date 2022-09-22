package com.internship.move.presentation.authentification.register.verification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.internship.move.R
import com.internship.move.presentation.authentification.viewmodel.AuthenticationViewModel
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class PendingVerificationFragment : Fragment(R.layout.fragment_pending_verification) {

    private val viewModel by viewModel<AuthenticationViewModel>()
    private val args: PendingVerificationFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val file = Compressor.compress(requireActivity().applicationContext, File(args.photoUri))
            viewModel.licenseVerification(file)
            viewModel.licenseData.observe(viewLifecycleOwner) {
                if (it != "") {
                    findNavController().navigate(PendingVerificationFragmentDirections.actionPendingVerificationFragmentToSuccessValidationFragment())
                }
            }
        }
    }
}