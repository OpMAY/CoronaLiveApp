package com.application.coronalive.main

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.coronalive.R
import com.application.coronalive.databinding.ActivityMainBinding
import com.application.coronalive.fragments.DomesticFragment
import com.application.coronalive.fragments.WorldFragment
import com.application.coronalive.fragments.adapters.Data
import com.application.coronalive.fragments.adapters.FavoritesAdapter
import com.application.coronalive.fragments.adapters.ViewPagerAdapter
import com.application.coronalive.pref.CityRelationship
import com.application.coronalive.pref.Preferences
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_domestic.*
import kotlinx.android.synthetic.main.home.*
import kotlinx.android.synthetic.main.navigationdrawer.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var pressedTime : Long = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main) -> Binding 작업으로 필요없는 구문
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setUpTabs()

        menu.setOnClickListener { layout_drawer.openDrawer(GravityCompat.START) }
        navigationView.setNavigationItemSelectedListener(this)
        subscribeUI(binding)
    }


    private fun subscribeUI(binding: ActivityMainBinding) {
        //ViewModel은 instance로 직접 초기화가 불가능함
        //아래 ViewModelFactory, ViewModelProvider로 ViewModel을 초기화 함
        //https://readystory.tistory.com/176 참조
        val factory = MainViewModelFactory()
        val viewModel: MainViewModel =
            ViewModelProvider(this, factory).get(MainViewModel::class.java)

        // Update City Info
        viewModel.updateCityList(this@MainActivity)

        viewModel.showErrorToast.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, "알 수 없는 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.showUpdatedToast.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, "정보 업데이트 완료", Toast.LENGTH_SHORT).show()
            }
        })
        // Update UI
        for (elements in viewModel.cityArray!!) {
            binding.city = elements
        }
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> Toast.makeText(this, "설정", Toast.LENGTH_SHORT).show()
            R.id.region -> Toast.makeText(this, "전체 지역별", Toast.LENGTH_SHORT).show()
            R.id.faq -> Toast.makeText(this, "FAQ 및 제보하기", Toast.LENGTH_SHORT).show()
            R.id.share -> Toast.makeText(this, "SNS 공유", Toast.LENGTH_SHORT).show()
            R.id.message -> Toast.makeText(this, "재난문자", Toast.LENGTH_SHORT).show()
            R.id.guidelines -> Toast.makeText(this, "거리두기 지침", Toast.LENGTH_SHORT).show()
            R.id.add_favorite -> addPref() //-> 즐겨찾기 등록화면에서 처리
        }
        layout_drawer.closeDrawers()
        return false
    }

    override fun onBackPressed() {
        if (layout_drawer.isDrawerOpen(GravityCompat.START)) layout_drawer.closeDrawers()
        else {
            if (System.currentTimeMillis() - pressedTime <= 2000)
                finish()
            else {
                pressedTime = System.currentTimeMillis()
                Toast.makeText(this, "이전 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(DomesticFragment(), "국내")
        adapter.addFragment(WorldFragment(), "세계")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

    }


    private fun addPref(){
        val factory = MainViewModelFactory()
        val viewModel: MainViewModel =
            ViewModelProvider(this, factory).get(MainViewModel::class.java)

        viewModel.addPref(this)
    }

    fun recyclerClick(curData: Data) {
        Toast.makeText(this, curData.region, Toast.LENGTH_SHORT).show()
    }

}