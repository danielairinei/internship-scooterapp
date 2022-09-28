package com.internship.move.presentation.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
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
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.internship.move.R
import com.internship.move.databinding.FragmentMapBinding
import com.internship.move.presentation.map.viewmodel.MapViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {

    private val binding by viewBinding(FragmentMapBinding::bind)
    private val viewModel by viewModel<MapViewModel>()
    private var map: GoogleMap? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private val permissionsResultCallback = initPermissionsResultCallback()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        permissionInit(mapFragment)
        initListeners()
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
        binding.locationIV.setOnClickListener {
            fetchLocation()
        }
    }

    private fun initPermissionsResultCallback() = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permissionAllowed ->
        if (permissionAllowed) {
            binding.locationIV.setImageResource(R.drawable.ic_focus_location)
            binding.headingTV.setText(R.string.map_header_location_allowed)
        } else {
            binding.headingTV.setText(R.string.map_header_location_not_allowed)
            binding.locationIV.setBackgroundResource(R.drawable.ic_allow_location)
            Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun permissionInit(mapFragment: SupportMapFragment) {
        val requestPermissionLauncher = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)

        if (requestPermissionLauncher != PackageManager.PERMISSION_GRANTED) {
            permissionsResultCallback.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            binding.locationIV.setImageResource(R.drawable.ic_focus_location)
            binding.headingTV.setText(R.string.map_header_location_allowed)
            mapFragment.getMapAsync(this)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map?.setMaxZoomPreference(MAX_ZOOM_LEVEL)
        map?.setMinZoomPreference(MIN_ZOOM_LEVEL)
        map?.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.style_json))

        fetchLocation()
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocation() {
        val task: Task<Location>? = fusedLocationProviderClient?.lastLocation

        task?.addOnSuccessListener { userLocation ->
            if (userLocation != null) {
                updateMap(userLocation.latitude, userLocation.longitude)
            }
        }
    }

    private fun updateMap(latitude: Double, longitude: Double) {
        val position = LatLng(latitude, longitude)

        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(position, DEFAULT_ZOOM_LEVEL))
        map?.addMarker(MarkerOptions().position(position).title("Your location"))
    }

    companion object {
        private const val MAX_ZOOM_LEVEL = 19f
        private const val MIN_ZOOM_LEVEL = 6f
        private const val DEFAULT_ZOOM_LEVEL = 17f
    }
}