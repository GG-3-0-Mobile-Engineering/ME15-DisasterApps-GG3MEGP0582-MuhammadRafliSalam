package com.raflisalam.generasigigih

import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.raflisalam.generasigigih.data.DisasterArea
import com.raflisalam.generasigigih.databinding.ActivityDisasterMapsBinding
import com.raflisalam.generasigigih.utils.LocationUtils
import com.raflisalam.generasigigih.utils.Utils

class DisasterMapsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDisasterMapsBinding
    private lateinit var reportsViewModel: ReportsViewModel
    private lateinit var adapterDisaster: DisasterAdapter
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var locationUtils: LocationUtils
    private lateinit var utils: Utils

    private val callback =  OnMapReadyCallback { googleMap ->
        setupMaps(googleMap)
        reportsViewModel.getDisasterCoordinates().observe(this) { reports ->
            googleMap.clear()
            reports?.forEach { data ->
                if (data.type == "Point") {
                    val coordinate = data.coordinates
                    if (coordinate.size >= 2) {
                        val latitude = coordinate[1]
                        val longitude = coordinate[0]
                        val markers = LatLng(latitude, longitude)
                        googleMap.addMarker(
                            MarkerOptions()
                                .position(markers)
                                .title(data.disasterReports.disasterType)
                                .snippet("Bencana terjadi pada ${data.disasterReports.createdAt}")
                        )
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers, 10f))
                    }
                } else if (data.type == "") {
                    val defaultPositionMarkers = LatLng(-2.2237827, 95.9284679)
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultPositionMarkers, 8f))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisasterMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(callback)
        utils = Utils()

        setupViewModel()
        setupButton()
        requestLocation()
        filterDisastersBasedOnArea()
    }

    private fun requestLocation() {
        locationUtils = LocationUtils(this)
        locationUtils.requestLocationUpdates(object: LocationUtils.LocationCallback {
            override fun onLocationReceived(regionCode: String) {
                reportsViewModel.fetchDisastersBasendOnArea(regionCode,604800)
            }
        })
    }

    private fun filterDisastersBasedOnArea() {
        binding.apply {
            searching.setOnClickListener {
                searching.isIconified = false
            }
            searching.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(regionName: String?): Boolean {
                    if (!regionName.isNullOrEmpty()) {
                        reportsViewModel.fetchDisastersBasendOnArea(convertToRegionCode(regionName), 604800)
                    }
                    searching.setQuery("", false)
                    searching.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }
    }

    private fun setupViewModel() {
        reportsViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ReportsViewModel::class.java]
        reportsViewModel.getDisastersReports().observe(this) { disasters ->
            if (disasters != null) {
                adapterDisaster = DisasterAdapter(disasters)
                setBottomSheetDialog(disasters)
            }
        }
    }

    private fun setBottomSheetDialog(dataDisasters: List<DisasterReports>) {
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
        val recyclerView = bottomSheetView.findViewById<RecyclerView>(R.id.rvDisaster)
        bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(bottomSheetView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DisasterAdapter(dataDisasters)
    }

    private fun setupButton() {
        binding.btnShowDisasters.setOnClickListener {
            bottomSheetDialog.show()
        }
    }

    private fun setupMaps(googleMap: GoogleMap) {
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isIndoorLevelPickerEnabled = true
        googleMap.uiSettings.isCompassEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = true
        setMapStyle(googleMap)
    }

    private fun setMapStyle(googleMap: GoogleMap) {
        try {
            val success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }

    private fun convertToRegionCode(regionName: String) : String {
        return when (regionName) {
            "Aceh" -> DisasterArea.ACEH.value.code
            "Bali" -> DisasterArea.BALI.value.code
            "Kepulauan Bangka Belitung" -> DisasterArea.KEPULAUAN_BANGKA_BELITUNG.value.code
            "Banten" -> DisasterArea.BANTEN.value.code
            "Bengkulu" -> DisasterArea.BENGKULU.value.code
            "Jawa Tengah" -> DisasterArea.JAWA_TENGAH.value.code
            "Kalimantan Tengah" -> DisasterArea.KALIMANTAN_TENGAH.value.code
            "Sulawesi Tengah" -> DisasterArea.SULAWESI_TENGAH.value.code
            "Jawa Timur" -> DisasterArea.JAWA_TIMUR.value.code
            "Kalimantan Timur" -> DisasterArea.KALIMANTAN_TIMUR.value.code
            "Nusa Tenggara Timur" -> DisasterArea.NUSA_TENGGARA_TIMUR.value.code
            "Gorontalo" -> DisasterArea.GORONTALO.value.code
            "DKI Jakarta" -> DisasterArea.DKI_JAKARTA.value.code
            "Jambi" -> DisasterArea.JAMBI.value.code
            "Lampung" -> DisasterArea.LAMPUNG.value.code
            "Maluku" -> DisasterArea.MALUKU.value.code
            "Kalimantan Utara" -> DisasterArea.KALIMANTAN_UTARA.value.code
            "Maluku Utara" -> DisasterArea.MALUKU_UTARA.value.code
            "Sulawesi Utara" -> DisasterArea.SULAWESI_UTARA.value.code
            "Sumatera Utara" -> DisasterArea.SUMATERA_UTARA.value.code
            "Papua" -> DisasterArea.PAPUA.value.code
            "Riau" -> DisasterArea.RIAU.value.code
            "Kepulauan Riau" -> DisasterArea.KEPULAUAN_RIAU.value.code
            "Sulawesi Tenggara" -> DisasterArea.SULAWESI_TENGGARA.value.code
            "Kalimantan Selata" -> DisasterArea.KALIMANTAN_SELATAN.value.code
            "Sulawesi Selatan" -> DisasterArea.SULAWESI_SELATAN.value.code
            "Sumatera Selatan" -> DisasterArea.SUMATERA_SELATAN.value.code
            "DI Yogyakarta" -> DisasterArea.DI_YOGYAKARTA.value.code
            "Jawa Barat" -> DisasterArea.JAWA_BARAT.value.code
            "Kalimantan Barat" -> DisasterArea.KALIMANTAN_BARAT.value.code
            "Nusa Tenggara Barat" -> DisasterArea.NUSA_TENGGARA_BARAT.value.code
            "Papua Barat" -> DisasterArea.PAPUA_BARAT.value.code
            "Sulawesi Barat" -> DisasterArea.SULAWESI_BARAT.value.code
            "Sumatera Barat" -> DisasterArea.SUMATERA_BARAT.value.code
            else -> "invalid region code"
        }
    }

    companion object {
        private const val TAG = "MapsActivity"
    }
}
