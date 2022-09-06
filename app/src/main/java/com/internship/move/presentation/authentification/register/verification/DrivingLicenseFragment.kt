package com.internship.move.presentation.authentification.register.verification

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.internship.move.BuildConfig
import com.internship.move.R
import com.internship.move.databinding.FragmentDrivingLicenseBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import java.io.File

class DrivingLicenseFragment : Fragment(R.layout.fragment_driving_license) {

    private val binding by viewBinding(FragmentDrivingLicenseBinding::bind)
    private var latestTmpUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners(){
        binding.backIV.setOnClickListener {
            findNavController().navigate(DrivingLicenseFragmentDirections.actionDrivingLicenseFragmentToAuthentificationGraph())
        }

        binding.addLicenseBtn.setOnClickListener {
            takeImage()
        }
    }

    private fun takeImage() {
        lifecycleScope.launchWhenStarted {
            getTmpFileUri().let { uri ->
                latestTmpUri = uri
                takeImageResult.launch(uri)
            }
        }
    }

    private fun getTmpFileUri(): Uri? {
        val tmpFile = File.createTempFile("tmp_image_file", ".png").apply {
            createNewFile()
            deleteOnExit()
        }

        return context?.let { FileProvider.getUriForFile(it, "${BuildConfig.APPLICATION_ID}.provider", tmpFile) }
    }

    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            latestTmpUri?.let { uri ->
                findNavController().navigate(DrivingLicenseFragmentDirections.actionDrivingLicenseFragmentToPendingVerificationFragment(uri.toString()))
            }

        }
    }
}