package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

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
        holder.cityTemp.text = "69"
    }

    override fun getItemCount(): Int {
        //return length of results list
        return citySet.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //set up holder variables
        val cityName : TextView = itemView.findViewById(R.id.cityNameTextView)
        val cityTemp : TextView = itemView.findViewById(R.id.cityTempTextView)
    }
}