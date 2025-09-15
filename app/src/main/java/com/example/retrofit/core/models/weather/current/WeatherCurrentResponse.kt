package com.example.retrofit.core.models.weather.current

data class WeatherCurrentResponse(
    val current: Current,
    val location: Location
)