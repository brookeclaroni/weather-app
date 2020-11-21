package com.example.weatherapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.activity_details.*
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

        //get intent shared preference variables
        val preferences = getSharedPreferences("weather-app", Context.MODE_PRIVATE)
        val cityCode = preferences.getString("CURR_CITY", "327658")!!
        val imp = preferences.getBoolean("IMPERIAL", true)

        //start the progress bar and disable clicks to the screen since networking is about to occur
        progBar.visibility= View.VISIBLE

        doAsync {
            val weatherManager = WeatherManager()
            val currentWeather = weatherManager.retrieveWeather(cityCode, getString(R.string.api_key))
            val fiveDayDetail = weatherManager.retrieve5DayWeather(currentWeather.locationKey, getString(R.string.api_key),!imp)
            runOnUiThread {
                detailsTextView.text = currentWeather.city
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

                if(currentWeather.sunIsOut) {
                    detailsBackground.background =
                        ColorDrawable(resources.getColor(R.color.colorBackgroundLight))
                }
                else {
                    detailsBackground.background =
                        ColorDrawable(resources.getColor(R.color.colorBackgroundDark))
                }

                if (fiveDayDetail[0].dayCondition == "Sunny") {
                    day1ImageView.setImageResource(R.drawable.day_sunny)
                }
                else if (fiveDayDetail[0].dayCondition == "Mostly sunny") {
                    day1ImageView.setImageResource(R.drawable.day_mostly_sunny)
                }
                else if (fiveDayDetail[0].dayCondition == "Partly sunny") {
                    day1ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[0].dayCondition == "Intermittent Clouds") {
                    day1ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[0].dayCondition == "Mostly Cloudy") {
                    day1ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[0].dayCondition == "Cloudy") {
                    day1ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[0].dayCondition == "Fog") {
                    day1ImageView.setImageResource(R.drawable.fog)
                }
                else if (fiveDayDetail[0].dayCondition == "Showers") {
                    day1ImageView.setImageResource(R.drawable.showers)
                }
                else if (fiveDayDetail[0].dayCondition == "T-Storms") {
                    day1ImageView.setImageResource(R.drawable.day_tstorms)
                }
                else if (fiveDayDetail[0].dayCondition == "Dreary (Overcast)") {
                    day1ImageView.setImageResource(R.drawable.cloudy)
                }
                else if (fiveDayDetail[0].dayCondition == "Mostly Cloudy w/ Showers"){
                    day1ImageView.setImageResource(R.drawable.day_sunny_showers)
                }
                else {
                    day1ImageView.setImageResource(R.drawable.day_sunny)
                }

                if (fiveDayDetail[1].dayCondition == "Sunny") {
                    day2ImageView.setImageResource(R.drawable.day_sunny)
                }
                else if (fiveDayDetail[1].dayCondition == "Mostly sunny") {
                    day2ImageView.setImageResource(R.drawable.day_mostly_sunny)
                }
                else if (fiveDayDetail[1].dayCondition == "Partly sunny") {
                    day2ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[1].dayCondition == "Intermittent Clouds") {
                    day2ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[1].dayCondition == "Mostly Cloudy") {
                    day2ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[1].dayCondition == "Cloudy") {
                    day2ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[1].dayCondition == "Fog") {
                    day2ImageView.setImageResource(R.drawable.fog)
                }
                else if (fiveDayDetail[1].dayCondition == "Showers") {
                    day2ImageView.setImageResource(R.drawable.showers)
                }
                else if (fiveDayDetail[1].dayCondition == "T-Storms") {
                    day2ImageView.setImageResource(R.drawable.day_tstorms)
                }
                else if (fiveDayDetail[1].dayCondition == "Dreary (Overcast)") {
                    day2ImageView.setImageResource(R.drawable.cloudy)
                }
                else if (fiveDayDetail[1].dayCondition == "Mostly Cloudy w/ Showers"){
                    day2ImageView.setImageResource(R.drawable.day_sunny_showers)
                }
                else {
                    day2ImageView.setImageResource(R.drawable.day_sunny)
                }


                if (fiveDayDetail[2].dayCondition == "Sunny") {
                    day3ImageView.setImageResource(R.drawable.day_sunny)
                }
                else if (fiveDayDetail[2].dayCondition == "Mostly sunny") {
                    day3ImageView.setImageResource(R.drawable.day_mostly_sunny)
                }
                else if (fiveDayDetail[2].dayCondition == "Partly sunny") {
                    day3ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[2].dayCondition == "Intermittent Clouds") {
                    day3ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[2].dayCondition == "Mostly Cloudy") {
                    day3ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[2].dayCondition == "Cloudy") {
                    day3ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[2].dayCondition == "Fog") {
                    day3ImageView.setImageResource(R.drawable.fog)
                }
                else if (fiveDayDetail[2].dayCondition == "Showers") {
                    day3ImageView.setImageResource(R.drawable.showers)
                }
                else if (fiveDayDetail[2].dayCondition == "T-Storms") {
                    day3ImageView.setImageResource(R.drawable.day_tstorms)
                }
                else if (fiveDayDetail[2].dayCondition == "Dreary (Overcast)") {
                    day3ImageView.setImageResource(R.drawable.cloudy)
                }
                else if (fiveDayDetail[2].dayCondition == "Mostly Cloudy w/ Showers"){
                    day3ImageView.setImageResource(R.drawable.day_sunny_showers)
                }
                else {
                    day3ImageView.setImageResource(R.drawable.day_sunny)
                }

                if (fiveDayDetail[3].dayCondition == "Sunny") {
                    day4ImageView.setImageResource(R.drawable.day_sunny)
                }
                else if (fiveDayDetail[3].dayCondition == "Mostly sunny") {
                    day4ImageView.setImageResource(R.drawable.day_mostly_sunny)
                }
                else if (fiveDayDetail[3].dayCondition == "Partly sunny") {
                    day4ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[3].dayCondition == "Intermittent Clouds") {
                    day4ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[3].dayCondition == "Mostly Cloudy") {
                    day4ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[3].dayCondition == "Cloudy") {
                    day4ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[3].dayCondition == "Fog") {
                    day4ImageView.setImageResource(R.drawable.fog)
                }
                else if (fiveDayDetail[3].dayCondition == "Showers") {
                    day4ImageView.setImageResource(R.drawable.showers)
                }
                else if (fiveDayDetail[3].dayCondition == "T-Storms") {
                    day4ImageView.setImageResource(R.drawable.day_tstorms)
                }
                else if (fiveDayDetail[3].dayCondition == "Dreary (Overcast)") {
                    day4ImageView.setImageResource(R.drawable.cloudy)
                }
                else if (fiveDayDetail[3].dayCondition == "Mostly Cloudy w/ Showers"){
                    day4ImageView.setImageResource(R.drawable.day_sunny_showers)
                }
                else {
                    day4ImageView.setImageResource(R.drawable.day_sunny)
                }

                if (fiveDayDetail[4].dayCondition == "Sunny") {
                    day5ImageView.setImageResource(R.drawable.day_sunny)
                }
                else if (fiveDayDetail[4].dayCondition == "Mostly sunny") {
                    day5ImageView.setImageResource(R.drawable.day_mostly_sunny)
                }
                else if (fiveDayDetail[4].dayCondition == "Partly sunny") {
                    day5ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[4].dayCondition == "Intermittent Clouds") {
                    day5ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[4].dayCondition == "Mostly Cloudy") {
                    day5ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[4].dayCondition == "Cloudy") {
                    day5ImageView.setImageResource(R.drawable.day_cloudy)
                }
                else if (fiveDayDetail[4].dayCondition == "Fog") {
                    day5ImageView.setImageResource(R.drawable.fog)
                }
                else if (fiveDayDetail[4].dayCondition == "Showers") {
                    day5ImageView.setImageResource(R.drawable.showers)
                }
                else if (fiveDayDetail[4].dayCondition == "T-Storms") {
                    day5ImageView.setImageResource(R.drawable.day_tstorms)
                }
                else if (fiveDayDetail[4].dayCondition == "Dreary (Overcast)") {
                    day5ImageView.setImageResource(R.drawable.cloudy)
                }
                else if (fiveDayDetail[4].dayCondition == "Mostly Cloudy w/ Showers"){
                    day5ImageView.setImageResource(R.drawable.day_sunny_showers)
                }
                else {
                    day5ImageView.setImageResource(R.drawable.day_sunny)
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
                vl.setDrawCircles(true)
                vl.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
                vl_2.valueTextSize = 15f
                vl_2.setValueTextColors(listOf(1,1,1,1))
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
                xAxis.setDrawAxisLine(false)
                xAxis.setDrawGridLines(false)
                xAxis.isEnabled = false
                val leftYAxis = lineChartView.axisLeft
                leftYAxis.isEnabled = false
                val rightYAxis = lineChartView.axisRight
                rightYAxis.isEnabled = false

                // lineChartView.data.setDrawValues(false)
                lineChartView.invalidate()
            }
        }

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
