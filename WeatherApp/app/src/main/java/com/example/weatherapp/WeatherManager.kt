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
            val countryName = jsonObject.getJSONObject("Country").getString("EnglishName")

            return retrieveWeatherByKey(locationKey,cityName, stateName, countryName, apiKey)
        }

        return Weather (
            city = "City",
            locationKey = "000000",
            state = "State",
            country = "NA",
            temp = "00",
            humidity= "00",
            uv = "0",
            windImp = "00",
            windMet = "00",
            saved = false,
            tempMet = "00",
            tempImp = "00",
            sunIsOut = true,
            pressureImp = "00",
            visibilityImp = "00",
            pressureMet = "00",
            visibilityMet = "00",
            precip1hrImp = "00",
            precip1hrMet = "00"
        )
    }

    fun retrieveWeatherByKey(locationKey : String, cityName : String, stateName : String, countryName: String, apiKey : String): Weather {

        val request = Request.Builder()
            .url("https://dataservice.accuweather.com/currentconditions/v1/$locationKey?apikey=$apiKey&details=true\n")
            .method("GET", null)
            .build()

        val response = okHttpClient.newCall(request).execute()
        val responseString: String? = response.body?.string()
        var weather = Weather (
            city = "City",
            locationKey = "000000",
            state = "State",
            country = "NA",
            temp = "00",
            humidity= "00",
            uv = "0",
            windImp = "00",
            windMet = "00",
            saved = false,
            tempMet = "00",
            tempImp = "00",
            sunIsOut = true,
            pressureImp = "00",
            visibilityImp = "00",
            pressureMet = "00",
            visibilityMet = "00",
            precip1hrImp = "00",
            precip1hrMet = "00"
        )

        if (!responseString.isNullOrEmpty() && response.isSuccessful) {
            val jsonArray = JSONArray(responseString)
            val jsonObject = jsonArray.getJSONObject(0)
            val isDayTime = jsonObject.getBoolean("IsDayTime")
            // humidity
            val humidityVal = jsonObject.getString("RelativeHumidity")
            // uv
            val uvVal = jsonObject.getString("UVIndex")
            // temp
            val temp = jsonObject.getJSONObject("Temperature")
            val tempMetric = temp.getJSONObject("Metric")
            val tempMetricVal = tempMetric.getString("Value").toDouble().toInt()
            val tempImperial = temp.getJSONObject("Imperial")
            val tempImperialVal = tempImperial.getString("Value").toDouble().toInt()
            // wind
            val wind = jsonObject.getJSONObject("Wind")
            val windSpeed = wind.getJSONObject("Speed")
            val windSpeedImperial = windSpeed.getJSONObject("Imperial")
            val windSpeedImperialVal = windSpeedImperial.getString("Value")
            val windSpeedMetric = windSpeed.getJSONObject("Metric")
            val windSpeedMetricVal = windSpeedMetric.getString("Value")
            // pressure
            val pressure = jsonObject.getJSONObject("Pressure")
            val pressureImperial = pressure.getJSONObject("Imperial")
            val pressureMetric = pressure.getJSONObject("Metric")
            val pressureImperialVal = pressureImperial.getString("Value") + " " + pressureImperial.getString("Unit")
            val pressureMetricVal = pressureMetric.getString("Value") + " " + pressureMetric.getString("Unit")
            // visibility
            val visib = jsonObject.getJSONObject("Visibility")
            val visibMet = visib.getJSONObject("Metric")
            val visibImp = visib.getJSONObject("Imperial")
            val visibMetVal = visibMet.getString("Value") + " " + visibMet.getString("Unit")
            val visibImpVal = visibImp.getString("Value") + " " + visibImp.getString("Unit")
            // preciptation
            val precip = jsonObject.getJSONObject("Precip1hr")
            val precipMet = precip.getJSONObject("Metric")
            val precipImp = precip.getJSONObject("Imperial")
            val precipMetVal = precipMet.getString("Value") + " " + precipMet.getString("Unit")
            val precipImpVal = precipImp.getString("Value") + " " + precipImp.getString("Unit")




            weather = Weather (
                city = "$cityName, $stateName",
                locationKey = locationKey,
                state = stateName,
                country = countryName,
                tempMet = tempMetricVal.toString(),
                tempImp = tempImperialVal.toString(),
                humidity= humidityVal,
                uv = uvVal,
                windImp = windSpeedImperialVal,
                windMet = windSpeedMetricVal,
                saved = false,
                temp = tempImperialVal.toString(),
                sunIsOut = isDayTime,
                pressureImp = pressureImperialVal,
                pressureMet = pressureMetricVal,
                visibilityImp = visibImpVal,
                visibilityMet = visibMetVal,
                precip1hrImp = precipImpVal,
                precip1hrMet = precipMetVal
            )
        }
        return weather
    }

    fun retrieve5DayWeather(locationKey: String, apiKey: String, metric : Boolean): ArrayList<BriefWeather>{
        val request = Request.Builder()
            .url("https://dataservice.accuweather.com/forecasts/v1/daily/5day/$locationKey?apikey=$apiKey&details=true&metric=$metric\n")
            .method("GET", null)
            .build()

        val response = okHttpClient.newCall(request).execute()
        val responseString: String? = response.body?.string()

        val fiveDayDetail = ArrayList<BriefWeather>(5)
        val dummyBriefWeather = BriefWeather(
            date = "NA",
            tempMax = "NA",
            tempMin = "NA",
            aqi = "NA",
            dayCondition = "NA",
            dayPrecipProb = "NA",
            nightPrecipProb = "NA"
        )
        for (i in 1..5) {
            fiveDayDetail.add(dummyBriefWeather)
        }

        if (!responseString.isNullOrEmpty() && response.isSuccessful) {
            val jsonObject = JSONObject(responseString)
            val dailyForecasts = jsonObject.getJSONArray("DailyForecasts")
            for (i in 0..4) {
                val tempObject = dailyForecasts.getJSONObject(i)
                val temp = tempObject.getJSONObject("Temperature")
                val airAndPol = tempObject.getJSONArray("AirAndPollen")
                val day = tempObject.getJSONObject("Day")
                val night = tempObject.getJSONObject("Night")
                val tempBriefWeather = BriefWeather(
                    date = tempObject.getString("Date"),
                    tempMax = temp.getJSONObject("Maximum").getString("Value"),
                    tempMin = temp.getJSONObject("Minimum").getString("Value"),
                    aqi = airAndPol.getJSONObject(0).getString("Value"),
                    dayCondition = day.getString("IconPhrase"),
                    dayPrecipProb = day.getString("RainProbability") + "%",
                    nightPrecipProb = night.getString("RainProbability") + "%"
                )
                // fiveDayDetail.set(i, tempBriefWeather)
                fiveDayDetail[i] = tempBriefWeather
            }
        }
        return fiveDayDetail
    }
}