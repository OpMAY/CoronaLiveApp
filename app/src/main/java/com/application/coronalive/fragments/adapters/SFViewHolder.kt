package com.application.coronalive.fragments.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.application.coronalive.R

class SFViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val regionName = itemView.findViewById<TextView>(R.id.regionName)
    val selected_check = itemView.findViewById<ImageView>(R.id.selected_check)
}