package com.internship.move.presentation.map.ride

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.internship.move.R
import com.internship.move.databinding.FragmentUnlockBinding
import com.internship.move.presentation.map.viewmodel.MapViewModel
import com.internship.move.utils.constants.ERROR_TIME
import com.tapadoo.alerter.Alerter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UnlockFragment : Fragment(R.layout.fragment_unlock) {

    private val binding by viewBinding(FragmentUnlockBinding::bind)
    private val viewModel by sharedViewModel<MapViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initObserver()

        viewModel.findScooters(viewModel.getUserLocation().latitude, viewModel.getUserLocation().longitude)

        binding.codePinView.doOnTextChanged { _, _, _, _ ->
            val scooterNumber = binding.codePinView.text.toString()
            if (scooterNumber.length == SCOOTER_SERIAL_NUMBER_LENGTH) {
                viewModel.startRide(viewModel.getScooterByNumber(scooterNumber.toInt()), viewModel.getUserLocation())
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

    private fun initObserver() {
        viewModel.startRideData.observe(viewLifecycleOwner) { unlockedScooter ->
            if (unlockedScooter != null) {
                findNavController().navigate(UnlockFragmentDirections.actionUnlockFragmentToSuccessUnlockFragment())
            }
        }
        viewModel.errorData.observe(viewLifecycleOwner) { errorResponse ->
            if (errorResponse != null) {
                Alerter.create(requireActivity())
                    .setTitle(errorResponse.message)
                    .setTitleAppearance(R.style.AlertTitleAppearance)
                    .setDuration(ERROR_TIME)
                    .setBackgroundColorRes(R.color.error_alerter_background)
                    .show()
            }
        }
    }

    companion object {
        private const val SCOOTER_SERIAL_NUMBER_LENGTH = 4
    }
}
//UNLOCK CALL
//            if (binding.codePinView.text.toString().length == SCOOTER_SERIAL_NUMBER_LENGTH) {
//                viewModel.getScooterByNumber(binding.codePinView.text.toString().toInt())?.let {
//                    viewModel.unlockScooter(
//                        viewModel.getLoginToken(),
//                        it
//                    )
//                }
//            }