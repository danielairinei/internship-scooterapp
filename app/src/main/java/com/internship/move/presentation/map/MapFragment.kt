package com.internship.move.presentation.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
import com.google.maps.android.clustering.ClusterManager
import com.internship.move.R
import com.internship.move.data.dto.scooter.ScooterDto
import com.internship.move.databinding.FragmentMapBinding
import com.internship.move.presentation.map.adapter.RideBottomSheetDialogFragment
import com.internship.move.presentation.map.adapter.ScooterBottomSheetDialogFragment
import com.internship.move.presentation.map.adapter.ScooterPlace
import com.internship.move.presentation.map.adapter.ScooterPlaceRenderer
import com.internship.move.presentation.map.viewmodel.MapViewModel
import com.internship.move.utils.extensions.bitmapDescriptorFromVector
import com.internship.move.utils.extensions.getPhotoByBattery
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        binding.scooterBs.scooterBS.visibility = View.INVISIBLE

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
            findNavController().navigate(MapFragmentDirections.actionMapFragmentToMenuFragment())
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
        sharedViewModel.rideStarted.observe(viewLifecycleOwner) { scooter ->
            if (scooter) {
                showRideInfoBottomSheetDialog()
            }
        }
        viewModel.errorData.observe(viewLifecycleOwner) { errorResponse ->
            if (errorResponse != null) {
                Toast.makeText(requireContext(), errorResponse.message, Toast.LENGTH_SHORT).show()
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

        fetchLocation()
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

        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(position, DEFAULT_ZOOM_LEVEL))
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

    @Suppress("DEPRECATION")
    private fun getAddressFromLocation(position: LatLng): String? =
        Geocoder(requireContext(), Locale.getDefault()).getFromLocation(position.latitude, position.longitude, 1)?.get(0)?.thoroughfare

    private fun showBottomSheetDialog(currScooter: ScooterDto) {
        binding.scooterBs.scooterBS.visibility = View.INVISIBLE
        val bottomSheetDialogFragment = ScooterBottomSheetDialogFragment.newInstance(currScooter)

        bottomSheetDialogFragment.show(childFragmentManager, ScooterBottomSheetDialogFragment::class.java.canonicalName)
    }

    private fun showRideInfoBottomSheetDialog(){
        val infoBottomSheetDialogFragment = RideBottomSheetDialogFragment.newInstance()

        infoBottomSheetDialogFragment.show(childFragmentManager, RideBottomSheetDialogFragment::class.java.canonicalName)
    }

    companion object {
        private const val MAX_ZOOM_LEVEL = 19f
        private const val MIN_ZOOM_LEVEL = 6f
        private const val DEFAULT_ZOOM_LEVEL = 17f
    }
}