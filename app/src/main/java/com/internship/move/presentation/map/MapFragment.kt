package com.internship.move.presentation.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
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
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import com.internship.move.R
import com.internship.move.databinding.FragmentMapBinding
import com.internship.move.presentation.map.viewmodel.MapViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {

    private val binding by viewBinding(FragmentMapBinding::bind)
    private val viewModel by viewModel<MapViewModel>()
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mapFragment: SupportMapFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        permissionInit()
        initListeners()
    }

    private fun initListeners() {
        binding.menuIV.setOnClickListener {
            findNavController().navigate(MapFragmentDirections.actionMapFragmentToMenuFragment())
        }
        binding.locationAllowedTV.setOnClickListener {
            fetchLocation()
        }

        binding.locationNotAllowedIV.setOnClickListener {
            permissionInit()
        }
    }

    private fun permissionInit() {
        val requestPermissionLauncher = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)

        if (requestPermissionLauncher != PackageManager.PERMISSION_GRANTED) {
            permissionsResultCallback.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            permissionsEnabled()
            mapFragment.getMapAsync(this)
        }
    }

    private val permissionsResultCallback = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            permissionsEnabled()
        } else {
            permissionsDisabled()
            Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.setMaxZoomPreference(MAX_ZOOM_LEVEL)
        map.setMinZoomPreference(MIN_ZOOM_LEVEL)
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.style_json))

        fetchLocation()
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocation() {
        val task: Task<Location> = fusedLocationProviderClient.lastLocation

        task.addOnSuccessListener {
            if (it != null) {
                updateMap(it.latitude, it.longitude)
            }
        }
    }

    private fun updateMap(latitude: Double, longitude: Double) {
        val position = LatLng(latitude, longitude)

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, DEFAULT_ZOOM_LEVEL))
        map.addMarker(
            MarkerOptions()
                .position(position)
                .title("Your location")
                .icon(bitmapDescriptorFromVector(requireContext(),R.drawable.ic_user_location))
        )
    }

    private fun permissionsEnabled() {
        binding.locationAllowedTV.visibility = View.VISIBLE
        binding.locationNotAllowedIV.visibility = View.INVISIBLE
        binding.headingTV.setText(R.string.map_header_location_allowed)
    }

    private fun permissionsDisabled() {
        binding.headingTV.setText(R.string.map_header_location_not_allowed)
        binding.locationNotAllowedIV.visibility = View.VISIBLE
        binding.locationAllowedTV.visibility = View.INVISIBLE
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    companion object {
        private const val MAX_ZOOM_LEVEL = 19f
        private const val MIN_ZOOM_LEVEL = 6f
        private const val DEFAULT_ZOOM_LEVEL = 17f
    }
}