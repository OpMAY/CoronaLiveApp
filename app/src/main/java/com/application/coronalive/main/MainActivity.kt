package com.application.coronalive.main

import android.os.Bundle
import com.application.coronalive.R
import com.application.coronalive.mvvmbase.BaseActivity

class MainActivity : BaseActivity() {
    private val binding by binding<>(R.layout.activity_main)
    private var house by lazy { intent.getSerializableExtra(KEY_HOUSE) as HouseType }
    private var viewModel : MainViewModel by viewModel { parametersOf(house) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply{
            house = this@MainActivity.house
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
        }
    }
}