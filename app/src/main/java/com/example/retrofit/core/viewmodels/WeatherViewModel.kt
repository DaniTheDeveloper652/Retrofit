package com.example.retrofit.core.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofit.core.api.Constant
import com.example.retrofit.core.api.NetworkResponse
import com.example.retrofit.core.api.RetrofitInstance
import com.example.retrofit.core.models.weather.current.WeatherCurrentResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult =
        MutableStateFlow<NetworkResponse<WeatherCurrentResponse>>(NetworkResponse.Loading)
    private val response = _weatherResult.value
    val weatherResult: StateFlow<NetworkResponse<WeatherCurrentResponse>> = _weatherResult

    fun getData(city: String) {
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherApi.getCurrentWeather(Constant.apikey, city)
                if (response.isSuccessful && response.body() != null) {
                    _weatherResult.value = NetworkResponse.Success(response.body()!!)
                } else {
                    _weatherResult.value = NetworkResponse.Error("Failed to load")
                }
            } catch (e: Exception) {
                Log.d("384284234242342", "getData: error: ${e.message}")
                _weatherResult.value = NetworkResponse.Error("EXCEPTION occur: ${e.message}")
            }
        }
    }
}