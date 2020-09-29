package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CityAdapter (private val results: List<Weather>) : RecyclerView.Adapter<CityAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //fill in recycler view with city rows
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_city, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //retrieve the current result to add to the recycler view
        val currentResult = results[position]

        //fill in event, location, and artist
        holder.cityName.text = currentResult.city
        holder.cityTemp.text = currentResult.temp
    }

    override fun getItemCount(): Int {
        //return length of results list
        return results.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //set up holder variables
        val cityName : TextView = itemView.findViewById(R.id.cityNameTextView)
        val cityTemp : TextView = itemView.findViewById(R.id.cityTempTextView)
    }
}