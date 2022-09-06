package com.internship.move.presentation.authentification.register

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentDrivingLicenseBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class DrivingLicenseFragment : Fragment(R.layout.fragment_driving_license) {

    private val binding by viewBinding(FragmentDrivingLicenseBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners(){
        binding.backIV.setOnClickListener {
            findNavController().navigate(DrivingLicenseFragmentDirections.actionDrivingLicenseFragmentToAuthentificationGraph())
        }

        binding.addLicenseBtn.setOnClickListener {
            //bottom sheet?
            startActivity(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }
    }
}