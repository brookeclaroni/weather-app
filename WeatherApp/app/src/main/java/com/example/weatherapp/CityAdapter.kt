package com.example.weatherapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        //retrieve the current result to add to the recycler view
        val currentCity = citySet.elementAt(position)

        //fill in event, location, and artist
        holder.cityName.text = currentCity

        doAsync {
            val weatherManager = WeatherManager()
            val currentWeather = weatherManager.retrieveWeather(currentCity, holder.card.context.getString(R.string.api_key))
            holder.card.context.runOnUiThread {
                holder.cityTemp.text = getString(R.string.temperature, currentWeather.temp)
            }
        }

        holder.card.setOnClickListener {
            val preferences = holder.card.context.getSharedPreferences("weather-app", Context.MODE_PRIVATE)
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
    }
}