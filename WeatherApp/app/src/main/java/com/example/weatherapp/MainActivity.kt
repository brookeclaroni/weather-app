package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    //initialize lateinit variables
    private lateinit var searchButton: ImageButton
    private lateinit var offStarButton: ImageButton
    private lateinit var onStarButton: ImageButton
    private lateinit var cityTextView: TextView
    private lateinit var temperatureTextView: TextView
    private lateinit var humidityImageView: ImageView
    private lateinit var uvImageView: ImageView
    private lateinit var windImageView: ImageView
    private lateinit var humidityValueTextView: TextView
    private lateinit var uvValueTextView: TextView
    private lateinit var windValueTextView: TextView
    private lateinit var humidityTextView: TextView
    private lateinit var uvTextView: TextView
    private lateinit var windTextView: TextView
    private lateinit var moreDetailsButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //populate lateinit variables
        searchButton = findViewById(R.id.searchButton)
        offStarButton = findViewById(R.id.offStarButton)
        onStarButton = findViewById(R.id.onStarButton)
        cityTextView = findViewById(R.id.cityTextView)
        temperatureTextView = findViewById(R.id.temperatureTextView)
        humidityImageView = findViewById(R.id.humidityImageView)
        uvImageView = findViewById(R.id.uvImageView)
        windImageView = findViewById(R.id.windImageView)
        humidityValueTextView = findViewById(R.id.humidityValueTextView)
        uvValueTextView = findViewById(R.id.uvValueTextView)
        windValueTextView = findViewById(R.id.windValueTextView)
        humidityTextView = findViewById(R.id.humidityTextView)
        uvTextView = findViewById(R.id.uvTextView)
        windTextView = findViewById(R.id.windTextView)
        moreDetailsButton = findViewById(R.id.moreDetailsButton)

    }
}
