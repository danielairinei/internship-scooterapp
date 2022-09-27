package com.internship.move.presentation.map.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.internship.move.databinding.ItemRideInfoBottomSheetBinding
import com.internship.move.presentation.map.viewmodel.MapViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RideBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private val sharedViewModel by sharedViewModel<MapViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = ItemRideInfoBottomSheetBinding.inflate(inflater, container, false)

        initDialog(binding)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun initDialog(binding: ItemRideInfoBottomSheetBinding) {
        binding.timeChronometer.start()
        binding.endRideBtn.setOnClickListener{
            sharedViewModel.endRide()
        }
    }

    companion object {
        fun newInstance(): RideBottomSheetDialogFragment = RideBottomSheetDialogFragment()
    }
}