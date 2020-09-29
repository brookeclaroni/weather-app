package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CitiesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities)

        recyclerView = findViewById(R.id.cityRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        var results : List<Weather> = listOf(
            Weather (
                city = "Reading",
                state = "State",
                temp = "59",
                humidity= "00",
                uv = "0",
                wind = "00"
            ),
            Weather (
                city = "Washington",
                state = "73",
                temp = "73",
                humidity= "00",
                uv = "0",
                wind = "00"
            ),
            Weather (
                city = "Boston",
                state = "State",
                temp = "61",
                humidity= "00",
                uv = "0",
                wind = "00"
            )
        )

        val adapter = CityAdapter(results)
        recyclerView.adapter = adapter
    }
}
