package com.application.coronalive.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.application.coronalive.R
import com.application.coronalive.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main) -> Binding 작업으로 필요없는 구문
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        subscribeUI(binding)
    }

    private fun subscribeUI(binding: ActivityMainBinding){
        //ViewModel은 instance로 직접 초기화가 불가능함
        //아래 ViewModelFactory, ViewModelProvider로 ViewModel을 초기화 함
        //https://readystory.tistory.com/176 참조
        val factory = MainViewModelFactory()
        val viewModel : MainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        // Update City Info
        GlobalScope.launch(Dispatchers.Main){
            viewModel.updateCityList(this@MainActivity)
        }
        // Update UI
        for(elements in viewModel.cityArray){
            binding.city = elements
        }
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}