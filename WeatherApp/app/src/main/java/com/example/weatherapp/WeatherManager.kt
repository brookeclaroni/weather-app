package com.example.weatherapp

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class WeatherManager {

    private val okHttpClient: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()

        // Set up our OkHttpClient instance to log all network traffic to Logcat
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)

        builder.connectTimeout(15, TimeUnit.SECONDS)
        builder.readTimeout(15, TimeUnit.SECONDS)
        builder.writeTimeout(15, TimeUnit.SECONDS)

        okHttpClient = builder.build()
    }

    fun retrieveWeather(query : String, apiKey : String): Weather {
        val request = Request.Builder()
            .url("https://dataservice.accuweather.com/locations/v1/cities/search?apikey=$apiKey&q=$query\n")
            .method("GET", null)
            .build()

        val response = okHttpClient.newCall(request).execute()
        val responseString: String? = response.body?.string()

        if (!responseString.isNullOrEmpty() && response.isSuccessful) {
            val jsonArray = JSONArray(responseString)
            val jsonObject = jsonArray.getJSONObject(0)
            val locationKey = jsonObject.getString("Key")
            val cityName = jsonObject.getString("EnglishName")
            val adminArea = jsonObject.getJSONObject("AdministrativeArea")
            val stateName = adminArea.getString("ID")

            return retrieveWeatherByKey(locationKey,cityName, stateName, apiKey)
        }

        return Weather (
            city = "City",
            state = "State",
            temp = "00",
            humidity= "00",
            uv = "0",
            wind = "00",
            saved = false
        )
    }

    fun retrieveWeatherByKey(locationKey : String, cityName : String, stateName : String, apiKey : String): Weather {

        //val locationsKey = "327659"
        val request = Request.Builder()
            .url("https://dataservice.accuweather.com/currentconditions/v1/$locationKey?apikey=$apiKey&details=true\n")
            .method("GET", null)
            .build()

        val response = okHttpClient.newCall(request).execute()
        val responseString: String? = response.body?.string()
        var weather = Weather (
            city = "City",
            state = "State",
            temp = "00",
            humidity= "00",
            uv = "0",
            wind = "00",
            saved = false
        )

        if (!responseString.isNullOrEmpty() && response.isSuccessful) {
            val jsonArray = JSONArray(responseString)
            val jsonObject = jsonArray.getJSONObject(0)
            val humidityVal = jsonObject.getString("RelativeHumidity")
            val uvVal = jsonObject.getString("UVIndex")
            val temp = jsonObject.getJSONObject("Temperature")
            val tempImperial = temp.getJSONObject("Imperial")
            val tempImperialVal = tempImperial.getString("Value")
            val wind = jsonObject.getJSONObject("Wind")
            val windSpeed = wind.getJSONObject("Speed")
            val windSpeedImperial = windSpeed.getJSONObject("Imperial")
            val windSpeedImperialVal = windSpeedImperial.getString("Value")

            weather = Weather (
                city = "$cityName, $stateName",
                state = stateName,
                temp = tempImperialVal,
                humidity= humidityVal,
                uv = uvVal,
                wind = windSpeedImperialVal,
                saved = false
            )
        }
        return weather
    }
}