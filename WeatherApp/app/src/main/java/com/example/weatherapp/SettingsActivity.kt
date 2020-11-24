package com.example.weatherapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Switch

class SettingsActivity : AppCompatActivity() {
    private lateinit var backButton: ImageButton
    private lateinit var timeSwitch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        backButton = findViewById(R.id.settingBackButton)
        timeSwitch = findViewById(R.id.timeFormatSwitch)

        // link preference mapping
        val preferences = getSharedPreferences("weather-app", Context.MODE_PRIVATE)
        val timeFormat = preferences.getBoolean("USE_24_H", true)

        timeSwitch.isChecked = timeFormat

        timeSwitch.setOnCheckedChangeListener { _ , isChecked ->
            //if 24h is requested
            if(isChecked) {
                preferences.edit().putBoolean("USE_24_H", true).apply()
            }
            //if 12h is requested
            else {
                preferences.edit().putBoolean("USE_24_H", false).apply()
            }
        }

        backButton.setOnClickListener {
            val intent = Intent(this, CitiesActivity::class.java)
            startActivity(intent)
        }
    }
}