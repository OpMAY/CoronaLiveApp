package com.application.coronalive.fragments.adapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.application.coronalive.R
import kotlinx.android.synthetic.main.set_favorites_list.view.*

class SFViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val regionName = itemView.findViewById<TextView>(R.id.regionName)
    val selected_check = itemView.findViewById<ImageView>(R.id.selected_check)

    var index = 0

    fun bind(sfData: SFData) {
        itemView.setOnClickListener {
            index += 1

            if (index > 1) { index = 0 }
            if (index == 0) {
                itemView.selected_check.setImageResource(R.drawable.favorites_unselected)
            }
            if (index == 1) {
                itemView.selected_check.setImageResource(R.drawable.favorites_selected)
            }



        }
    }
}