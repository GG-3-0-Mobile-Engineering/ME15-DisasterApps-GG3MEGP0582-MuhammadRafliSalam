package com.raflisalam.disastertracker.presentation.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.raflisalam.disastertracker.R
import com.raflisalam.disastertracker.common.Constant
import com.raflisalam.disastertracker.common.Resource
import com.raflisalam.disastertracker.common.utils.GetLocation
import com.raflisalam.disastertracker.common.utils.showToast
import com.raflisalam.disastertracker.data.local.DisasterTimePeriod
import com.raflisalam.disastertracker.databinding.ActivityHomeBinding
import com.raflisalam.disastertracker.domain.model.DisasterReports
import com.raflisalam.disastertracker.presentation.adapter.DisasterAdapter
import com.raflisalam.disastertracker.presentation.adapter.DiscoverAdapter
import com.raflisalam.disastertracker.presentation.viewmodel.DisasterReportsViewModel
import com.raflisalam.disastertracker.presentation.viewmodel.WeatherReportsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var getLocation: GetLocation
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var adapter: DiscoverAdapter

    private val reportsViewModel: DisasterReportsViewModel by viewModels()
    private val weatherViewModel: WeatherReportsViewModel by viewModels()

    private var regionCode: String? = null
    private var disasterType: String? = null
    private var defaultTimePeriod: Number = 604800
    private var selectedTimePeriod: Number = 604800

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showToast("Notifications permission granted")
            } else {
                showToast("Notifications permission rejected")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setupButton()
        setupTheme()
        setupMenu()
        requestPermissionAndGetLocation()
        fetchDisasterReports()
    }

    private fun setupMenu(): Number {
        binding.apply {
            val triggerPopup = filterPeriod
            val popupMenu = PopupMenu(this@HomeActivity, triggerPopup)

            popupMenu.menuInflater.inflate(R.menu.filter_period_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { periodItem ->
                when (periodItem.itemId) {
                    R.id.today_period -> {
                        selectedTimePeriod = DisasterTimePeriod.TODAY.periodInSec
                        reportsViewModel.fetchDisasterReports(regionCode, disasterType, selectedTimePeriod)
                        showToast("Info bencana hari ini")
                        true
                    }
                    R.id.three_days_period -> {
                        selectedTimePeriod = DisasterTimePeriod.THREE_DAYS.periodInSec
                        reportsViewModel.fetchDisasterReports(regionCode, disasterType, selectedTimePeriod)
                        showToast("Info bencana 3 hari terakhir")
                        true
                    }
                    R.id.one_weeks_period -> {
                        selectedTimePeriod = DisasterTimePeriod.ONE_WEEKS.periodInSec
                        reportsViewModel.fetchDisasterReports(regionCode, disasterType, selectedTimePeriod)
                        showToast("Info bencana 1 minggu terakhir")
                        true
                    }
                    else -> false
                }
            }
            triggerPopup.setOnClickListener {
                popupMenu.show()
            }
        }
        return selectedTimePeriod
    }

    private fun requestPermissionAndGetLocation() {
        getLocation = GetLocation(this)
        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
        getLocation.requestLocationUpdates(object : GetLocation.LocationCallback {
            override fun onLocationReceived(cityName: String) {
                binding.textLocation.text = cityName
                fetchWeatherReports(cityName)
            }
        })
    }

    private fun fetchDisasterReports() {
        reportsViewModel.fetchDisasterReports(regionCode, disasterType, selectedTimePeriod)
        reportsViewModel.disasterReports.observe(this) {
            when (it) {
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    val reports = it.data ?: emptyList()
                    initRecyclerView(reports)
                }
                null -> showToast("Not Found")
            }
        }
    }

    private fun initRecyclerView(reports: List<DisasterReports>) {
        adapter = DiscoverAdapter(reports)
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = DiscoverAdapter(reports)
        }
    }

    private fun fetchWeatherReports(cityName: String) {
        weatherViewModel.fetchWeatherReports(Constant.WEATHER_API_KEY, cityName)
        weatherViewModel.weatherReports.observe(this) {
            when (it) {
                is Resource.Error -> {
                    showToast(it.message.toString())
                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    val report = it.data
                    binding.apply {
                        textValueSuhu.text = "${report?.tempC}°C"
                        if (report?.tempC!! <= 19) {
                            textCondition.text = "Dingin"
                        } else {
                            textCondition.text = "Panas"
                        }
                        textValueBerawan.text = "${report?.clouds}%"
                        textValueAngin.text = "${report?.wind}mph"
                        textValueLembab.text = "${report?.humidity}%"
                    }
                }
                null -> showToast("Not Found")
            }
        }
    }

    private fun setupTheme() {
        sharedPref = getSharedPreferences(KEY_THEME, Context.MODE_PRIVATE)
        val themeMode = sharedPref.getInt(KEY_THEME, AppCompatDelegate.MODE_NIGHT_NO)
        AppCompatDelegate.setDefaultNightMode(themeMode)
    }

    private fun setupButton() {
        binding.apply {
            btnSettings.setOnClickListener {
                startActivity(Intent(this@HomeActivity, SettingsActivity::class.java))
            }

            btnShowMore.setOnClickListener {
                startActivity(Intent(this@HomeActivity, MapsActivity::class.java))
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        reportsViewModel.fetchDisasterReports(regionCode, disasterType, defaultTimePeriod)
        reportsViewModel.disasterReports.observe(this) {
            when (it) {
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    val report = it.data?.get(0)
                    if (report != null) {
                        val coordinates = report.coordinates
                        val markers = LatLng(coordinates.longitude, coordinates.latitude)
                        googleMap.addMarker(
                            MarkerOptions()
                                .position(markers)
                                .title(report.title)
                                .snippet("Bencana Terjadi Pada ${report.createdAt}")
                        )
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers, 14f))
                        val reportType = report.reportData.reportType
                        val floodDepth = report.reportData.floodDepth
                        reportsViewModel.pushNotificationFloodDepth(reportType, floodDepth)
                    }
                }
                null -> "Data Not Found"
            }
        }
    }

    companion object {
        private const val KEY_THEME = "theme_mode"
    }
}