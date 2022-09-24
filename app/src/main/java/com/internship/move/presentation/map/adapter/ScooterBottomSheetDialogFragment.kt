package com.internship.move.presentation.map.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.internship.move.R
import com.internship.move.data.dto.scooter.ScooterDto
import com.internship.move.databinding.ItemScooterBottomSheetBinding
import com.internship.move.utils.extensions.getPhotoByBattery

class ScooterBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = ItemScooterBottomSheetBinding.inflate(inflater, container, false)

        initDialog(binding)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun initDialog(binding: ItemScooterBottomSheetBinding) {
        binding.scooterIdTV.text = "#${arguments?.getInt(KEY_SCOOTER_NUMBER, 0).toString()}"
        binding.batteryTV.text = "${arguments?.getInt(KEY_SCOOTER_BATTERY, 0).toString()}%"
        binding.batteryIV.setImageResource(getPhotoByBattery(arguments?.getInt(KEY_SCOOTER_BATTERY)))

        binding.nfcBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Not available", Toast.LENGTH_SHORT).show()
        }

        binding.qrBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Not available", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

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