package com.application.coronalive.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.application.coronalive.R
import com.application.coronalive.main.MainActivity
import kotlinx.android.synthetic.main.favorites_list.view.*

class Data (val region:String, val regionTotalNum:String, val regionVariation: String, val regionRealTimeNum:String, val regionRealTimeVariation:String) {
}

class FavoritesListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val region = v.region
    val regionTotalNum = v.regionTotalNum
    val regionVariation = v.regionVariation
    val regionRealTimeNum = v.regionRealTimeNum
    val regionRealTimeVariation = v.regionRealTimeVariation
}

class FavoritesListAdapter(val DataList:ArrayList<Data>, val context:Context) : RecyclerView.Adapter<FavoritesListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesListViewHolder {
        val cellForRow = LayoutInflater.from(context).inflate(R.layout.favorites_list, parent, false)
        return FavoritesListViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: FavoritesListViewHolder, position: Int) {
        val curData = DataList[position]

        holder.region.text = curData.region
        holder.regionTotalNum.text = curData.regionTotalNum
        holder.regionVariation.text = curData.regionVariation
        holder.regionRealTimeNum.text = curData.regionRealTimeNum
        holder.regionRealTimeVariation.text = curData.regionRealTimeVariation

        // ## 클릭 이벤트 리스너 구현 ##
        holder.itemView.setOnClickListener{
            (context as MainActivity).RecyclerClick(curData)
        }
    }

    override fun getItemCount(): Int {
        return DataList.size
    }
}