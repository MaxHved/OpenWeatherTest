package com.opemweather.test.presentation.picker

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.opemweather.test.R
import com.opemweather.test.databinding.FragmentPlacePickerBinding
import com.opemweather.test.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PlacePickerFragment : BaseFragment<FragmentPlacePickerBinding, PlacePickerViewModel>(),
    OnMapReadyCallback {

    companion object {
        fun newInstance(): PlacePickerFragment = PlacePickerFragment()
    }

    override val viewModel: PlacePickerViewModel by viewModels()
    private var googleMap: GoogleMap? = null
    private var marker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapsInitializer.initialize(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlacePickerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            toolbar.setNavigationOnClickListener { viewModel.onBackPressed() }
            mapView.onCreate(savedInstanceState)
            buttonPickPlace.isEnabled = marker != null
            buttonPickPlace.setOnClickListener {
                marker?.run { viewModel.pickPlace(position) }
            }
            if (googleMap == null) {
                mapView.getMapAsync(this@PlacePickerFragment)
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onDestroyView() {
        binding.mapView.onDestroy()
        googleMap = null
        super.onDestroyView()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Timber.i("onMapReady")
        this.googleMap = googleMap
        googleMap.uiSettings.isMapToolbarEnabled = true
        marker?.run {
            googleMap.addMarker(MarkerOptions().position(position))
        }
        googleMap.setOnMapClickListener(onMapClickListener)
        checkPermission()
    }

    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                enableMyLocationUI(true)
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                Toast.makeText(
                    requireContext(),
                    R.string.message_need_permission_find_your_location,
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean -> enableMyLocationUI(isGranted) }

    @SuppressLint("MissingPermission")
    private fun enableMyLocationUI(enable: Boolean) {
        googleMap?.apply {
            isMyLocationEnabled = enable
            uiSettings.isMyLocationButtonEnabled = enable
        }
    }

    private val onMapClickListener = GoogleMap.OnMapClickListener { latLng ->
        marker?.remove()
        marker = googleMap?.addMarker(MarkerOptions().position(latLng))
        with(binding) {
            buttonPickPlace.isEnabled = true
            labelCoordinates.text = "${latLng.latitude}, ${latLng.longitude}"
        }
    }

}