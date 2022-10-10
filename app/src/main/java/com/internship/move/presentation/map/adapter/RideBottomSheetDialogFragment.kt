//package com.internship.move.presentation.map.adapter
//
//import android.annotation.SuppressLint
//import android.app.Dialog
//import android.content.res.Resources
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.coordinatorlayout.widget.CoordinatorLayout
//import com.google.android.material.bottomsheet.BottomSheetBehavior
//import com.google.android.material.bottomsheet.BottomSheetDialog
//import com.google.android.material.bottomsheet.BottomSheetDialogFragment
//import com.internship.move.R
//import com.internship.move.data.dto.ride.EndRideRequestDto
//import com.internship.move.databinding.ItemRideInfoBottomSheetBinding
//import com.internship.move.presentation.map.viewmodel.MapViewModel
//import org.koin.androidx.viewmodel.ext.android.sharedViewModel
//
//class RideBottomSheetDialogFragment : BottomSheetDialogFragment() {
//
//    private val sharedViewModel by sharedViewModel<MapViewModel>()
//    private lateinit var behavior: BottomSheetBehavior<View>
//    private lateinit var binding: ItemR
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return super.onCreateDialog(savedInstanceState) as BottomSheetDialog
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        binding = ItemRideInfoBottomSheetBinding.inflate(inflater, container, false)
//
//        return binding.bottomSheet
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        behavior = BottomSheetBehavior.from(binding.bottomSheet)
//        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
//
//        val layout : CoordinatorLayout? = dialog?.findViewById(R.id.bottomSheet)
//
//        layout?.minimumHeight = Resources.getSystem().displayMetrics.heightPixels
//    }
//
//    @SuppressLint("SetTextI18n")
//    private fun initDialog(binding: ItemRideInfoBottomSheetBinding) {
//        binding.timeChronometer.start()
//        binding.endRideBtn.setOnClickListener {
//            sharedViewModel.endRide(
//                EndRideRequestDto(
//                    sharedViewModel.getCurrentScooterInRideId(),
//                    sharedViewModel.getUserLocation().longitude,
//                    sharedViewModel.getUserLocation().latitude
//                )
//            )
//        }
//    }
//
//    companion object {
//        fun newInstance(): RideBottomSheetDialogFragment = RideBottomSheetDialogFragment()
//    }
//}