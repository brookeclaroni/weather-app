package com.example.weatherapp

data class Weather(
    val cityState: String,
    val locationKey: String,
    val city: String,
    val country: String,
    val lastUpdatedTime: String,
    val temp: String,
    val weatherText: String,
    val humidity: String,
    val uv: String,
    val uvStatus: String,
    val windImp: String,
    val windMet: String,
    val visibilityMet: String,
    val visibilityImp: String,
    val pressureMet: String,
    val pressureImp: String,
    val precip1hrMet: String,
    val precip1hrImp: String,
    var saved: Boolean,
    val tempMet: String,
    val tempImp: String,
    val realFeelTempMet: String,
    val realFeelTempImp: String,
    val sunIsOut: Boolean,
    val weatherInt: Int
)

data class BriefWeather(
    val date: String,
    val tempMax: String,
    val tempMin: String,
    val aqi: String,
    val dayCondition: String,
    val dayPrecipProb: String,
    val nightPrecipProb: String,
    val sunrise: String,
    val sunset: String
)