package com.raflisalam.generasigigih.utils

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
import com.raflisalam.generasigigih.data.DisasterArea
import java.io.IOException
import java.util.Locale

class LocationUtils(private val activity: FragmentActivity) {
    private lateinit var locationManager: LocationManager
    private lateinit var fusedLocation: FusedLocationProviderClient


    private var locationCallback: LocationCallback? = null

    interface LocationCallback {
        fun onLocationReceived(regionCode: String)
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
            val address: List<Address> = geocoder.getFromLocation(-6.200229, 106.849211, 1)!!
            for (region in address) {
                val city = region.adminArea
                regionCode = convertCityIntoRegionCode(city)
                locationCallback?.onLocationReceived(regionCode)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun convertCityIntoRegionCode(city: String?): String {
        return when (city) {
            "Aceh" -> DisasterArea.ACEH.value.code
            "Bali" -> DisasterArea.BALI.value.code
            "Kepulauan Bangka Belitung" -> DisasterArea.KEPULAUAN_BANGKA_BELITUNG.value.code
            "Banten" -> DisasterArea.BANTEN.value.code
            "Bengkulu" -> DisasterArea.BENGKULU.value.code
            "Jawa Tengah" -> DisasterArea.JAWA_TENGAH.value.code
            "Kalimantan Tengah" -> DisasterArea.KALIMANTAN_TENGAH.value.code
            "Central Sulawesi" -> DisasterArea.SULAWESI_TENGAH.value.code
            "Jawa Timur" -> DisasterArea.JAWA_TIMUR.value.code
            "East Kalimantan" -> DisasterArea.KALIMANTAN_TIMUR.value.code
            "East Nusa Tenggara" -> DisasterArea.NUSA_TENGGARA_TIMUR.value.code
            "Gorontalo" -> DisasterArea.GORONTALO.value.code
            "Daerah Khusus Ibukota Jakarta" -> DisasterArea.DKI_JAKARTA.value.code
            "Jambi" -> DisasterArea.JAMBI.value.code
            "Lampung" -> DisasterArea.LAMPUNG.value.code
            "Maluku" -> DisasterArea.MALUKU.value.code
            "North Kalimantan" -> DisasterArea.KALIMANTAN_UTARA.value.code
            "North Maluku" -> DisasterArea.MALUKU_UTARA.value.code
            "Sulawesi Utara" -> DisasterArea.SULAWESI_UTARA.value.code
            "Sumatera Utara" -> DisasterArea.SUMATERA_UTARA.value.code
            "Papua" -> DisasterArea.PAPUA.value.code
            "Riau" -> DisasterArea.RIAU.value.code
            "Kepulauan Riau" -> DisasterArea.KEPULAUAN_RIAU.value.code
            "South East Sulawesi" -> DisasterArea.SULAWESI_TENGGARA.value.code
            "South Kalimantan" -> DisasterArea.KALIMANTAN_SELATAN.value.code
            "South Sulawesi" -> DisasterArea.SULAWESI_SELATAN.value.code
            "South Sumatera" -> DisasterArea.SUMATERA_SELATAN.value.code
            "Daerah Istimewa Yogyakarta" -> DisasterArea.DI_YOGYAKARTA.value.code
            "Jawa Barat" -> DisasterArea.JAWA_BARAT.value.code
            "Kalimantan Barat" -> DisasterArea.KALIMANTAN_BARAT.value.code
            "West Nusa Tenggara" -> DisasterArea.NUSA_TENGGARA_BARAT.value.code
            "West Papua" -> DisasterArea.PAPUA_BARAT.value.code
            "West Sulawesi" -> DisasterArea.SULAWESI_BARAT.value.code
            "Sumatera Barat" -> DisasterArea.SUMATERA_BARAT.value.code
            else -> "invalid region code"
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

