package com.application.coronalive.fragments.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.favorites_list.view.*

class FavoritesViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val region: TextView = v.region
    val divLine: ImageView = v.div_line
    val regionTotalNum: TextView = v.regionTotalNum
    val tvRegionPeople: TextView = v.tv_region_people
    val regionVariation: TextView = v.regionVariation
    val regionRealTimeNum: TextView = v.regionRealTimeNum
    val tvRegionRealTimePeople: TextView = v.tv_regionRealTime_people
    val regionRealTimeVariation: TextView = v.regionRealTimeVariation
    val detailsBtn: ImageView = v.details_btn
}