package com.raflisalam.generasigigih

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.Feature
import com.raflisalam.generasigigih.api.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportsViewModel: ViewModel() {
    private val disasterReports = MutableLiveData<List<Geometry>?>()
    private val dataDisasters = MutableLiveData<List<DisasterReports>?>()

/*
    fun fetchDisastersReports(timePeriod: Number) {
        ApiClient.instance.getDisasterReports(timePeriod).enqueue(object : Callback<DisasterResponse> {
            override fun onResponse(
                call: Call<DisasterResponse>,
                response: Response<DisasterResponse>
            ) {
                if (response.isSuccessful) {
                    val geometries = response.body()?.result?.objects?.output?.geometries
                    disasterReports.postValue(geometries)
                    dataDisasters.value = geometries?.map { it.disasterReports } ?:  emptyList()
                }
            }

            override fun onFailure(call: Call<DisasterResponse>, t: Throwable) {
                t.message?.let { Log.d("Fail Load!", it) }
            }
        })
    }
*/

    fun fetchDisastersBasendOnArea(regionCode: String, timePeriod: Number) {
        ApiClient.instance.getDisastersBasedOnArea(regionCode, timePeriod).enqueue(object : Callback<DisasterResponse> {
            override fun onResponse(
                call: Call<DisasterResponse>,
                response: Response<DisasterResponse>
            ) {
                if (response.isSuccessful) {
                    val geometries = response.body()?.result?.objects?.output?.geometries
                    disasterReports.postValue(geometries)
                    dataDisasters.value = geometries?.map { it.disasterReports } ?:  emptyList()
                }
            }

            override fun onFailure(call: Call<DisasterResponse>, t: Throwable) {
                t.message?.let { Log.d("Fail Load!", it) }
            }

        })
    }

    fun getDisasterCoordinates(): LiveData<List<Geometry>?> {
        return disasterReports
    }

    fun getDisastersReports(): LiveData<List<DisasterReports>?> {
        return dataDisasters
    }

}