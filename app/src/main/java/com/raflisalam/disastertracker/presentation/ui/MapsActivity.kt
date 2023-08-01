package com.raflisalam.disastertracker.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.raflisalam.disastertracker.R
import com.raflisalam.disastertracker.common.Resource
import com.raflisalam.disastertracker.databinding.ActivityMapsBinding
import com.raflisalam.disastertracker.presentation.adapter.DisasterAdapter
import com.raflisalam.disastertracker.presentation.viewmodel.DisasterReportsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapsBinding

    private lateinit var adapter: DisasterAdapter
    private lateinit var bottomSheetDialog: BottomSheetDialog

    private val reportsViewModel : DisasterReportsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        adapter = DisasterAdapter()

        setupButton()
        initBottomSheet()
    }

    private fun setupButton() {
        binding.apply {

            btnShowDisasters.setOnClickListener {
                bottomSheetDialog.show()
            }
        }
    }

    private fun initBottomSheet() {
        val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, null)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(view)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        setupMaps(googleMap)
        reportsViewModel.disasterReports.observe(this) {
            when (it) {
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    googleMap.clear()
                    val reports = it.data ?: emptyList()
                    adapter.setListDisasters(reports)
                    reports.forEach { data ->
                        val coordinates = data.coordinates
                        val markers = LatLng(coordinates.longitude, coordinates.latitude)
                        googleMap.addMarker(
                            MarkerOptions()
                                .position(markers)
                                .title(data.title)
                                .snippet("Bencana Terjadi Pada ${data.createdAt}")
                        )
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers, 14f))
                    }
                }
                else -> "Data Not Found"
            }
        }
    }

    private fun setupMaps(googleMap: GoogleMap) {
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = true
    }
}