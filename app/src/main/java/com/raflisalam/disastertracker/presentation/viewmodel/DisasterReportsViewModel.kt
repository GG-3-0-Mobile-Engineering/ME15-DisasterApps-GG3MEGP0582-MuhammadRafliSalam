package com.raflisalam.disastertracker.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raflisalam.disastertracker.common.Resource
import com.raflisalam.disastertracker.domain.model.DisasterReports
import com.raflisalam.disastertracker.domain.usecase.GetDisasterReportsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisasterReportsViewModel @Inject constructor(
    private val getDisasterReportsUseCase: GetDisasterReportsUseCase
) : ViewModel() {

    private val _disasterReports = MutableLiveData<Resource<List<DisasterReports>>?>()
    val disasterReports: LiveData<Resource<List<DisasterReports>>?>
        get() = _disasterReports

       fun fetchDisasterReports(regionCode: String?, disaster: String?, timePeriod: Number) {
        viewModelScope.launch {
            _disasterReports.value = Resource.Loading()
            try {
                getDisasterReportsUseCase.invoke(regionCode, disaster, timePeriod).collect {
                    _disasterReports.value = it
                }

            } catch (e: Exception) {
                _disasterReports.value = Resource.Error("Failed to fetch disaster reports")
            }
        }
    }

    fun pushNotificationFloodDepth(reportType: String, floodDepth: Int) {
        getDisasterReportsUseCase.checkAndSendPushNotification(reportType, floodDepth)
    }
}
