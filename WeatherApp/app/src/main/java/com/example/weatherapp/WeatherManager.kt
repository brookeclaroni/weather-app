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

    fun retrieveWeather(apiKey : String): Weather {

        val locationKey = "327659"
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
            wind = "00"
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
                city = "Washington",
                state = "DC",
                temp = tempImperialVal,
                humidity= humidityVal,
                uv = uvVal,
                wind = windSpeedImperialVal
            )
        }
        return weather
    }
}