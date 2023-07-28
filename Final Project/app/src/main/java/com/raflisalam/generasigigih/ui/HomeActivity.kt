package com.raflisalam.generasigigih.ui

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.raflisalam.generasigigih.DisasterMapsActivity
import com.raflisalam.generasigigih.R
import com.raflisalam.generasigigih.ReportsViewModel
import com.raflisalam.generasigigih.databinding.ActivityHomeBinding
import com.raflisalam.generasigigih.utils.LocationUtils
import com.raflisalam.generasigigih.utils.NotificationUtils

class HomeActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var sharedPref: SharedPreferences
    private lateinit var reportsViewModel: ReportsViewModel
    private lateinit var locationUtils: LocationUtils

    private var regionCode: String = ""
    private var disasterType: String = "flood"
    private var timePeriod: Number = 604800

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notifications permission rejected", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        setupButton()
        setupTheme()
        setupViewModel()
        checkLocation()
        getDataForPushNotification()
    }

    private fun checkLocation() {
        locationUtils = LocationUtils(this)
        locationUtils.requestLocationUpdates(object : LocationUtils.LocationCallback {
            override fun onLocationReceived(regionCode: String) {
                this@HomeActivity.regionCode = regionCode
                reportsViewModel.fetchDisastersBasendOnType(regionCode, disasterType, timePeriod)
            }
        })
    }

    private fun getDataForPushNotification() {
        reportsViewModel.getDisastersReports().observe(this) { reports ->
            if (reports != null) {
                for (data in reports) {
                    val floods_location = data.tags.instanceRegionCode
                    val report_data = data.reportData.reportType
                    val flood_depth = data.reportData.flood_depth
                    pushNotification(floods_location, report_data, flood_depth)
                }
            }
        }
    }

    private fun pushNotification(floodsLocation: String?, reportData: String, floodDepth: Int) {
        if (regionCode == floodsLocation) {
            if (reportData == "flood") {
                NotificationUtils.checkAndSendPushNotification(this, reportData, floodDepth)
            }
        }
    }


    private fun setupViewModel() {
        reportsViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ReportsViewModel::class.java]
    }

    private fun setupButton() {
        binding.apply {
            btnSettings.setOnClickListener {
                startActivity(Intent(this@HomeActivity, SettingsActivity::class.java))
            }

            btnMoreDisaster.setOnClickListener {
                startActivity(Intent(this@HomeActivity, DisasterMapsActivity::class.java))
            }
        }
    }

    private fun setupTheme() {
        sharedPref = getSharedPreferences(KEY_THEME, Context.MODE_PRIVATE)
        val themeMode = sharedPref.getInt(KEY_THEME, AppCompatDelegate.MODE_NIGHT_NO)
        AppCompatDelegate.setDefaultNightMode(themeMode)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        setMapStyle(googleMap)
        val latitude = -6.200229
        val longitude = 106.849211
        val markers = LatLng(latitude, longitude)
        googleMap.addMarker(
            MarkerOptions()
                .position(markers)
                .title("Banjir")
                .snippet("Bencana terjadi pada Senin, 28 Juli 2023")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers, 14f))
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

    companion object {
        private const val TAG = "HomeActivity"
        private const val KEY_THEME = "theme_mode"
        private const val KEY_LOCATION = "coordinate_user"
    }

}