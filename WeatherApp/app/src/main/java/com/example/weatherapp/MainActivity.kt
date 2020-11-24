package com.example.weatherapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import org.jetbrains.anko.doAsync


class MainActivity : AppCompatActivity() {

    //initialize lateinit variables
    private lateinit var cityButton: ImageButton
    private lateinit var offStarButton: ImageButton
    private lateinit var onStarButton: ImageButton
    private lateinit var sunImageView: ImageView
    private lateinit var cityTextView: TextView
    private lateinit var countryTextView: TextView
    private lateinit var temperatureTextView: TextView
    private lateinit var weatherTextView: TextView
    private lateinit var realFeelView: TextView
    private lateinit var sunriseImageView: ImageView
    private lateinit var precipImageView: ImageView
    private lateinit var sunsetImageView: ImageView
    private lateinit var sunriseValueTextView: TextView
    private lateinit var precipValueTextView: TextView
    private lateinit var sunsetValueTextView: TextView
    private lateinit var sunriseTextView: TextView
    private lateinit var precipTextView: TextView
    private lateinit var sunsetTextView: TextView
    private lateinit var moreDetailsButton: Button
    private lateinit var degreeSwitch: Switch
    private lateinit var degreeCTextView: TextView
    private lateinit var background: ConstraintLayout
    private lateinit var searchEditText: EditText
    private lateinit var searchImageButton: ImageButton
    private lateinit var progBar: ProgressBar
    private lateinit var degreeLetterTextView: TextView
    private lateinit var helpTextView: TextView

    private lateinit var sunCenter : ImageView
    private lateinit var sunFlare : ImageView
    private lateinit var bottomCloud : ImageView
    private lateinit var topCloud : ImageView
    private lateinit var rainDrop1 : ImageView
    private lateinit var rainDrop2 : ImageView
    private lateinit var rainDrop3 : ImageView
    private lateinit var thunder : ImageView
    private lateinit var snowFlake1 : ImageView
    private lateinit var snowFlake2 : ImageView
    private lateinit var snowFlake3 : ImageView
    private lateinit var star1 : ImageView
    private lateinit var star2 : ImageView
    private lateinit var star3 : ImageView

