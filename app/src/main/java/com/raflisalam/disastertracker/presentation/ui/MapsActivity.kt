package com.raflisalam.disastertracker.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.raflisalam.disastertracker.R
import com.raflisalam.disastertracker.common.Resource
import com.raflisalam.disastertracker.common.helper.Convert
import com.raflisalam.disastertracker.common.utils.showToast
import com.raflisalam.disastertracker.databinding.ActivityMapsBinding
import com.raflisalam.disastertracker.domain.model.DisasterReports
import com.raflisalam.disastertracker.presentation.adapter.DisasterAdapter
import com.raflisalam.disastertracker.presentation.viewmodel.DisasterReportsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapsBinding

    private lateinit var adapter: DisasterAdapter
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var mapFragment: SupportMapFragment

    private val reportsViewModel : DisasterReportsViewModel by viewModels()

    private var regionName: String? = null
    private var disasterType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setupButton()
        fetchDisasterReports()
        filterDisastersBasedOnArea()
        filterDisastersBasedOnType()
    }

    private fun fetchDisasterReports() {
        reportsViewModel.fetchDisasterReports(regionName, disasterType, 604800)
        reportsViewModel.disasterReports.observe(this) {
            when (it) {
                is Resource.Error -> {
                    binding.apply {
                        loadingProgress.visibility = View.GONE
                        mapFragment.view?.visibility = View.VISIBLE
                        materialCardView.visibility = View.VISIBLE
                        disasterTypeFilter.visibility = View.VISIBLE
                        btnShowDisasters.visibility = View.VISIBLE
                    }
                    showToast(it.message.toString())
                }
                is Resource.Loading -> {
                    binding.apply {
                        loadingProgress.visibility = View.VISIBLE
                        mapFragment.view?.visibility = View.INVISIBLE
                        materialCardView.visibility = View.INVISIBLE
                        disasterTypeFilter.visibility = View.INVISIBLE
                        btnShowDisasters.visibility = View.INVISIBLE
                    }
                }
                is Resource.Success -> {
                    val reports = it.data ?: emptyList()
                    binding.apply {
                        loadingProgress.visibility = View.GONE
                        mapFragment.view?.visibility = View.VISIBLE
                        materialCardView.visibility = View.VISIBLE
                        disasterTypeFilter.visibility = View.VISIBLE
                        btnShowDisasters.visibility = View.VISIBLE
                    }
                    initBottomSheet(reports)
                }

                else -> {
                    Resource.Error("Data Not Found", it?.message)
                }
            }
        }
    }

    private fun filterDisastersBasedOnType(): String? {
        binding.apply {
            disasterTypeFilter.setOnCheckedChangeListener { group, checkedId ->
                val chip: Chip? = group.findViewById(checkedId)
                val selectedChipDisaster = Convert.chipValuesToEnglish(chip?.text.toString())
                disasterType = selectedChipDisaster
                reportsViewModel.fetchDisasterReports(regionName, disasterType, 604800)
            }
            return disasterType
        }
    }

    private fun filterDisastersBasedOnArea(): String? {
        binding.apply {
            searching.setOnClickListener { searching.isIconified = false }
            searching.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!query.isNullOrEmpty()) {
                        regionName = query.lowercase(Locale.getDefault())
                        reportsViewModel.fetchDisasterReports(regionName, disasterType, 604800)
                    }
                    searching.setQuery("", false)
                    searching.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
            return regionName
        }
    }

    private fun setupButton() {
        binding.apply {
            btnShowDisasters.setOnClickListener {
                bottomSheetDialog.show()
            }

            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun initBottomSheet(reports: List<DisasterReports>) {
        val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, null)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        adapter = DisasterAdapter(reports)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DisasterAdapter(reports)

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