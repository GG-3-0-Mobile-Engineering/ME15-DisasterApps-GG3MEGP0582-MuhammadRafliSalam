package com.raflisalam.generasigigih

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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
import com.google.android.material.chip.Chip
import com.raflisalam.generasigigih.data.DisasterArea
import com.raflisalam.generasigigih.databinding.ActivityDisasterMapsBinding
import com.raflisalam.generasigigih.ui.SettingsActivity
import com.raflisalam.generasigigih.utils.LocationUtils
import com.raflisalam.generasigigih.utils.Utils

class DisasterMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityDisasterMapsBinding
    private lateinit var reportsViewModel: ReportsViewModel
    private lateinit var adapterDisaster: DisasterAdapter
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var locationUtils: LocationUtils
    private lateinit var sharedPref: SharedPreferences
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var utils: Utils
    private val handler = Handler()

    private var regionCode: String = ""
    private var disasterType: String = ""
    private var timePeriod: Number = 86400

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisasterMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        utils = Utils()

        setLoadMaps()
        setupViewModel()
        setupTheme()
        setupButton()
        requestLocation()
        filterDisastersBasedOnArea()
        filterDisastersBasedOnType()

    }

    private fun setLoadMaps() {
        binding.apply {
            //Proses Load Maps
            loadingProgress.visibility = View.VISIBLE
            mapFragment.view?.visibility = View.INVISIBLE
            materialCardView.visibility = View.INVISIBLE
            materialCardView2.visibility = View.INVISIBLE
            chipGroupFilter.visibility = View.INVISIBLE
            btnShowDisasters.visibility = View.INVISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        setPeriodTimeDisasters()
    }

    private fun setPeriodTimeDisasters() {
        val periodDisasters = resources.getStringArray(R.array.daftar_periode_bencana)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, periodDisasters)
        binding.apply {
            autoCompleteTextView.setAdapter(arrayAdapter)
            autoCompleteTextView.setOnItemClickListener { adapterView, view, position, id ->
                val selectedPeriod = adapterView.getItemAtPosition(position).toString()
                Log.d("PERIOD_TERBARU", selectedPeriod)
                timePeriod = getDropdownPeriod(selectedPeriod)
                Log.d("PERIOD 2", timePeriod.toString())
                reportsViewModel.fetchDisastersBasendOnType(regionCode, disasterType, timePeriod)
            }
        }
    }

    private fun getDropdownPeriod(selectedPeriod: String): Number {
        return when (selectedPeriod) {
            "1 Hari" -> 86400
            "3 Hari" -> 259200
            "7 Hari" -> 604800
            else -> 0
        }
    }

    private fun setupTheme() {
        sharedPref = getSharedPreferences(KEY_THEME, Context.MODE_PRIVATE)
        val themeMode = sharedPref.getInt(KEY_THEME, AppCompatDelegate.MODE_NIGHT_NO)
        AppCompatDelegate.setDefaultNightMode(themeMode)
    }

    private fun requestLocation() {
        locationUtils = LocationUtils(this)
        locationUtils.requestLocationUpdates(object: LocationUtils.LocationCallback {
            override fun onLocationReceived(regionCode: String) {
                this@DisasterMapsActivity.regionCode = regionCode
                reportsViewModel.fetchDisastersBasendOnArea(regionCode, timePeriod)
            }
        })
    }

    private fun filterDisastersBasedOnType(): String {
        binding.chipGroupFilter.setOnCheckedChangeListener { group, checkedId ->
            val chip: Chip? = group.findViewById(checkedId)
            val selectedChipDisasters = convertToEnglish(chip?.text.toString())
            disasterType = selectedChipDisasters
            reportsViewModel.fetchDisastersBasendOnType(regionCode, disasterType, timePeriod)
        }
        return disasterType
    }

    private fun convertToEnglish(chip: String): String {
        return when (chip) {
            "Banjir" -> "flood"
            "Badai" -> "wind"
            "Gempa" -> "earthquake"
            "Kabut" -> "haze"
            "Kebakaran" -> "fire"
            "Gunung Berapi" -> "volcano"
            else -> "Invalid chip"
        }
    }

    private fun filterDisastersBasedOnArea(): String {
        binding.apply {
            searching.setOnClickListener {
                searching.isIconified = false
            }
            searching.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(regionName: String?): Boolean {
                    if (!regionName.isNullOrEmpty()) {
                        val convertRegionName =  convertToRegionCode(regionName)
                        regionCode = convertRegionName
                        reportsViewModel.fetchDisastersBasendOnArea(convertRegionName, timePeriod)
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
        return regionCode
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

        binding.btnSetting.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        setupMaps(googleMap)
        binding.apply {
            handler.postDelayed({
                loadingProgress.visibility = View.INVISIBLE
                mapFragment.view?.visibility = View.VISIBLE
                materialCardView.visibility = View.VISIBLE
                materialCardView2.visibility = View.VISIBLE
                chipGroupFilter.visibility = View.VISIBLE
                btnShowDisasters.visibility = View.VISIBLE
            }, 2000)
        }

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
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers, 14f))
                    }
                } else if (data.type == "") {
                    val defaultPositionMarkers = LatLng(-2.2237827, 95.9284679)
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultPositionMarkers, 14f))
                }
            }
        }
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
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
        private const val KEY_THEME = "theme_mode"
    }
}
