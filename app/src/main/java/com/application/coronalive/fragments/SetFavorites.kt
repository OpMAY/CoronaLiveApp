package com.application.coronalive.fragments

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.coronalive.R
import com.application.coronalive.fragments.adapters.SFAdapter
import com.application.coronalive.fragments.adapters.SFData
import com.application.coronalive.main.MainActivity
import kotlinx.android.synthetic.main.activity_set_favorites.*
import kotlinx.android.synthetic.main.toolbar_favorites.*

class SetFavorites : AppCompatActivity() {

    val sfList = arrayListOf(
        SFData("서울", R.drawable.favorites_unselected),
        SFData("경기", R.drawable.favorites_unselected),
        SFData("인천", R.drawable.favorites_unselected),
        SFData("대구", R.drawable.favorites_unselected),
        SFData("경북", R.drawable.favorites_unselected),
        SFData("부산", R.drawable.favorites_unselected),
        SFData("충남", R.drawable.favorites_unselected),
        SFData("경남", R.drawable.favorites_unselected),
        SFData("광주", R.drawable.favorites_unselected),
        SFData("강원", R.drawable.favorites_unselected),
        SFData("충북", R.drawable.favorites_unselected),
        SFData("대전", R.drawable.favorites_unselected),
        SFData("전북", R.drawable.favorites_unselected),
        SFData("울산", R.drawable.favorites_unselected),
        SFData("전남", R.drawable.favorites_unselected),
        SFData("제주", R.drawable.favorites_unselected),
        SFData("세종", R.drawable.favorites_unselected),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_favorites)

        previous_favorites.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        recyclerView_sf.layoutManager = LinearLayoutManager(this)
        recyclerView_sf.adapter = SFAdapter(sfList, this)

    }
}