package com.example.weatherapp

data class Weather(
    val city: String,
    val locationKey: String,
    val state: String,
    val country: String,
    val temp: String,
    val humidity: String,
    val uv: String,
    val wind: String,
    var saved: Boolean,
    val tempMet: String,
    val tempImp: String
)

data class BriefWeather(
    val date: String,
    val tempMax: String,
    val tempMin: String,
    val aqi: String,
    val dayCondition: String
)