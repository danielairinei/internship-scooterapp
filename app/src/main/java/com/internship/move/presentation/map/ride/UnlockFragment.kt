package com.internship.move.presentation.map.ride

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentUnlockBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class UnlockFragment : Fragment(R.layout.fragment_unlock) {

    private val binding by viewBinding(FragmentUnlockBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()

        binding.codePinView.doOnTextChanged { _, _, _, _ ->
            //unlock api call
            if (binding.codePinView.text.toString().length == SCOOTER_SERIAL_NUMBER_LENGTH){
                findNavController().navigate(UnlockFragmentDirections.actionUnlockFragmentToSuccessUnlockFragment())
            }
        }
    }

    private fun initListeners() {
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().navigate(UnlockFragmentDirections.actionUnlockFragmentToMapFragment())
        }

        binding.qrBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Not available", Toast.LENGTH_SHORT).show()
        }

        binding.nfcBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Not available", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val SCOOTER_SERIAL_NUMBER_LENGTH = 4
    }
}