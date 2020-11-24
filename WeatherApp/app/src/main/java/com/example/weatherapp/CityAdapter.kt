package com.example.weatherapp

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread

class CityAdapter (private val citySet: MutableSet<String>) : RecyclerView.Adapter<CityAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //fill in recycler view with city rows
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_city, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //get shared prefs
        val preferences = holder.card.context.getSharedPreferences("weather-app", Context.MODE_PRIVATE)
        val savedCitySet = preferences.getStringSet("SAVED_CITIES", mutableSetOf())
        val imp = preferences.getBoolean("IMPERIAL", true)

        //retrieve the current result to add to the recycler view
        val currentCity = citySet.elementAt(position)

        //acknowledge that this city has been favorited
        holder.cityName.isSelected = true

        //start the progress bar and disable clicks to the screen since networking is about to occur
        holder.progBar.visibility=View.VISIBLE

        doAsync {
            val weatherManager = WeatherManager()
            val currentWeather = weatherManager.retrieveWeather(currentCity, holder.card.context.getString(R.string.api_key))
            holder.card.context.runOnUiThread {
                holder.cityName.text = currentWeather.city

                if (imp) {
                    holder.cityTemp.text = getString(R.string.tempF, currentWeather.tempImp)
                    holder.degLetter.text = getString(R.string.degree_f)
                }
                else {
                    holder.cityTemp.text = getString(R.string.tempC, currentWeather.tempMet)
                    holder.degLetter.text = getString(R.string.degree_c)
                }

                if (currentWeather.sunIsOut)
                    holder.card.setCardBackgroundColor(resources.getColor(R.color.colorBackgroundLight))
                else
                    holder.card.setCardBackgroundColor(resources.getColor(R.color.colorBackgroundDark))

                //get and set weather icon
                if(currentWeather.weatherInt in 1..2)
                    holder.weatherIcon.setImageResource(R.drawable.day_sunny)
                else if(currentWeather.weatherInt in 3..6)
                    holder.weatherIcon.setImageResource(R.drawable.day_mostly_sunny)
                else if(currentWeather.weatherInt in 7..11 || currentWeather.weatherInt in 35..38)
                    holder.weatherIcon.setImageResource(R.drawable.cloudy)
                else if(currentWeather.weatherInt in 12..14 || currentWeather.weatherInt in 39..40 || currentWeather.weatherInt == 18)
                    holder.weatherIcon.setImageResource(R.drawable.showers)
                else if(currentWeather.weatherInt in 15..17)
                    holder.weatherIcon.setImageResource(R.drawable.day_tstorms)
                else if(currentWeather.weatherInt in 41..42)
                    holder.weatherIcon.setImageResource(R.drawable.ic_white_thunder_night)
                else if(currentWeather.weatherInt in 19..29)
                    holder.weatherIcon.setImageResource(R.drawable.ic_white_snow_day)
                else if(currentWeather.weatherInt in 43..44)
                    holder.weatherIcon.setImageResource(R.drawable.ic_white_snow_night)
                else if(currentWeather.weatherInt in 33..34)
                    holder.weatherIcon.setImageResource(R.drawable.ic_white_moon)
                else{
                    if (currentWeather.sunIsOut)
                        holder.weatherIcon.setImageResource(R.drawable.day_sunny)
                    else
                        holder.weatherIcon.setImageResource(R.drawable.ic_white_moon)
                }

//                if(currentWeather.weatherInt in 1..2)
//                    holder.weatherIcon.setImageResource(R.drawable.sun)//fix to updated sun
//                else if(currentWeather.weatherInt in 3..6)
//                    holder.weatherIcon.setImageResource(R.drawable.sun)//fix to sun with part cloud
//                else if(currentWeather.weatherInt in 7..11 || currentWeather.weatherInt in 35..38)
//                    holder.weatherIcon.setImageResource(R.drawable.ic_top_cloud)
//                else if(currentWeather.weatherInt in 12..14 || currentWeather.weatherInt in 39..40 || currentWeather.weatherInt == 18)
//                    holder.weatherIcon.setImageResource(R.drawable.ic_rain_drop)
//                else if(currentWeather.weatherInt in 15..17 || currentWeather.weatherInt in 41..42)
//                    holder.weatherIcon.setImageResource(R.drawable.ic_thunder)
//                else if(currentWeather.weatherInt in 19..29 || currentWeather.weatherInt in 43..44)
//                    holder.weatherIcon.setImageResource(R.drawable.ic_snowflake)
//                else if(currentWeather.weatherInt in 33..34)
//                    holder.weatherIcon.setImageResource(R.drawable.ic_star)
//                else{
//                    if (currentWeather.sunIsOut)
//                        holder.weatherIcon.setImageResource(R.drawable.sun)//fix to updated sun
//                    else
//                        holder.weatherIcon.setImageResource(R.drawable.ic_star)
//                }

                holder.progBar.visibility=View.GONE
            }
        }

        //if star is grey and then the user clicks to save
        holder.starOff.setOnClickListener{
            //change the color, object data, and update shared preferences
            holder.starOn.visibility = View.VISIBLE
            savedCitySet?.add(currentCity)
            preferences.edit().putStringSet("SAVED_CITIES", savedCitySet).apply()
        }

        //if star is yellow and then the user clicks to unsave
        holder.starOn.setOnClickListener{
            //change the color, object data, and update shared preferences
            holder.starOn.visibility = View.INVISIBLE
            savedCitySet?.remove(currentCity)
            preferences.edit().putStringSet("SAVED_CITIES", savedCitySet).apply()
        }

        //when the card is clicked go to that city's landing page
        holder.card.setOnClickListener {
            preferences
                .edit()
                .putString("CURR_CITY", currentCity)
                .apply()
            val intent = Intent(it.context, MainActivity::class.java)
            it.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        //return length of results list
        return citySet.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //set up holder variables
        val cityName : TextView = itemView.findViewById(R.id.cityNameTextView)
        val cityTemp : TextView = itemView.findViewById(R.id.cityTempTextView)
        val card : CardView = itemView.findViewById(R.id.cardView)
        val starOn : ImageButton = itemView.findViewById(R.id.starOnButton2)
        val starOff : ImageButton = itemView.findViewById(R.id.starOffButton2)
        val progBar : ProgressBar = itemView.findViewById(R.id.cardProgBar)
        val degLetter : TextView = itemView.findViewById(R.id.degLetterTextView)
        val weatherIcon : ImageView = itemView.findViewById(R.id.whiteWeatherIcon)
    }
}