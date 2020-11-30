package com.example.weatherapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import org.jetbrains.anko.doAsync
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

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
    private lateinit var day1AqiView: TextView
    private lateinit var day2AqiView: TextView
    private lateinit var day3AqiView: TextView
    private lateinit var day4AqiView: TextView
    private lateinit var day5AqiView: TextView
    private  lateinit var day1ImageView: ImageView
    private  lateinit var day2ImageView: ImageView
    private  lateinit var day3ImageView: ImageView
    private  lateinit var day4ImageView: ImageView
    private  lateinit var day5ImageView: ImageView
    private  lateinit var backButton: ImageButton
    private lateinit var detailsBackground : ConstraintLayout
    private lateinit var progBar: ProgressBar
    private lateinit var lineChartView: LineChart
    private lateinit var xAxisDate1: TextView
    private lateinit var xAxisDate2: TextView
    private lateinit var xAxisDate3: TextView
    private lateinit var xAxisDate4: TextView
    private lateinit var xAxisDate5: TextView
    private lateinit var humidProb: TextView
    private lateinit var wind: TextView
    private lateinit var uv: TextView
    private lateinit var precipValue: TextView
    private lateinit var pressure: TextView
    private lateinit var visibility: TextView
    private lateinit var lastUpdatedTime: TextView
    var day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    var date = Calendar.getInstance().get(Calendar.DATE)

    // hourly var
    private lateinit var hour1TempView: TextView
    private lateinit var hour2TempView: TextView
    private lateinit var hour3TempView: TextView
    private lateinit var hour4TempView: TextView
    private lateinit var hour5TempView: TextView
    private lateinit var hour6TempView: TextView
    private lateinit var hour7TempView: TextView
    private lateinit var hour8TempView: TextView
    private lateinit var hour9TempView: TextView
    private lateinit var hour10TempView: TextView
    private lateinit var hour11TempView: TextView
    private lateinit var hour12TempView: TextView

    private lateinit var hour1TextView: TextView
    private lateinit var hour2TextView: TextView
    private lateinit var hour3TextView: TextView
    private lateinit var hour4TextView: TextView
    private lateinit var hour5TextView: TextView
    private lateinit var hour6TextView: TextView
    private lateinit var hour7TextView: TextView
    private lateinit var hour8TextView: TextView
    private lateinit var hour9TextView: TextView
    private lateinit var hour10TextView: TextView
    private lateinit var hour11TextView: TextView
    private lateinit var hour12TextView: TextView

    private lateinit var hour1ImageView: ImageView
    private lateinit var hour2ImageView: ImageView
    private lateinit var hour3ImageView: ImageView
    private lateinit var hour4ImageView: ImageView
    private lateinit var hour5ImageView: ImageView
    private lateinit var hour6ImageView: ImageView
    private lateinit var hour7ImageView: ImageView
    private lateinit var hour8ImageView: ImageView
    private lateinit var hour9ImageView: ImageView
    private lateinit var hour10ImageView: ImageView
    private lateinit var hour11ImageView: ImageView
    private lateinit var hour12ImageView: ImageView

    private lateinit var unitView: TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null)
            supportActionBar?.hide()
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
        //day1TempView = findViewById(R.id.day1Temp)
        //day2TempView = findViewById(R.id.day2Temp)
        //day3TempView = findViewById(R.id.day3Temp)
        //day4TempView = findViewById(R.id.day4Temp)
        //day5TempView = findViewById(R.id.day5Temp)
        day1AqiView = findViewById(R.id.day1Aqi)
        day2AqiView = findViewById(R.id.day2Aqi)
        day3AqiView = findViewById(R.id.day3Aqi)
        day4AqiView = findViewById(R.id.day4Aqi)
        day5AqiView = findViewById(R.id.day5Aqi)
        day1ImageView = findViewById(R.id.day1ImageView)
        day2ImageView = findViewById(R.id.day2ImageView)
        day3ImageView = findViewById(R.id.day3ImageView)
        day4ImageView = findViewById(R.id.day4ImageView)
        day5ImageView = findViewById(R.id.day5ImageView)
        backButton = findViewById(R.id.backButton)
        detailsBackground = findViewById(R.id.detailsBackground)
        progBar = findViewById(R.id.detailsProgBar)
        detailsTextView = findViewById(R.id.detailsTextView)
        lineChartView = findViewById(R.id.chartView)
        xAxisDate1 = findViewById(R.id.xAxisDate1)
        xAxisDate2 = findViewById(R.id.xAxisDate2)
        xAxisDate3 = findViewById(R.id.xAxisDate3)
        xAxisDate4 = findViewById(R.id.xAxisDate4)
        xAxisDate5 = findViewById(R.id.xAxisDate5)
        uv = findViewById(R.id.UVValue)
        wind = findViewById(R.id.windValue)
        pressure = findViewById(R.id.pressureValue)
        humidProb = findViewById(R.id.humidityValue)
        precipValue = findViewById(R.id.percepVValue)
        visibility = findViewById(R.id.visibilityValue)
        lastUpdatedTime = findViewById(R.id.lastUpdatedTimeView)

        hour1TempView = findViewById(R.id.hour1TempTextView)
        hour2TempView = findViewById(R.id.hour1TempTextView2)
        hour3TempView = findViewById(R.id.hour1TempTextView3)
        hour4TempView = findViewById(R.id.hour1TempTextView4)
        hour5TempView = findViewById(R.id.hour1TempTextView5)
        hour6TempView = findViewById(R.id.hour1TempTextView6)
        hour7TempView = findViewById(R.id.hour1TempTextView7)
        hour8TempView = findViewById(R.id.hour1TempTextView8)
        hour9TempView = findViewById(R.id.hour1TempTextView9)
        hour10TempView = findViewById(R.id.hour1TempTextView10)
        hour11TempView = findViewById(R.id.hour1TempTextView11)
        hour12TempView = findViewById(R.id.hour1TempTextView12)

        hour1ImageView = findViewById(R.id.hour1ImageView)
        hour2ImageView = findViewById(R.id.hour1ImageView2)
        hour3ImageView = findViewById(R.id.hour1ImageView3)
        hour4ImageView = findViewById(R.id.hour1ImageView4)
        hour5ImageView = findViewById(R.id.hour1ImageView5)
        hour6ImageView = findViewById(R.id.hour1ImageView6)
        hour7ImageView = findViewById(R.id.hour1ImageView7)
        hour8ImageView = findViewById(R.id.hour1ImageView8)
        hour9ImageView = findViewById(R.id.hour1ImageView9)
        hour10ImageView = findViewById(R.id.hour1ImageView10)
        hour11ImageView = findViewById(R.id.hour1ImageView11)
        hour12ImageView = findViewById(R.id.hour1ImageView12)

        hour1TextView = findViewById(R.id.hour1TextView)
        hour2TextView = findViewById(R.id.hour2TextView)
        hour3TextView = findViewById(R.id.hour3TextView)
        hour4TextView = findViewById(R.id.hour4TextView)
        hour5TextView = findViewById(R.id.hour5TextView)
        hour6TextView = findViewById(R.id.hour6TextView)
        hour7TextView = findViewById(R.id.hour7TextView)
        hour8TextView = findViewById(R.id.hour8TextView)
        hour9TextView = findViewById(R.id.hour9TextView)
        hour10TextView = findViewById(R.id.hour10TextView)
        hour11TextView = findViewById(R.id.hour11TextView)
        hour12TextView = findViewById(R.id.hour12TextView)

        unitView = findViewById(R.id.unitView)

        var dayList = listOf<String>("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")

        //get intent shared preference variables
        val preferences = getSharedPreferences("weather-app", Context.MODE_PRIVATE)
        val cityCode = preferences.getString("CURR_CITY", "327658")!!
        val imp = preferences.getBoolean("IMPERIAL", true)
        val timeFormat24 = preferences.getBoolean("USE_24_H", false)

        //start the progress bar and disable clicks to the screen since networking is about to occur
        progBar.visibility= View.VISIBLE

        doAsync {
            val weatherManager = WeatherManager()
            val currentWeather = weatherManager.retrieveWeather(cityCode, getString(R.string.api_key))
            val fiveDayDetail = weatherManager.retrieve5DayWeather(currentWeather.locationKey, getString(R.string.api_key),!imp)
            val hourlyDetail = weatherManager.retrieve12HourWeather(currentWeather.locationKey, getString(R.string.api_key), !imp)
            // day = day + fiveDayDetail[0].date.substring(8, 10).toInt() - date - 1
            day = day - 1
            runOnUiThread {
                detailsTextView.text = currentWeather.city
                day1TextView.text = dayList[day % 7] + " " + fiveDayDetail[0].date.substring(8,10)
                day2TextView.text = dayList[(day + 1)%7] +" " + fiveDayDetail[1].date.substring(8,10)
                day3TextView.text = dayList[(day + 2)%7] +" " + fiveDayDetail[2].date.substring(8,10)
                day4TextView.text = dayList[(day + 3)%7] +" " + fiveDayDetail[3].date.substring(8,10)
                day5TextView.text = dayList[(day + 4)%7] +" " + fiveDayDetail[4].date.substring(8,10)
                day1CondView.isSelected = true
                day2CondView.isSelected = true
                day3CondView.isSelected = true
                day4CondView.isSelected = true
                day5CondView.isSelected = true

                if (timeFormat24) {
                    lastUpdatedTime.text = "Last Updated: " + currentWeather.lastUpdatedTime.substring(5,7) + "/" + currentWeather.lastUpdatedTime.substring(8,10) + " " + currentWeather.lastUpdatedTime.substring(11,16)
                }
                else {
                    val tempTime = timeFormat24to12(currentWeather.lastUpdatedTime.substring(11,16))
                    lastUpdatedTime.text = "Last Updated: " + currentWeather.lastUpdatedTime.substring(5,7) + "/" + currentWeather.lastUpdatedTime.substring(8,10) + " " + tempTime
                }

                if (fiveDayDetail[0].dayCondition == "Intermittent Clouds") {
                    day1CondView.text = "Cloudy"
                }
                else {
                    day1CondView.text = fiveDayDetail[0].dayCondition
                }
                if (fiveDayDetail[1].dayCondition == "Intermittent Clouds") {
                    day2CondView.text = "Cloudy"
                }
                else {
                    day2CondView.text = fiveDayDetail[1].dayCondition
                }
                if (fiveDayDetail[2].dayCondition == "Intermittent Clouds") {
                    day3CondView.text = "Cloudy"
                }
                else {
                    day3CondView.text = fiveDayDetail[2].dayCondition
                }
                if (fiveDayDetail[3].dayCondition == "Intermittent Clouds") {
                    day4CondView.text = "Cloudy"
                }
                else {
                    day4CondView.text = fiveDayDetail[3].dayCondition
                }
                if (fiveDayDetail[4].dayCondition == "Intermittent Clouds") {
                    day5CondView.text = "Cloudy"
                }
                else {
                    day5CondView.text = fiveDayDetail[4].dayCondition
                }

                /*
                if(imp) {
                    day1TempView.text =
                        fiveDayDetail[0].tempMax + "°F/" + fiveDayDetail[0].tempMin + "°F"
                    day2TempView.text =
                        fiveDayDetail[1].tempMax + "°F/" + fiveDayDetail[1].tempMin + "°F"
                    day3TempView.text =
                        fiveDayDetail[2].tempMax + "°F/" + fiveDayDetail[2].tempMin + "°F"
                    day4TempView.text =
                        fiveDayDetail[3].tempMax + "°F/" + fiveDayDetail[3].tempMin + "°F"
                    day5TempView.text =
                        fiveDayDetail[4].tempMax + "°F/" + fiveDayDetail[4].tempMin + "°F"
                }
                else{
                    day1TempView.text =
                        fiveDayDetail[0].tempMax + "°C/" + fiveDayDetail[0].tempMin + "°C"
                    day2TempView.text =
                        fiveDayDetail[1].tempMax + "°C/" + fiveDayDetail[1].tempMin + "°C"
                    day3TempView.text =
                        fiveDayDetail[2].tempMax + "°C/" + fiveDayDetail[2].tempMin + "°C"
                    day4TempView.text =
                        fiveDayDetail[3].tempMax + "°C/" + fiveDayDetail[3].tempMin + "°C"
                    day5TempView.text =
                        fiveDayDetail[4].tempMax + "°C/" + fiveDayDetail[4].tempMin + "°C"
                }

                 */

                day1AqiView.text = "AQI: " + fiveDayDetail[0].aqi
                day2AqiView.text = "AQI: " + fiveDayDetail[1].aqi
                day3AqiView.text = "AQI: " + fiveDayDetail[2].aqi
                day4AqiView.text = "AQI: " + fiveDayDetail[3].aqi
                day5AqiView.text = "AQI: " + fiveDayDetail[4].aqi

                if (imp) {
                    pressure.text = currentWeather.pressureImp
                    precipValue.text = currentWeather.precip1hrImp
                    visibility.text = currentWeather.visibilityImp
                    wind.text = currentWeather.windImp + " mph"
                }
                else {
                    pressure.text = currentWeather.pressureMet
                    precipValue.text = currentWeather.precip1hrMet
                    visibility.text = currentWeather.visibilityMet
                    wind.text = currentWeather.windMet + " km/h"
                }
                uv.text = currentWeather.uv + ", " + currentWeather.uvStatus

                if(currentWeather.sunIsOut) {
                    window.statusBarColor = resources.getColor(R.color.colorBackgroundLight)
                    window.navigationBarColor = resources.getColor(R.color.colorBackgroundLight)
                    detailsBackground.background =
                        ColorDrawable(resources.getColor(R.color.colorBackgroundLight))
                }
                else {
                    window.statusBarColor = resources.getColor(R.color.colorBackgroundDark)
                    window.navigationBarColor = resources.getColor(R.color.colorBackgroundDark)
                    detailsBackground.background =
                        ColorDrawable(resources.getColor(R.color.colorBackgroundDark))
                }
                humidProb.text = currentWeather.humidity + "%"
                // Day 1
                if (fiveDayDetail[0].dayCondition == "Sunny") {
                    day1ImageView.setImageResource(R.drawable.day_sunny)
                }
                else if (fiveDayDetail[0].dayCondition == "Mostly sunny") {
                    day1ImageView.setImageResource(R.drawable.day_mostly_sunny)
                }
                else if (fiveDayDetail[0].dayCondition == "Partly sunny") {
                    day1ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[0].dayCondition == "Intermittent clouds") {
                    day1ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[0].dayCondition == "Mostly cloudy") {
                    day1ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[0].dayCondition == "Cloudy") {
                    day1ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[0].dayCondition == "Fog") {
                    day1ImageView.setImageResource(R.drawable.fog)
                }
                else if ((fiveDayDetail[0].dayCondition == "Showers")||(fiveDayDetail[0].dayCondition == "Rain")) {
                    day1ImageView.setImageResource(R.drawable.showers)
                }
                else if (fiveDayDetail[0].dayCondition == "T-Storms") {
                    day1ImageView.setImageResource(R.drawable.day_tstorms)
                }
                else if (fiveDayDetail[0].dayCondition == "Dreary (Overcast)") {
                    day1ImageView.setImageResource(R.drawable.cloudy)
                }
                else if (fiveDayDetail[0].dayCondition == "Mostly cloudy w/ showers"){
                    day1ImageView.setImageResource(R.drawable.day_sunny_showers)
                }
                else {
                    day1ImageView.setImageResource(R.drawable.day_sunny)
                }
                // Day 2
                if (fiveDayDetail[1].dayCondition == "Sunny") {
                    day2ImageView.setImageResource(R.drawable.day_sunny)
                }
                else if (fiveDayDetail[1].dayCondition == "Mostly sunny") {
                    day2ImageView.setImageResource(R.drawable.day_mostly_sunny)
                }
                else if (fiveDayDetail[1].dayCondition == "Partly sunny") {
                    day2ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[1].dayCondition == "Intermittent clouds") {
                    day2ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[1].dayCondition == "Mostly cloudy") {
                    day2ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[1].dayCondition == "Cloudy") {
                    day2ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[1].dayCondition == "Fog") {
                    day2ImageView.setImageResource(R.drawable.fog)
                }
                else if ((fiveDayDetail[1].dayCondition == "Showers")||(fiveDayDetail[1].dayCondition == "Rain")) {
                    day2ImageView.setImageResource(R.drawable.showers)
                }
                else if (fiveDayDetail[1].dayCondition == "T-Storms") {
                    day2ImageView.setImageResource(R.drawable.day_tstorms)
                }
                else if (fiveDayDetail[1].dayCondition == "Dreary (Overcast)") {
                    day2ImageView.setImageResource(R.drawable.cloudy)
                }
                else if (fiveDayDetail[1].dayCondition == "Mostly cloudy w/ showers"){
                    day2ImageView.setImageResource(R.drawable.day_sunny_showers)
                }
                else {
                    day2ImageView.setImageResource(R.drawable.day_sunny)
                }

                // Day 3
                if (fiveDayDetail[2].dayCondition == "Sunny") {
                    day3ImageView.setImageResource(R.drawable.day_sunny)
                }
                else if (fiveDayDetail[2].dayCondition == "Mostly sunny") {
                    day3ImageView.setImageResource(R.drawable.day_mostly_sunny)
                }
                else if (fiveDayDetail[2].dayCondition == "Partly sunny") {
                    day3ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[2].dayCondition == "Intermittent clouds") {
                    day3ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[2].dayCondition == "Mostly cloudy") {
                    day3ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[2].dayCondition == "Cloudy") {
                    day3ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[2].dayCondition == "Fog") {
                    day3ImageView.setImageResource(R.drawable.fog)
                }
                else if ((fiveDayDetail[2].dayCondition == "Showers")||(fiveDayDetail[2].dayCondition == "Rain")) {
                    day3ImageView.setImageResource(R.drawable.showers)
                }
                else if (fiveDayDetail[2].dayCondition == "T-Storms") {
                    day3ImageView.setImageResource(R.drawable.day_tstorms)
                }
                else if (fiveDayDetail[2].dayCondition == "Dreary (Overcast)") {
                    day3ImageView.setImageResource(R.drawable.cloudy)
                }
                else if (fiveDayDetail[2].dayCondition == "Mostly cloudy w/ showers"){
                    day3ImageView.setImageResource(R.drawable.day_sunny_showers)
                }
                else {
                    day3ImageView.setImageResource(R.drawable.day_sunny)
                }
                // Day 4
                if (fiveDayDetail[3].dayCondition == "Sunny") {
                    day4ImageView.setImageResource(R.drawable.day_sunny)
                }
                else if (fiveDayDetail[3].dayCondition == "Mostly sunny") {
                    day4ImageView.setImageResource(R.drawable.day_mostly_sunny)
                }
                else if (fiveDayDetail[3].dayCondition == "Partly sunny") {
                    day4ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[3].dayCondition == "Intermittent clouds") {
                    day4ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[3].dayCondition == "Mostly cloudy") {
                    day4ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[3].dayCondition == "Cloudy") {
                    day4ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[3].dayCondition == "Fog") {
                    day4ImageView.setImageResource(R.drawable.fog)
                }
                else if ((fiveDayDetail[3].dayCondition == "Showers")||(fiveDayDetail[3].dayCondition == "Rain")) {
                    day4ImageView.setImageResource(R.drawable.showers)
                }
                else if (fiveDayDetail[3].dayCondition == "T-Storms") {
                    day4ImageView.setImageResource(R.drawable.day_tstorms)
                }
                else if (fiveDayDetail[3].dayCondition == "Dreary (Overcast)") {
                    day4ImageView.setImageResource(R.drawable.cloudy)
                }
                else if (fiveDayDetail[3].dayCondition == "Mostly cloudy w/ showers"){
                    day4ImageView.setImageResource(R.drawable.day_sunny_showers)
                }
                else {
                    day4ImageView.setImageResource(R.drawable.day_sunny)
                }
                // Day 5
                if (fiveDayDetail[4].dayCondition == "Sunny") {
                    day5ImageView.setImageResource(R.drawable.day_sunny)
                }
                else if (fiveDayDetail[4].dayCondition == "Mostly sunny") {
                    day5ImageView.setImageResource(R.drawable.day_mostly_sunny)
                }
                else if (fiveDayDetail[4].dayCondition == "Partly sunny") {
                    day5ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[4].dayCondition == "Intermittent clouds") {
                    day5ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[4].dayCondition == "Mostly cloudy") {
                    day5ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[4].dayCondition == "Cloudy") {
                    day5ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[4].dayCondition == "Fog") {
                    day5ImageView.setImageResource(R.drawable.fog)
                }
                else if ((fiveDayDetail[4].dayCondition == "Showers")||(fiveDayDetail[4].dayCondition == "Rain")) {
                    day5ImageView.setImageResource(R.drawable.showers)
                }
                else if (fiveDayDetail[4].dayCondition == "T-Storms") {
                    day5ImageView.setImageResource(R.drawable.day_tstorms)
                }
                else if (fiveDayDetail[4].dayCondition == "Dreary (Overcast)") {
                    day5ImageView.setImageResource(R.drawable.cloudy)
                }
                else if (fiveDayDetail[4].dayCondition == "Mostly cloudy w/ showers"){
                    day5ImageView.setImageResource(R.drawable.day_sunny_showers)
                }
                else {
                    day5ImageView.setImageResource(R.drawable.day_sunny)
                }

                // set hourly text
                var imageResource = R.drawable.day_sunny
                for (i in 0..11) {
                    var currentTime = hourlyDetail[i].time
                    if (!timeFormat24) {
                        val newTIme = timeFormat24to12(currentTime)
                        val endPos1 = newTIme.indexOf(':')
                        val endPos2 = newTIme.indexOf('M')
                        currentTime =  newTIme.substring(0,endPos1) + " " + newTIme.substring(endPos2 - 1, endPos2 + 1)
                    }
                    val currentTemp = hourlyDetail[i].temp
                    val currentWeatherIcon = hourlyDetail[i].weatherIcon
                    if(currentWeatherIcon in 1..2)
                        imageResource = R.drawable.day_sunny
                    else if(currentWeatherIcon in 3..6)
                        imageResource = R.drawable.day_mostly_sunny
                    else if(currentWeatherIcon in 7..11 || currentWeatherIcon in 35..38)
                        imageResource = R.drawable.cloudy
                    else if(currentWeatherIcon in 12..14 || currentWeatherIcon in 39..40 || currentWeatherIcon == 18)
                        imageResource = R.drawable.showers
                    else if(currentWeatherIcon in 15..17 || currentWeatherIcon in 41..42)
                        imageResource = R.drawable.day_tstorms //fix to night tstorms
                    else if(currentWeatherIcon in 19..29 || currentWeatherIcon in 43..44)
                        imageResource = R.drawable.day_tstorms //fix to night snowy
                    else if(currentWeatherIcon in 33..34)
                        imageResource = R.drawable.day_tstorms //fix to starry
                    else{
                        if (currentWeather.sunIsOut)
                            imageResource = R.drawable.day_sunny
                        else
                            imageResource = R.drawable.day_sunny//fix to starry
                    }
                    if (i == 0){
                        hour1TextView.text = currentTime
                        hour1TempView.text = currentTemp
                        hour1ImageView.setImageResource(imageResource)
                    }
                    else if (i == 1) {
                        hour2TextView.text = currentTime
                        hour2TempView.text = currentTemp
                        hour2ImageView.setImageResource(imageResource)
                    }
                    else if (i == 2) {
                        hour3TextView.text = currentTime
                        hour3TempView.text = currentTemp
                        hour3ImageView.setImageResource(imageResource)
                    }
                    else if (i == 3) {
                        hour4TextView.text = currentTime
                        hour4TempView.text = currentTemp
                        hour4ImageView.setImageResource(imageResource)
                    }
                    else if (i == 4) {
                        hour5TextView.text = currentTime
                        hour5TempView.text = currentTemp
                        hour5ImageView.setImageResource(imageResource)
                    }
                    else if (i == 5) {
                        hour6TextView.text = currentTime
                        hour6TempView.text = currentTemp
                        hour6ImageView.setImageResource(imageResource)
                    }
                    else if (i == 6) {
                        hour7TextView.text = currentTime
                        hour7TempView.text = currentTemp
                        hour7ImageView.setImageResource(imageResource)
                    }
                    else if (i == 7) {
                        hour8TextView.text = currentTime
                        hour8TempView.text = currentTemp
                        hour8ImageView.setImageResource(imageResource)
                    }
                    else if (i == 8) {
                        hour9TextView.text = currentTime
                        hour9TempView.text = currentTemp
                        hour9ImageView.setImageResource(imageResource)
                    }
                    else if (i == 9) {
                        hour10TextView.text = currentTime
                        hour10TempView.text = currentTemp
                        hour10ImageView.setImageResource(imageResource)
                    }
                    else if (i == 10) {
                        hour11TextView.text = currentTime
                        hour11TempView.text = currentTemp
                        hour11ImageView.setImageResource(imageResource)
                    }
                    else {
                        hour12TextView.text = currentTime
                        hour12TempView.text = currentTemp
                        hour12ImageView.setImageResource(imageResource)
                    }
                }

                //stop prog bar when networking is done
                progBar.visibility=View.GONE

                val entriesMax = ArrayList<Entry>()
                entriesMax.add(Entry(1f, fiveDayDetail[0].tempMax.toFloat()))
                entriesMax.add(Entry(2f, fiveDayDetail[1].tempMax.toFloat()))
                entriesMax.add(Entry(3f, fiveDayDetail[2].tempMax.toFloat()))
                entriesMax.add(Entry(4f, fiveDayDetail[3].tempMax.toFloat()))
                entriesMax.add(Entry(5f, fiveDayDetail[4].tempMax.toFloat()))
                val entriesMin = ArrayList<Entry>()
                entriesMin.add(Entry(1f, fiveDayDetail[0].tempMin.toFloat()))
                entriesMin.add(Entry(2f, fiveDayDetail[1].tempMin.toFloat()))
                entriesMin.add(Entry(3f, fiveDayDetail[2].tempMin.toFloat()))
                entriesMin.add(Entry(4f, fiveDayDetail[3].tempMin.toFloat()))
                entriesMin.add(Entry(5f, fiveDayDetail[4].tempMin.toFloat()))


                val vl = LineDataSet(entriesMax, "TempMax")
                val vl_2 = LineDataSet(entriesMin, "TempMin")
                //Part4
                vl.valueTextSize = 15f
                vl.setDrawFilled(true)
                vl.lineWidth = 4f
                vl.fillColor = R.color.grey
                // vl.fillAlpha = R.color.red
                vl.valueTextColor = Color.WHITE
                vl.setDrawCircles(true)
                vl.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
                vl_2.valueTextSize = 15f
                vl_2.valueTextColor = Color.WHITE
                vl_2.setDrawFilled(true)
                vl_2.lineWidth = 4f
                vl_2.setDrawCircles(true)
                vl_2.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
                val lineDataSets = listOf(vl, vl_2)
                lineChartView.xAxis.labelRotationAngle = 0f

                lineChartView.data = LineData(lineDataSets)

                lineChartView.axisRight.isEnabled = false
                lineChartView.setDrawBorders(false)
                lineChartView.setScaleEnabled(false)
                lineChartView.getDescription().setEnabled(false)
                lineChartView.getLegend().setEnabled(false)
                lineChartView.isHighlightPerDragEnabled = false
                lineChartView.isHighlightPerTapEnabled = false
                // grid
                val xAxis: XAxis = lineChartView.xAxis
                // xAxis.valueFormatter = IndexAxisValueFormatter(arrayListOf(dayList[day % 7], dayList[(day + 1)%7], dayList[(day + 2)%7], dayList[(day + 3)%7], dayList[(day + 4)%7]))
                xAxis.setDrawAxisLine(false)
                xAxis.setDrawGridLines(false)
                xAxis.setGranularity(0f)
                // xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.isEnabled = false
                val leftYAxis = lineChartView.axisLeft
                leftYAxis.isEnabled = false
                val rightYAxis = lineChartView.axisRight
                rightYAxis.isEnabled = false

                // lineChartView.data.setDrawValues(false)
                lineChartView.invalidate()

                // set xAxis label
                xAxisDate1.text = dayList[day % 7]
                xAxisDate2.text = dayList[(day + 1) % 7]
                xAxisDate3.text = dayList[(day + 2) % 7]
                xAxisDate4.text = dayList[(day + 3) % 7]
                xAxisDate5.text = dayList[(day + 4) % 7]

                if (imp) {
                    unitView.text = getString(R.string.unitName_f)
                }
                else {
                    unitView.text = getString(R.string.unitName_c)
                }
            }
        }

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
    fun timeFormat24to12(origin: String): String{
        var ret = ""
        if (origin.substring(0,2).toInt() < 12) {
            ret = origin + "AM"
            if (ret.substring(0,1) == "0") {
                ret = ret.substring(1,ret.length)
            }
            return ret
        }
        else if (origin.substring(0,2).toInt() == 12){
            ret = origin + "PM"
            return ret
        }
        else {
            ret = (origin.substring(0,2).toInt() - 12).toString() + origin.substring(2,5) + "PM"
            return ret
        }
    }
}
