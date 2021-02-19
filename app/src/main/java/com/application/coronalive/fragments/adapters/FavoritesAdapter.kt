package com.application.coronalive.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.application.coronalive.R
import com.application.coronalive.main.MainActivity
import kotlinx.android.synthetic.main.favorites_list.view.*

class Data (val region:String, val div_line:Int, val regionTotalNum:Int, val tv_region_people:String,
            val regionVariation:String, val regionRealTimeNum:Int, val tv_regionRealTime_people:String,
            val regionRealTimeVariation:String, val details_btn:Int)

class FavoritesViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val region = v.region
    val div_line = v.div_line
    val regionTotalNum = v.regionTotalNum
    val tv_region_people = v.tv_region_people
    val regionVariation = v.regionVariation
    val regionRealTimeNum = v.regionRealTimeNum
    val tv_regionRealTime_people = v.tv_regionRealTime_people
    val regionRealTimeVariation = v.regionRealTimeVariation
    var details_btn = v.details_btn
}

class FavoritesAdapter (val dataList:ArrayList<Data>, val context:Context) : RecyclerView.Adapter<FavoritesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val cellForRow = LayoutInflater.from(context).inflate(R.layout.favorites_list, parent, false)
        return FavoritesViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val curData = dataList[position]

        holder.region.text = curData.region
        holder.div_line.setImageResource(curData.div_line)
        holder.regionTotalNum.text = curData.regionTotalNum.toString()
        holder.tv_region_people.text = curData.tv_region_people
        holder.regionVariation.text = curData.regionVariation
        holder.regionRealTimeNum.text = curData.regionRealTimeNum.toString()
        holder.tv_regionRealTime_people.text = curData.tv_regionRealTime_people
        holder.regionRealTimeVariation.text = curData.regionRealTimeVariation
        holder.details_btn.setImageResource(curData.details_btn)

        holder.itemView.setOnClickListener {
            (context as MainActivity).RecyclerClick(curData)
        }
    }

    override fun getItemCount(): Int {
        if (dataList != null) {
            return dataList.size
        }
        return 0
    }
}