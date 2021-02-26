package com.application.coronalive.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.coronalive.R

class SFAdapter(private val sfList: ArrayList<SFData>, private val context:Context) : RecyclerView.Adapter<SFViewHolder>()
{
    var index = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SFViewHolder {
        val cellForRow = LayoutInflater.from(context).inflate(R.layout.set_favorites_list, parent, false)
        return SFViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: SFViewHolder, position: Int) {
        val curData = sfList[position]

        holder.regionName.text = curData.regionName
        holder.selected_check.setImageResource(curData.selected_check)

        // 즐겨찾기 버튼 토글 오류!!
        
        holder.selected_check.setOnClickListener {
            index += 1

            if(index > 1) index = 0

            if(index == 0) {
                holder.selected_check.setImageResource(R.drawable.favorites_unselected)
            }

            if(index == 1) {
                holder.selected_check.setImageResource(R.drawable.favorites_selected)
            }
        }





    }

    override fun getItemCount(): Int {
        return sfList.size
    }

}