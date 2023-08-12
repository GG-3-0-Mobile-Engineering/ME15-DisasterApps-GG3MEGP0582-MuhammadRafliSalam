package com.raflisalam.disastertracker.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raflisalam.disastertracker.common.Resource
import com.raflisalam.disastertracker.domain.model.WeatherReports
import com.raflisalam.disastertracker.domain.usecase.weather.GetWeatherReportsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherReportsViewModel @Inject constructor(
    private val getWeatherReportsUseCase: GetWeatherReportsUseCase
): ViewModel() {

    private var _weatherReports = MutableLiveData<Resource<WeatherReports>?>()
    val weatherReports: LiveData<Resource<WeatherReports>?>
        get() = _weatherReports

    fun fetchWeatherReports(apiKey: String, location: String) {
        viewModelScope.launch {
            _weatherReports.value = Resource.Loading()
            try {
                getWeatherReportsUseCase.invoke(apiKey, location).collect {
                    _weatherReports.value = it
                }
            } catch (e: Exception) {
                _weatherReports.value = Resource.Error("Failed to fetch weather reports")
            }
        }
    }

}