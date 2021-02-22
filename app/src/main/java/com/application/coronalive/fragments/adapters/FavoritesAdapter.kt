package com.application.coronalive.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.coronalive.R
import com.application.coronalive.main.MainActivity

class FavoritesAdapter(private val dataList: ArrayList<Data>?, private val context: Context) :
    RecyclerView.Adapter<FavoritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val cellForRow =
            LayoutInflater.from(context).inflate(R.layout.favorites_list, parent, false)
        return FavoritesViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val curData = dataList?.get(position)!!

        holder.region.text = curData.region
        holder.divLine.setImageResource(curData.div_line)
        holder.regionTotalNum.text = curData.regionTotalNum.toString()
        holder.tvRegionPeople.text = curData.tv_region_people
        holder.regionVariation.text = curData.regionVariation
        holder.regionRealTimeNum.text = curData.regionRealTimeNum.toString()
        holder.tvRegionRealTimePeople.text = curData.tv_regionRealTime_people
        holder.regionRealTimeVariation.text = curData.regionRealTimeVariation
        holder.detailsBtn.setImageResource(curData.details_btn)

        holder.itemView.setOnClickListener {
            (context as MainActivity).recyclerClick(curData)
        }
    }

    override fun getItemCount(): Int {
        return dataList?.size!!
    }
}