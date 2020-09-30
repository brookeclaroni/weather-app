package com.example.weatherapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CitiesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var searchImageButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities)

        recyclerView = findViewById(R.id.cityRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        searchEditText = findViewById(R.id.searchEditText)
        searchImageButton = findViewById(R.id.searchImageButton)

        val preferences = getSharedPreferences("weather-app", Context.MODE_PRIVATE)

        /*
        var results : List<Weather> = listOf(
            Weather (
                city = "Reading",
                state = "State",
                temp = "59°F",
                humidity= "00",
                uv = "0",
                wind = "00",
                saved = false
            ),
            Weather (
                city = "Washington",
                state = "73",
                temp = "73°F",
                humidity= "00",
                uv = "0",
                wind = "00",
                saved = false
            ),
            Weather (
                city = "Boston",
                state = "State",
                temp = "61°F",
                humidity= "00",
                uv = "0",
                wind = "00",
                saved = false
            ),
            Weather (
                city = "Arlington",
                state = "State",
                temp = "69°F",
                humidity= "00",
                uv = "0",
                wind = "00",
                saved = false
            ),
            Weather (
                city = "Alexandria",
                state = "State",
                temp = "68°F",
                humidity= "00",
                uv = "0",
                wind = "00",
                saved = false
            ),
            Weather (
                city = "Rockville",
                state = "State",
                temp = "71°F",
                humidity= "00",
                uv = "0",
                wind = "00",
                saved = false
            ),
            Weather (
                city = "Baltimore",
                state = "State",
                temp = "68°F",
                humidity= "00",
                uv = "0",
                wind = "00",
                saved = false
            ),
            Weather (
                city = "Tampa",
                state = "State",
                temp = "81°F",
                humidity= "00",
                uv = "0",
                wind = "00",
                saved = false
            ),
            Weather (
                city = "Atlanta",
                state = "State",
                temp = "76°F",
                humidity= "00",
                uv = "0",
                wind = "00",
                saved = false
            )
        )
         */

        val cityNames = preferences.getStringSet("SAVED_CITIES", mutableSetOf())

        val adapter = CityAdapter(cityNames!!)
        recyclerView.adapter = adapter


        searchImageButton.setOnClickListener{


            preferences
                .edit()
                .putString("CURR_CITY", searchEditText.text.toString())
            .apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
