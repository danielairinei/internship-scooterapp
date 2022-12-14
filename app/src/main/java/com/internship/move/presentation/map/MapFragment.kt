package com.internship.move.presentation.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.maps.android.clustering.ClusterManager
import com.internship.move.R
import com.internship.move.data.dto.ride.EndRideRequestDto
import com.internship.move.data.dto.ride.UpdateRideRequestDto
import com.internship.move.data.dto.ride.ViewRideRequestDto
import com.internship.move.data.dto.ride.ViewRideResponseDto
import com.internship.move.data.dto.scooter.ScooterDto
import com.internship.move.databinding.FragmentMapBinding
import com.internship.move.presentation.map.adapter.ScooterBottomSheetDialogFragment
import com.internship.move.presentation.map.adapter.ScooterPlace
import com.internship.move.presentation.map.adapter.ScooterPlaceRenderer
import com.internship.move.presentation.map.timer.RideTimer
import com.internship.move.presentation.map.timer.TimerService
import com.internship.move.presentation.map.viewmodel.MapViewModel
import com.internship.move.utils.constants.ERROR_TIME
import com.internship.move.utils.extensions.bitmapDescriptorFromVector
import com.internship.move.utils.extensions.getPhotoByBattery
import com.tapadoo.alerter.Alerter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {

    private val binding by viewBinding(FragmentMapBinding::bind)
    private val sharedViewModel by sharedViewModel<MapViewModel>()
    private val viewModel by viewModel<MapViewModel>()
    private var map: GoogleMap? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private val permissionsResultCallback = initPermissionsResultCallback()
    private lateinit var mapFragment: SupportMapFragment
    private var userLocation: Marker? = null
    private val rideTimer by lazy { RideTimer(requireContext(), requireActivity()) }
    private var userIsInRide = false
    private lateinit var updateTime: BroadcastReceiver
    private var buttonUnlockedStatus = true
    private var viewRideDetailsJob: Job? = null
    private var updateRideDetailsJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.scooterBs.scooterBS.visibility = View.INVISIBLE
        binding.rideInfo.rideInfoCollapsed.visibility = View.INVISIBLE
        binding.rideInfo.rideInfoExpanded.root.visibility = View.INVISIBLE

        initPermission()
        initListeners()
        initObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        map?.clear()
        map = null
        fusedLocationProviderClient = null
    }

    private fun initListeners() {
        binding.menuIV.setOnClickListener {
            if (userIsInRide) {
                Alerter.create(requireActivity())
                    .setTitle(getString(R.string.map_fragment_in_ride_menu_alert))
                    .setTitleAppearance(R.style.AlertTitleAppearance)
                    .setDuration(ERROR_TIME)
                    .setBackgroundColorRes(R.color.error_alerter_background)
                    .show()
            } else {
                findNavController().navigate(MapFragmentDirections.actionMapFragmentToMenuFragment())
            }
        }

        binding.locationAllowedIV.setOnClickListener {
            mapFragment.getMapAsync(this)
        }

        binding.locationNotAllowedIV.setOnClickListener {
            initPermission()
        }
    }

    private fun initObserver() {
        viewModel.scooterList.observe(viewLifecycleOwner) { scooters ->
            if (scooters != null) {
                addClusteredMarkers(scooters)
            }
        }

        sharedViewModel.startRideData.observe(viewLifecycleOwner) { scooter ->
            if (scooter != null) {
                startRide(scooter)
            }
        }

        sharedViewModel.unlockData.observe(viewLifecycleOwner) { isUnlocked ->
            if (isUnlocked != null) {
                initUnlockButton()
            }
        }

        sharedViewModel.rideInProgress.observe(viewLifecycleOwner) { rideInProgress ->
            if (rideInProgress == false && userIsInRide) {
                stopRide()
            }
        }

        sharedViewModel.viewRideData.observe(viewLifecycleOwner) { viewRideResponse ->
            if (viewRideResponse != null) {
                updateRideInfo(viewRideResponse)
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

    private fun initPermission() {
        val requestPermissionLauncher = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)

        if (requestPermissionLauncher != PackageManager.PERMISSION_GRANTED) {
            permissionsResultCallback.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            mapFragment.getMapAsync(this)
            permissionsEnabled()
        }
    }

    private fun initPermissionsResultCallback() = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permissionAreEnabled ->
        if (permissionAreEnabled) {
            permissionsEnabled()
        } else {
            permissionsDisabled()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap.apply {
            setMaxZoomPreference(MAX_ZOOM_LEVEL)
            setMinZoomPreference(MIN_ZOOM_LEVEL)
            setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.style_json))
        }

        lifecycleScope.launch {
            while (true) {
                fetchLocation()
                delay(UPDATE_LOCATION_DELAY)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocation() {
        val task: Task<Location>? = fusedLocationProviderClient?.lastLocation

        task?.addOnSuccessListener {
            if (it != null) {
                updateMap(it.latitude, it.longitude)
            }
        }
    }

    private fun updateMap(latitude: Double, longitude: Double) {
        viewModel.findScooters(latitude, longitude)

        val position = LatLng(latitude, longitude)
        viewModel.saveUserLocation(position)

        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(position, DEFAULT_ZOOM_LEVEL))
        userLocation?.remove()
        userLocation = map?.addMarker(
            MarkerOptions()
                .position(position)
                .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_user_location))
        )
    }

    private fun permissionsEnabled() {
        binding.locationAllowedIV.visibility = View.VISIBLE
        binding.locationNotAllowedIV.visibility = View.INVISIBLE
        binding.headingTV.setText(R.string.map_header_location_allowed)
    }

    private fun permissionsDisabled() {
        binding.headingTV.setText(R.string.map_header_location_not_allowed)
        binding.locationNotAllowedIV.visibility = View.VISIBLE
        binding.locationAllowedIV.visibility = View.INVISIBLE
    }

    private fun addClusteredMarkers(scooterList: List<ScooterDto>) {
        val scootersPlace = viewModel.getMarkerList(scooterList)
        val clusterManager = ClusterManager<ScooterPlace>(requireContext(), map)
        val clusterRenderer = ScooterPlaceRenderer(requireContext(), map!!, clusterManager)
        var lastMarker: Marker? = null

        clusterManager.renderer = clusterRenderer

        scootersPlace.forEach { place ->
            clusterManager.addItem(place)
            clusterManager.setOnClusterItemClickListener {
                lastMarker?.setIcon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_pin_not_selected))
                onMarkerClicked(clusterRenderer.getMarker(it))
                lastMarker = clusterRenderer.getMarker(it)
                true
            }
        }

        clusterManager.cluster()

        map?.apply {
            setOnMapClickListener {
                lastMarker?.setIcon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_pin_not_selected))
                binding.scooterBs.scooterBS.visibility = View.INVISIBLE
            }
            setOnCameraIdleListener {
                clusterManager.onCameraIdle()
            }
            setOnMarkerClickListener {
                true
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onMarkerClicked(scooterPlace: Marker) {
        map?.animateCamera(CameraUpdateFactory.newLatLng(scooterPlace.position))

        val currentScooter = viewModel.getScooterByNumber(scooterPlace.title?.toInt())

        scooterPlace.setIcon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_pin_selected))

        binding.scooterBs.scooterIdTV.text = "#${currentScooter?.number.toString()}"
        binding.scooterBs.batteryTV.text = "${currentScooter?.battery.toString()}%"
        binding.scooterBs.batteryIV.setImageResource(getPhotoByBattery(currentScooter?.battery))
        binding.scooterBs.locationTV.text = getAddressFromLocation(scooterPlace.position)
        binding.scooterBs.scooterBS.visibility = View.VISIBLE
        binding.scooterBs.unlockBtn.setOnClickListener {
            currentScooter ?: return@setOnClickListener
            showBottomSheetDialog(currentScooter)
        }
    }

    private fun getAddressFromLocation(position: LatLng): String? =
        Geocoder(requireContext(), Locale.getDefault()).getFromLocation(position.latitude, position.longitude, 1)?.get(0)?.thoroughfare

    private fun showBottomSheetDialog(currentScooter: ScooterDto) {
        binding.scooterBs.scooterBS.visibility = View.INVISIBLE

        val bottomSheetDialogFragment = ScooterBottomSheetDialogFragment.newInstance(currentScooter)

        bottomSheetDialogFragment.show(childFragmentManager, ScooterBottomSheetDialogFragment::class.java.canonicalName)
    }

    private fun startRide(scooterDto: ScooterDto) {
        userIsInRide = true

        val rideInfoCollapsedBehavior = BottomSheetBehavior.from(binding.rideInfo.rideInfoCollapsed)
        val rideInfoExpandedBehavior = BottomSheetBehavior.from(binding.rideInfo.rideInfoExpanded.rideInfoExpandedCL)
        updateTime = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
                binding.rideInfo.rideInfoExpanded.timeTV.text = rideTimer.getTimeStringFromDoubleExtended(time)
                binding.rideInfo.timeTV.text = rideTimer.getTimeStringFromDoubleCollapsed(time)
            }
        }
        val collapsedBottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                rideInfoExpandedBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    binding.rideInfo.rideInfoCollapsed.isVisible = !binding.rideInfo.rideInfoCollapsed.isVisible
                    binding.rideInfo.rideInfoExpanded.root.isVisible = !binding.rideInfo.rideInfoExpanded.root.isVisible
                    rideInfoExpandedBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
        val expandedBottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                rideInfoCollapsedBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.rideInfo.rideInfoCollapsed.isVisible = !binding.rideInfo.rideInfoCollapsed.isVisible
                    binding.rideInfo.rideInfoExpanded.root.isVisible = !binding.rideInfo.rideInfoExpanded.root.isVisible
                    rideInfoCollapsedBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }
        }

        rideTimer.startStopTimer()
        requireActivity().registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))

        binding.rideInfo.rideInfoCollapsed.animate().alpha(1.0f)
        binding.rideInfo.rideInfoCollapsed.visibility = View.VISIBLE
        rideInfoCollapsedBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        rideInfoCollapsedBehavior.addBottomSheetCallback(collapsedBottomSheetCallback)
        rideInfoExpandedBehavior.addBottomSheetCallback(expandedBottomSheetCallback)

        initRideInfoBottomSheet(scooterDto)

        viewRideDetailsJob = lifecycleScope.launch {
            while (true) {
                sharedViewModel.viewRide(ViewRideRequestDto(sharedViewModel.getCurrentRideId()))
                delay(RIDE_DATA_DELAY)
            }
        }

        updateRideDetailsJob = lifecycleScope.launch {
            while (true) {
                sharedViewModel.updateRide(
                    UpdateRideRequestDto(
                        scooterDto.id,
                        sharedViewModel.getUserLocation().longitude,
                        sharedViewModel.getUserLocation().latitude
                    )
                )
                delay(RIDE_DATA_DELAY)
            }
        }
    }

    private fun stopRide() {
        userIsInRide = false

        viewRideDetailsJob?.cancel()
        updateRideDetailsJob?.cancel()
        rideTimer.startStopTimer()
        activity?.unregisterReceiver(updateTime)
        binding.rideInfo.rideInfoCollapsed.visibility = View.INVISIBLE
        binding.rideInfo.rideInfoExpanded.root.visibility = View.INVISIBLE
    }

    private fun initRideInfoBottomSheet(scooterDto: ScooterDto) {
        binding.rideInfo.batteryTV.text = getString(R.string.scooter_bottom_sheet_battery_level, scooterDto.battery)
        binding.rideInfo.batteryIV.setImageResource(getPhotoByBattery(scooterDto.battery))
        binding.rideInfo.rideInfoExpanded.batteryTV.text = getString(R.string.scooter_bottom_sheet_battery_level, scooterDto.battery)
        binding.rideInfo.rideInfoExpanded.batteryHeadingIV.setImageResource(getPhotoByBattery(scooterDto.battery))
        binding.rideInfo.distanceTV.text = getString(R.string.ride_info_default_value)
        binding.rideInfo.rideInfoExpanded.distanceTV.text = getString(R.string.ride_info_default_value)

        binding.rideInfo.lockBtn.setOnClickListener {
            if (buttonUnlockedStatus) {
                println(scooterDto.id)
                sharedViewModel.lockScooter(scooterDto.id)
            } else {
                sharedViewModel.unlockScooter(scooterDto.id)
            }
        }

        binding.rideInfo.rideInfoExpanded.lockBtn.setOnClickListener {
            if (buttonUnlockedStatus) {
                sharedViewModel.lockScooter(scooterDto.id)
            } else {
                sharedViewModel.unlockScooter(scooterDto.id)
            }
        }

        binding.rideInfo.endRideBtn.setOnClickListener {
            sharedViewModel.endRide(
                EndRideRequestDto(
                    sharedViewModel.getCurrentRideId(),
                    viewModel.getUserLocation().longitude,
                    viewModel.getUserLocation().latitude
                )
            )
        }

        binding.rideInfo.rideInfoExpanded.endRideBtn.setOnClickListener {
            sharedViewModel.endRide(
                EndRideRequestDto(
                    sharedViewModel.getCurrentRideId(),
                    viewModel.getUserLocation().longitude,
                    viewModel.getUserLocation().latitude
                )
            )
        }
    }

    private fun initUnlockButton() {
        if (buttonUnlockedStatus) {
            binding.rideInfo.lockBtn.setIconResource(R.drawable.ic_unlock)
            binding.rideInfo.lockBtn.text = getString(R.string.ride_info_btn_unlock)
            binding.rideInfo.rideInfoExpanded.lockBtn.setIconResource(R.drawable.ic_unlock)
            binding.rideInfo.rideInfoExpanded.lockBtn.text = getString(R.string.ride_info_btn_unlock)
            buttonUnlockedStatus = false
        } else {
            binding.rideInfo.lockBtn.setIconResource(R.drawable.ic_lock)
            binding.rideInfo.lockBtn.text = getString(R.string.ride_info_btn_lock)
            binding.rideInfo.rideInfoExpanded.lockBtn.setIconResource(R.drawable.ic_lock)
            binding.rideInfo.rideInfoExpanded.lockBtn.text = getString(R.string.ride_info_btn_lock)
            buttonUnlockedStatus = true
        }
    }

    private fun updateRideInfo(viewRideResponseDto: ViewRideResponseDto) {
        binding.rideInfo.batteryTV.text = getString(R.string.scooter_bottom_sheet_battery_level, viewRideResponseDto.battery)
        binding.rideInfo.batteryIV.setImageResource(getPhotoByBattery(viewRideResponseDto.battery))
        binding.rideInfo.distanceTV.text = getString(R.string.ride_info_distance_text, viewRideResponseDto.distance / KEY_KILOMETER_VALUE)
        binding.rideInfo.rideInfoExpanded.batteryTV.text =
            getString(R.string.scooter_bottom_sheet_battery_level, viewRideResponseDto.battery)
        binding.rideInfo.rideInfoExpanded.batteryHeadingIV.setImageResource(getPhotoByBattery(viewRideResponseDto.battery))
        binding.rideInfo.rideInfoExpanded.distanceTV.text =
            getString(R.string.ride_info_distance_text, viewRideResponseDto.distance / KEY_KILOMETER_VALUE)
    }

    companion object {
        private const val UPDATE_LOCATION_DELAY = 10000L
        private const val RIDE_DATA_DELAY = 7000L
        private const val KEY_KILOMETER_VALUE = 1000F
        private const val MAX_ZOOM_LEVEL = 20f
        private const val MIN_ZOOM_LEVEL = 6f
        private const val DEFAULT_ZOOM_LEVEL = 17f
    }
}