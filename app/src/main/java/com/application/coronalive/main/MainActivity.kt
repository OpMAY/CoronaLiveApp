package com.application.coronalive.main

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.application.coronalive.R
import com.application.coronalive.databinding.ActivityMainBinding
import com.application.coronalive.fragments.DomesticFragment
import com.application.coronalive.fragments.WorldFragment
import com.application.coronalive.fragments.adapters.ViewPagerAdapter
import com.application.coronalive.pref.Preferences
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.home.*
import kotlinx.android.synthetic.main.navigationdrawer.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
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
        // Update UI
        for (elements in viewModel.cityArray) {
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
            //R.id.add_favorite -> addPref()
        }
        layout_drawer.closeDrawers()
        return false
    }

    private fun addPref() {
        Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show()
        val a = Preferences
        a.setFavoritePlace(this, "second", mapOf("서울특별시" to null))
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

    }
}