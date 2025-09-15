package com.example.retrofit.core.api.services

import com.example.retrofit.core.models.weather.current.WeatherCurrentResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String,
        @Query("aqi") aqi: String = "no"
    ): Response<WeatherCurrentResponse>



}