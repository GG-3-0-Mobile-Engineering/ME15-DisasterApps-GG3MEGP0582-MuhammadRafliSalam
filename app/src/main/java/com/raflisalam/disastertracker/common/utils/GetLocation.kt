package com.raflisalam.disastertracker.common.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.raflisalam.disastertracker.common.helper.Convert
import java.io.IOException
import java.util.Locale

class GetLocation (private val activity: FragmentActivity) {
    private lateinit var locationManager: LocationManager
    private lateinit var fusedLocation: FusedLocationProviderClient

    private var locationCallback: LocationCallback? = null

    interface LocationCallback {
        fun onLocationReceived(cityName: String)
    }

    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var regionCode: String = ""

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }

    fun requestLocationUpdates(callback: LocationCallback) {
        locationCallback = callback
        getCurrentLocation()
    }

    private fun getCurrentLocation() {
        locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (checkLocationPermission()) {
            fusedLocation = LocationServices.getFusedLocationProviderClient(activity)
            val location: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location != null) {
                latitude = location.latitude
                longitude = location.longitude
                getRegionCode(latitude, longitude)
            }
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun getRegionCode(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(activity, Locale.getDefault())
        try {
            val address: List<Address> = geocoder.getFromLocation(-6.196701, 106.852072, 1)!!
            for (region in address) {
                val cityName = region.subAdminArea
                locationCallback?.onLocationReceived(cityName)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    private fun checkLocationPermission(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)
    }
}

