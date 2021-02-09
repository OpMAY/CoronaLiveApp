package com.application.coronalive.intro

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
<<<<<<< HEAD:app/src/main/java/com/application/coronalive/intro/IntroActivity.kt
import com.application.coronalive.R

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
=======
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.application.coronalive.fragments.DomesticFragment
import com.application.coronalive.fragments.WorldFragment
import com.application.coronalive.fragments.adapters.ViewPagerAdapter
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.home.*
import kotlinx.android.synthetic.main.navigationdrawer.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setUpTabs()

        menu.setOnClickListener { layout_drawer.openDrawer(GravityCompat.START) }
        navigationView.setNavigationItemSelectedListener(this)
    };


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> Toast.makeText(this, "설정", Toast.LENGTH_SHORT).show()
            R.id.region -> Toast.makeText(this, "전체 지역별", Toast.LENGTH_SHORT).show()
            R.id.faq -> Toast.makeText(this, "FAQ 및 제보하기", Toast.LENGTH_SHORT).show()
            R.id.share -> Toast.makeText(this, "SNS 공유", Toast.LENGTH_SHORT).show()
            R.id.message -> Toast.makeText(this, "재난문자", Toast.LENGTH_SHORT).show()
            R.id.guidelines -> Toast.makeText(this, "거리두기 지침", Toast.LENGTH_SHORT).show()
        }
        layout_drawer.closeDrawers()
        return false
    }

    override fun onBackPressed() {
        if (layout_drawer.isDrawerOpen(GravityCompat.START)) layout_drawer.closeDrawers()
        else super.onBackPressed()
    }

    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(DomesticFragment(), "국내")
        adapter.addFragment(WorldFragment(), "세계")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

>>>>>>> origin/design:app/src/main/java/com/application/coronalive/MainActivity.kt
    }

}

