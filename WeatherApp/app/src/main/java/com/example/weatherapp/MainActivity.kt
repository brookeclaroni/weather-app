package com.example.weatherapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import org.jetbrains.anko.doAsync

class MainActivity : AppCompatActivity() {

    //initialize lateinit variables
    private lateinit var cityButton: ImageButton
    private lateinit var offStarButton: ImageButton
    private lateinit var onStarButton: ImageButton
    private lateinit var sunImageView: ImageView
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
        cityButton = findViewById(R.id.cityButton)
        offStarButton = findViewById(R.id.offStarButton)
        onStarButton = findViewById(R.id.onStarButton)
        sunImageView = findViewById(R.id.sunImageView)
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

        //get intent shared preference variables
        val preferences = getSharedPreferences("weather-app", Context.MODE_PRIVATE)
        val cityCode = preferences.getString("CURR_CITY", "327658")!!

//        var currentWeather = Weather (
//            city = "City",
//            locationKey = "000000",
//            state = "State",
//            country = "Country",
//            temp = "00",
//            humidity= "00",
//            uv = "0",
//            wind = "00",
//            saved = false,
//            tempMet = "00",
//            tempImp = "00"
//        )

        doAsync {
            val weatherManager = WeatherManager()
            val currentWeather = weatherManager.retrieveWeather(cityCode, getString(R.string.api_key))
            runOnUiThread {
                cityTextView.text = currentWeather.city
                temperatureTextView.text = getString(R.string.temperature, currentWeather.temp)
                humidityValueTextView.text = getString(R.string.humidity_value, currentWeather.humidity)
                uvValueTextView.text = currentWeather.uv
                windValueTextView.text = getString(R.string.wind_value, currentWeather.wind)

                //when star is clicked, behave accordingly
                //get sharedPreferences, get the string set of saved concerts
                val savedCitySet = preferences.getStringSet("SAVED_CITIES", mutableSetOf())

                //make the star the appropriate color
                if(savedCitySet!!.contains(currentWeather.city)) {

                    offStarButton.visibility =
                        View.GONE //if this is a saved result, make the yellow star appear
                }
                else  //if this is not a saved result, make the grey star appear
                    offStarButton.visibility = View.VISIBLE

                //if star is grey and then the user clicks to save
                offStarButton.setOnClickListener{

                    //change the color, object data, and update shared preferences
                    offStarButton.visibility = View.GONE
                    currentWeather.saved=true
                    savedCitySet?.add(currentWeather.city)
                    preferences.edit().putStringSet("SAVED_CITIES", savedCitySet).apply()

                }

                //if star is yellow and then the user clicks to unsave
                onStarButton.setOnClickListener{

                    //change the color, object data, and update shared preferences
                    offStarButton.visibility = View.VISIBLE
                    currentWeather.saved=false
                    savedCitySet?.remove(currentWeather.city)
                    preferences.edit().putStringSet("SAVED_CITIES", savedCitySet).apply()
                }
            }
        }

        //when city button is pressed, head to city activity
        cityButton.setOnClickListener{
            val intent = Intent(this, CitiesActivity::class.java)
            startActivity(intent)
        }

        //when details button is pressed, head to details activity
        moreDetailsButton.setOnClickListener{
            val intent = Intent(this, DetailsActivity::class.java)
            startActivity(intent)
        }
    }
}
