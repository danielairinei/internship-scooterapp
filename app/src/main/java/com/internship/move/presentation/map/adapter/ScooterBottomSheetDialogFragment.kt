package com.internship.move.presentation.map.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.internship.move.data.dto.scooter.ScooterDto
import com.internship.move.databinding.DialogScooterDetailsBinding
import com.internship.move.presentation.map.MapFragmentDirections
import com.internship.move.utils.extensions.getPhotoByBattery

class ScooterBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = DialogScooterDetailsBinding.inflate(inflater, container, false)

        initDialog(binding)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun initDialog(binding: DialogScooterDetailsBinding) {
        binding.scooterIdTV.text = "#${arguments?.getInt(KEY_SCOOTER_NUMBER, 0).toString()}"
        binding.batteryTV.text = "${arguments?.getInt(KEY_SCOOTER_BATTERY, 0).toString()}%"
        binding.batteryIV.setImageResource(getPhotoByBattery(arguments?.getInt(KEY_SCOOTER_BATTERY)))

        binding.nfcBtn.setOnClickListener {
            Toast.makeText(requireContext(), KEY_FEATURE_NOT_AVAILABLE, Toast.LENGTH_SHORT).show()
        }

        binding.qrBtn.setOnClickListener {
            Toast.makeText(requireContext(), KEY_FEATURE_NOT_AVAILABLE, Toast.LENGTH_SHORT).show()
        }

        binding.codeBtn.setOnClickListener {
            dismiss()
            findNavController().navigate(MapFragmentDirections.actionMapFragmentToUnlockFragment())
        }
    }

    companion object {
        private const val KEY_FEATURE_NOT_AVAILABLE = "Not available"
        private const val KEY_SCOOTER_NUMBER = "KEY_SCOOTER_NUMBER"
        private const val KEY_SCOOTER_BATTERY = "KEY_SCOOTER_BATTERY"

        fun newInstance(scooterDto: ScooterDto): ScooterBottomSheetDialogFragment = ScooterBottomSheetDialogFragment().apply {
            val bundle = Bundle()

            bundle.putInt(KEY_SCOOTER_NUMBER, scooterDto.number)
            bundle.putInt(KEY_SCOOTER_BATTERY, scooterDto.battery)

            arguments = bundle
        }
    }
}