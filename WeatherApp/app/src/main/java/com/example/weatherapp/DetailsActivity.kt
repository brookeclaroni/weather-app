package com.example.weatherapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.jetbrains.anko.doAsync

class DetailsActivity : AppCompatActivity() {

    private lateinit var detailsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        //get intent shared preference variables
        val preferences = getSharedPreferences("weather-app", Context.MODE_PRIVATE)
        val cityCode = preferences.getString("CURR_CITY", "327658")!!

        detailsTextView = findViewById(R.id.detailsTextView)

        doAsync {
            val weatherManager = WeatherManager()
            var currentWeather = weatherManager.retrieveWeather(cityCode, getString(R.string.api_key))
            var fiveDayDetail = weatherManager.retrieve5DayWeather(currentWeather.locationKey, getString(R.string.api_key))
            runOnUiThread {

            }
        }
    }
}
