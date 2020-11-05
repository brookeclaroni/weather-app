package com.example.weatherapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
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

        //fill in city name
        holder.cityName.text = currentCity

        doAsync {
            val weatherManager = WeatherManager()
            val currentWeather = weatherManager.retrieveWeather(currentCity, holder.card.context.getString(R.string.api_key))
            holder.card.context.runOnUiThread {
                if (imp)
                    holder.cityTemp.text = getString(R.string.tempF, currentWeather.tempImp)
                else
                    holder.cityTemp.text = getString(R.string.tempC, currentWeather.tempMet)
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
    }
}