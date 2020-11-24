package com.example.weatherapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
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
    private lateinit var degreeSwitch: Switch
    private lateinit var degreeCTextView: TextView
    private lateinit var background: ConstraintLayout
    private lateinit var searchEditText: EditText
    private lateinit var searchImageButton: ImageButton
    private lateinit var progBar: ProgressBar

    private lateinit var sunCenter : ImageView
    private lateinit var sunFlare : ImageView
    private lateinit var bottomCloud : ImageView


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
        degreeSwitch = findViewById(R.id.degreeSwitch)
        degreeCTextView = findViewById(R.id.degreeCTextView)
        background = findViewById(R.id.background)
        searchEditText = findViewById(R.id.mainSearchEditText)
        searchImageButton = findViewById(R.id.mainSearchButton)
        progBar = findViewById(R.id.mainProgBar)

        sunCenter = findViewById(R.id.sunCenterImageView)
        sunFlare = findViewById(R.id.sunFlareImageView)
        bottomCloud = findViewById(R.id.bottomCloudImageView)

        //get intent shared preference variables
        val preferences = getSharedPreferences("weather-app", Context.MODE_PRIVATE)
        val cityCode = preferences.getString("CURR_CITY", "327658")!!
        val imp = preferences.getBoolean("IMPERIAL", true)

        var tempImp = ""
        var tempMet = ""
        var windImp = ""
        var windMet = ""

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

            preferences
                .edit()
                .putString("CURR_CITY", searchEditText.text.toString())
                .apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //start the progress bar and disable clicks to the screen since networking is about to occur
        progBar.visibility=View.VISIBLE
        window.setFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        doAsync {
            val weatherManager = WeatherManager()
            val currentWeather = weatherManager.retrieveWeather(
                cityCode,
                getString(R.string.api_key)
            )
            runOnUiThread {
                tempImp = currentWeather.tempImp
                tempMet = currentWeather.tempMet
                windImp = currentWeather.windImp
                windMet = currentWeather.windMet
                if(imp) {
                    temperatureTextView.text = getString(R.string.tempF, tempImp)
                    windValueTextView.text = getString(R.string.wind_value_imp, currentWeather.windImp)
                }
                else {
                    temperatureTextView.text = getString(R.string.tempC, tempMet)
                    windValueTextView.text = getString(R.string.wind_value_met, currentWeather.windMet)
                }

                if(currentWeather.sunIsOut) {
                    background.background =
                        ColorDrawable(resources.getColor(R.color.colorBackgroundLight))
                    moreDetailsButton.setTextColor(resources.getColor(R.color.colorBackgroundLight))
                }
                else {
                    background.background =
                        ColorDrawable(resources.getColor(R.color.colorBackgroundDark))
                    moreDetailsButton.setTextColor(resources.getColor(R.color.colorBackgroundDark))
                }

                cityTextView.text = currentWeather.city
                countryTextView.text = currentWeather.country
                humidityValueTextView.text = getString(
                    R.string.humidity_value,
                    currentWeather.humidity
                )
                uvValueTextView.text = currentWeather.uv


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

                //networking is done so get rid of prog bar
                progBar.visibility=View.GONE
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
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

                preferences.edit().putBoolean("IMPERIAL", false).apply()
                temperatureTextView.text = getString(R.string.tempC, tempMet)
                windValueTextView.text = getString(R.string.wind_value_met, windMet)
            }

            //if Fahrenheit is requested
            else {
                //Make F white and bold
                degreeSwitch.setTextColor(Color.parseColor("#FFFFFF"))
                degreeSwitch.setTypeface(null, Typeface.BOLD)

                //Make C greyed out and regular text weight
                degreeCTextView.setTextColor(Color.parseColor("#CFCFCF"))
                degreeCTextView.setTypeface(null, Typeface.NORMAL)

                preferences.edit().putBoolean("IMPERIAL", true).apply()
                temperatureTextView.text = getString(R.string.tempF, tempImp)
                windValueTextView.text = getString(R.string.wind_value_imp, windImp)
            }
        }

        //when details button is pressed, head to details activity
        moreDetailsButton.setOnClickListener{
            val intent = Intent(this, DetailsActivity::class.java)
            startActivity(intent)
        }

        //isSunny()
        isPartlyCloudy()
    }

    fun isSunny()
    {
        sunFlare.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_indefinitely))
    }
    fun isPartlyCloudy()
    {
        sunFlare.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_indefinitely))
        bottomCloud.startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_back_and_forth))
    }
}
