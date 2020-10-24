package com.example.weatherapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.doAsync

class DetailsActivity : AppCompatActivity() {

    private lateinit var detailsTextView: TextView
    private lateinit var day1TextView: TextView
    private lateinit var day2TextView: TextView
    private lateinit var day3TextView: TextView
    private lateinit var day4TextView: TextView
    private lateinit var day5TextView: TextView
    private lateinit var day1CondView: TextView
    private lateinit var day2CondView: TextView
    private lateinit var day3CondView: TextView
    private lateinit var day4CondView: TextView
    private lateinit var day5CondView: TextView
    private lateinit var day1TempView: TextView
    private lateinit var day2TempView: TextView
    private lateinit var day3TempView: TextView
    private lateinit var day4TempView: TextView
    private lateinit var day5TempView: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        day1TextView = findViewById(R.id.date1TextView)
        day2TextView = findViewById(R.id.date2TextView)
        day3TextView = findViewById(R.id.date3TextView)
        day4TextView = findViewById(R.id.date4TextView)
        day5TextView = findViewById(R.id.date5TextView)
        day1CondView = findViewById(R.id.day1Condition)
        day2CondView = findViewById(R.id.day2Condition)
        day3CondView = findViewById(R.id.day3Condition)
        day4CondView = findViewById(R.id.day4Condition)
        day5CondView = findViewById(R.id.day5Condition)
        day1TempView = findViewById(R.id.day1Temp)
        day2TempView = findViewById(R.id.day2Temp)
        day3TempView = findViewById(R.id.day3Temp)
        day4TempView = findViewById(R.id.day4Temp)
        day5TempView = findViewById(R.id.day5Temp)


        //get intent shared preference variables
        val preferences = getSharedPreferences("weather-app", Context.MODE_PRIVATE)
        val cityCode = preferences.getString("CURR_CITY", "327658")!!

        detailsTextView = findViewById(R.id.detailsTextView)

        doAsync {
            val weatherManager = WeatherManager()
            var currentWeather = weatherManager.retrieveWeather(cityCode, getString(R.string.api_key))
            var fiveDayDetail = weatherManager.retrieve5DayWeather(currentWeather.locationKey, getString(R.string.api_key))
            runOnUiThread {
                day1TextView.text = fiveDayDetail[0].date.substring(5,10)
                day2TextView.text = fiveDayDetail[1].date.substring(5,10)
                day3TextView.text = fiveDayDetail[2].date.substring(5,10)
                day4TextView.text = fiveDayDetail[3].date.substring(5,10)
                day5TextView.text = fiveDayDetail[4].date.substring(5,10)
                day1CondView.text = fiveDayDetail[0].dayCondition
                day2CondView.text = fiveDayDetail[1].dayCondition
                day3CondView.text = fiveDayDetail[2].dayCondition
                day4CondView.text = fiveDayDetail[3].dayCondition
                day5CondView.text = fiveDayDetail[4].dayCondition
                day1TempView.text = fiveDayDetail[0].tempMax + "/" + fiveDayDetail[0].tempMin
                day2TempView.text = fiveDayDetail[1].tempMax + "/" + fiveDayDetail[1].tempMin
                day3TempView.text = fiveDayDetail[2].tempMax + "/" + fiveDayDetail[2].tempMin
                day4TempView.text = fiveDayDetail[3].tempMax + "/" + fiveDayDetail[3].tempMin
                day5TempView.text = fiveDayDetail[4].tempMax + "/" + fiveDayDetail[4].tempMin

            }
        }
    }
}
