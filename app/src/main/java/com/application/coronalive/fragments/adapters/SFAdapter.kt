package com.application.coronalive.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.coronalive.R

class SFAdapter(private val sfList: ArrayList<SFData>, private val context:Context) : RecyclerView.Adapter<SFViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SFViewHolder {
        val cellForRow = LayoutInflater.from(context).inflate(R.layout.set_favorites_list, parent, false)
        return SFViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: SFViewHolder, position: Int) {
        val curData = sfList[position]

        holder.regionName.text = curData.regionName
        holder.selected_check.setImageResource(curData.selected_check)

        holder.bind(curData)
    }

    override fun getItemCount(): Int {
        return sfList.size
    }
}