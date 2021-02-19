package com.application.coronalive.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.coronalive.R
import com.application.coronalive.fragments.adapters.Data
import com.application.coronalive.fragments.adapters.FavoritesAdapter
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_domestic.*
import kotlinx.android.synthetic.main.fragment_domestic.view.*

class DomesticFragment : Fragment() {

    // 확진자 수 증감에 따른 TextView 색깔 변경 필요

   val dataList = arrayListOf(
       Data("경기", R.drawable.rv_divider, 22232, "명",
            "+ 189", 71, "명", "- 4", R.drawable.next),
       Data("인천", R.drawable.rv_divider, 4245, "명",
            "+ 38", 30, "명", "- 5", R.drawable.next),
       Data("서울", R.drawable.rv_divider, 27107, "명",
            "+ 180", 22, "명", "- 16", R.drawable.next),
       Data("경북", R.drawable.rv_divider, 3164, "명",
            "+ 22", 6, "명", "- 5", R.drawable.next)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_domestic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = activity?.let { FavoritesAdapter(dataList, it) }

        Log.d("size", dataList.size.toString())

    }
}