    fun openBrowser(view: View) {
        //Get url from tag
        val url = view.tag as String
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.data = Uri.parse(url)
            startActivity(intent)
        } catch (e: Exception) {
            println("Browser Undetected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null)
            supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        //populate lateinit variables
        cityButton = findViewById(R.id.cityButton)
        offStarButton = findViewById(R.id.offStarButton)
        onStarButton = findViewById(R.id.onStarButton)
        sunImageView = findViewById(R.id.sunImageView)
        cityTextView = findViewById(R.id.cityTextView)
        countryTextView = findViewById(R.id.countryTextView)
        temperatureTextView = findViewById(R.id.temperatureTextView)
        weatherTextView = findViewById(R.id.weatherTextView)
        realFeelView = findViewById(R.id.realFealView)
        sunriseImageView = findViewById(R.id.sunRIseView)
        precipImageView = findViewById(R.id.precipView)
        sunsetImageView = findViewById(R.id.sunSetView)
        sunriseValueTextView = findViewById(R.id.sunriseValueTextView)
        precipValueTextView = findViewById(R.id.precipValueTextView)
        sunsetValueTextView = findViewById(R.id.sunsetValueTextView)
        sunriseTextView = findViewById(R.id.sunriseTextView)
        precipTextView = findViewById(R.id.precipTextView)
        sunsetTextView = findViewById(R.id.sunsetTextView)
        moreDetailsButton = findViewById(R.id.moreDetailsButton)
        degreeSwitch = findViewById(R.id.degreeSwitch)
        degreeCTextView = findViewById(R.id.degreeCTextView)
        background = findViewById(R.id.background)
        searchEditText = findViewById(R.id.mainSearchEditText)
        searchImageButton = findViewById(R.id.mainSearchButton)
        progBar = findViewById(R.id.mainProgBar)
        degreeLetterTextView = findViewById(R.id.degreeLetterTextView)
        helpTextView = findViewById(R.id.helpTextView)

        sunCenter = findViewById(R.id.sunCenterImageView)
        sunFlare = findViewById(R.id.sunFlareImageView)
        bottomCloud = findViewById(R.id.bottomCloudImageView)
        topCloud = findViewById(R.id.topCloudImageView)
        rainDrop1 = findViewById(R.id.rainDropImageView1)
        rainDrop2 = findViewById(R.id.rainDropImageView2)
        rainDrop3 = findViewById(R.id.rainDropImageView3)
        thunder = findViewById(R.id.thunderImageView)
        snowFlake1 = findViewById(R.id.snowFlakeImageView1)
        snowFlake2 = findViewById(R.id.snowFlakeImageView2)
        snowFlake3 = findViewById(R.id.snowFlakeImageView3)
        star1 = findViewById(R.id.starImageView1)
        star2 = findViewById(R.id.starImageView2)
        star3 = findViewById(R.id.starImageView3)

        //get intent shared preference variables
        val preferences = getSharedPreferences("weather-app", Context.MODE_PRIVATE)
        val cityCode = preferences.getString("CURR_CITY", "327659")!!
        val imp = preferences.getBoolean("IMPERIAL", true)

        var tempImp = ""
        var tempMet = ""
        var windImp = ""
        var windMet = ""
        var realTempImp = ""
        var realTempMet = ""

        degreeSwitch.isChecked = !imp

        if(!imp) //temp is in celsius so witch the default
        {
            //Make F greyed out and regular text weight
            degreeSwitch.setTextColor(Color.parseColor("#CFCFCF"))
            degreeSwitch.setTypeface(null, Typeface.NORMAL)

            //Make C white and bold
            degreeCTextView.setTextColor(Color.parseColor("#FFFFFF"))
            degreeCTextView.setTypeface(null, Typeface.BOLD)
        }

        searchImageButton.setOnClickListener{
            if (searchEditText.text.toString() != "") {
                preferences
                    .edit()
                    .putString("CURR_CITY", searchEditText.text.toString())
                    .apply()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        //start the progress bar and disable clicks to the screen since networking is about to occur
        progBar.visibility=View.VISIBLE

        doAsync {
            val weatherManager = WeatherManager()
            val currentWeather = weatherManager.retrieveWeather(
                cityCode,
                getString(R.string.api_key)
            )
            val fiveDayDetail = weatherManager.retrieve5DayWeather(currentWeather.locationKey, getString(R.string.api_key),!imp)
            runOnUiThread {
                tempImp = currentWeather.tempImp
                tempMet = currentWeather.tempMet
                windImp = currentWeather.windImp
                windMet = currentWeather.windMet
                realTempImp = currentWeather.realFeelTempImp
                realTempMet = currentWeather.realFeelTempMet

                //get and set temp
                if(imp) {
                    temperatureTextView.text = getString(R.string.tempF, tempImp)
                    realFeelView.text = getString(R.string.real_feel_imp, realTempImp)
                    //change degree letter to F
                    degreeLetterTextView.text = getString(R.string.degree_f)
                }
                else {
                    temperatureTextView.text = getString(R.string.tempC, tempMet)
                    realFeelView.text = getString(R.string.real_feel_met, realTempMet)
                    //change degree letter to F
                    degreeLetterTextView.text = getString(R.string.degree_c)
                }

                //get and set background brightness
                if(currentWeather.sunIsOut) {
                    window.statusBarColor = resources.getColor(R.color.colorBackgroundLight)
                    window.navigationBarColor = resources.getColor(R.color.colorBackgroundLight)
                    background.background =
                        ColorDrawable(resources.getColor(R.color.colorBackgroundLight))
                    moreDetailsButton.setTextColor(resources.getColor(R.color.colorBackgroundLight))
                }
                else {
                    window.statusBarColor = resources.getColor(R.color.colorBackgroundDark)
                    window.navigationBarColor = resources.getColor(R.color.colorBackgroundDark)
                    background.background =
                        ColorDrawable(resources.getColor(R.color.colorBackgroundDark))
                    moreDetailsButton.setTextColor(resources.getColor(R.color.colorBackgroundDark))
                }

                //get and set weather icon
                if(currentWeather.weatherInt in 1..2)
                    isSunny()
                else if(currentWeather.weatherInt in 3..6)
                    isPartlyCloudy()
                else if(currentWeather.weatherInt in 7..11 || currentWeather.weatherInt in 35..38)
                    isCloudy()
                else if(currentWeather.weatherInt in 12..14 || currentWeather.weatherInt in 39..40 || currentWeather.weatherInt == 18)
                    isRainy()
                else if(currentWeather.weatherInt in 15..17 || currentWeather.weatherInt in 41..42)
                    isThundering()
                else if(currentWeather.weatherInt in 19..29 || currentWeather.weatherInt in 43..44)
                    isSnowy()
                else if(currentWeather.weatherInt in 33..34)
                    isStarry()
                else{
                    if (currentWeather.sunIsOut)
                        isSunny()
                    else
                        isStarry()
                }

                //get and set location details
                cityTextView.text = currentWeather.cityState
                countryTextView.text = currentWeather.country

                //get and set sunrise and sunset
                sunriseValueTextView.text = fiveDayDetail[0].sunrise
                sunsetValueTextView.text = fiveDayDetail[0].sunset

                //set weather text
                weatherTextView.text = currentWeather.weatherText
                // weatherTextView.isSelected = true

                //get and set precipitation probability
                if (currentWeather.sunIsOut) {
                    precipValueTextView.text = fiveDayDetail[0].dayPrecipProb
                }
                else {
                    precipValueTextView.text = fiveDayDetail[0].nightPrecipProb
                }



                //when star is clicked, behave accordingly
                //get sharedPreferences, get the string set of saved concerts
                val savedCitySet = preferences.getStringSet("SAVED_CITIES", mutableSetOf())

                //make the star the appropriate color
                if(savedCitySet!!.contains(currentWeather.cityState)) {

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
                    savedCitySet?.add(currentWeather.cityState)
                    preferences.edit().putStringSet("SAVED_CITIES", savedCitySet).apply()
                }

                //if star is yellow and then the user clicks to unsave
                onStarButton.setOnClickListener{

                    //change the color, object data, and update shared preferences
                    offStarButton.visibility = View.VISIBLE
                    currentWeather.saved=false
                    savedCitySet?.remove(currentWeather.cityState)
                    preferences.edit().putStringSet("SAVED_CITIES", savedCitySet).apply()
                }

                //networking is done so get rid of prog bar
                progBar.visibility=View.GONE
            }
        }

        //when city button is pressed, head to city activity
        cityButton.setOnClickListener{
            val intent = Intent(this, CitiesActivity::class.java)
            startActivity(intent)
        }

        degreeSwitch.setOnCheckedChangeListener { _ , isChecked ->
            //if Celsius is requested
            if(isChecked) {
                //Make F greyed out and regular text weight
                degreeSwitch.setTextColor(Color.parseColor("#CFCFCF"))
                degreeSwitch.setTypeface(null, Typeface.NORMAL)

                //Make C white and bold
                degreeCTextView.setTextColor(Color.parseColor("#FFFFFF"))
                degreeCTextView.setTypeface(null, Typeface.BOLD)

                //change degree letter to C
                degreeLetterTextView.text = getString(R.string.degree_c)

                preferences.edit().putBoolean("IMPERIAL", false).apply()
                temperatureTextView.text = getString(R.string.tempC, tempMet)
                realFeelView.text = getString(R.string.real_feel_met, realTempMet)
            }

            //if Fahrenheit is requested
            else {
                //Make F white and bold
                degreeSwitch.setTextColor(Color.parseColor("#FFFFFF"))
                degreeSwitch.setTypeface(null, Typeface.BOLD)

                //Make C greyed out and regular text weight
                degreeCTextView.setTextColor(Color.parseColor("#CFCFCF"))
                degreeCTextView.setTypeface(null, Typeface.NORMAL)

                //change degree letter to F
                degreeLetterTextView.text = getString(R.string.degree_f)

                preferences.edit().putBoolean("IMPERIAL", true).apply()
                temperatureTextView.text = getString(R.string.tempF, tempImp)
                realFeelView.text = getString(R.string.real_feel_imp, realTempImp)
            }
        }

        //when details button is pressed, head to details activity
        moreDetailsButton.setOnClickListener{
            val intent = Intent(this, DetailsActivity::class.java)
            startActivity(intent)
        }

        //when details button is pressed, head to details activity
        helpTextView.setOnClickListener{
            val intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
        }
    }

    fun isSunny()
    {
        sunFlare.visibility = View.VISIBLE
        sunCenter.visibility = View.VISIBLE
        sunFlare.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_indefinitely))
    }
    fun isPartlyCloudy()
    {
        sunFlare.visibility = View.VISIBLE
        sunCenter.visibility = View.VISIBLE
        bottomCloud.visibility = View.VISIBLE
        sunFlare.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_indefinitely))
        bottomCloud.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_back_and_forth))
    }
    fun isCloudy()
    {
        topCloud.visibility = View.VISIBLE
        bottomCloud.visibility = View.VISIBLE
        topCloud.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_back_and_forth))
        bottomCloud.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_back_and_forth_delayed))
    }
    fun isRainy()
    {
        rainDrop1.visibility = View.VISIBLE
        rainDrop2.visibility = View.VISIBLE
        rainDrop3.visibility = View.VISIBLE
        topCloud.visibility = View.VISIBLE
        topCloud.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_back_and_forth))
        rainDrop1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_down))
        rainDrop2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_down_delay1))
        rainDrop3.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_down_delay2))
    }

    fun isThundering()
    {
        isRainy()
        thunder.visibility = View.VISIBLE
        thunder.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_down_and_back))
    }

    fun isSnowy()
    {
        snowFlake1.visibility = View.VISIBLE
        snowFlake2.visibility = View.VISIBLE
        snowFlake3.visibility = View.VISIBLE
        topCloud.visibility = View.VISIBLE
        topCloud.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_back_and_forth))
        snowFlake1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_down))
        snowFlake2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_down_delay1))
        snowFlake3.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_down_delay2))
    }

    fun isStarry()
    {
        star1.visibility = View.VISIBLE
        star2.visibility = View.VISIBLE
        star3.visibility = View.VISIBLE
        star1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_indefinitely))
        star2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_delay1))
        star3.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_indefinitely))
    }
}
