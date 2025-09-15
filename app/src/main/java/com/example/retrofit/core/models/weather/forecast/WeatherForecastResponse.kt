package com.example.retrofit.core.models.weather.forecast

data class WeatherForecastResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)