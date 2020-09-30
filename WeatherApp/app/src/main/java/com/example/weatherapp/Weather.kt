package com.example.weatherapp

data class Weather (
    val city: String,
    val state: String,
    val temp: String,
    val humidity: String,
    val uv: String,
    val wind: String,
    var saved: Boolean
)