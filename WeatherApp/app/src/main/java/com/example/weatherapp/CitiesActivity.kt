package com.example.weatherapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CitiesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var searchImageButton: ImageButton
    private lateinit var settingButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities)

        recyclerView = findViewById(R.id.cityRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        searchEditText = findViewById(R.id.searchEditText)
        searchImageButton = findViewById(R.id.searchImageButton)
        settingButton = findViewById(R.id.settingButton)

        val preferences = getSharedPreferences("weather-app", Context.MODE_PRIVATE)
        val cityNames = preferences.getStringSet("SAVED_CITIES", mutableSetOf())

        val adapter = CityAdapter(cityNames!!)
        recyclerView.adapter = adapter


        searchImageButton.setOnClickListener{
            if (searchEditText.text.toString() == ""){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            if (searchEditText.text.toString() != "") {
                preferences
                    .edit()
                    .putString("CURR_CITY", searchEditText.text.toString())
                    .apply()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        settingButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
