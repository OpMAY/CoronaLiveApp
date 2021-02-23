package com.application.coronalive.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.coronalive.R
import com.application.coronalive.api.response.CityInformationResponse
import com.application.coronalive.fragments.adapters.Data
import com.application.coronalive.fragments.adapters.FavoritesAdapter
import com.application.coronalive.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_domestic.*

class DomesticFragment : Fragment() {

    // 확진자 수 증감에 따른 TextView 색깔 변경 필요
    private val viewModel: MainViewModel by activityViewModels()
    private var dataList: ArrayList<Data>? = ArrayList() // ViewModel 통해서 데이터 받아올 것
    //Data(<지역>, R.drawable.rv_divider, <총 확진자 수>, "명", <총 확진자 수 증감>, <현재 확진자 수>, "명", <현재 확진자 수 증감>, 다음 버튼)
    //https://thdev.tech/androiddev/2020/07/13/Android-Fragment-ViewModel-Example/ 참고


    private fun showData(response: CityInformationResponse): Data {
        val cityName = response.smallCityName ?: response.bigCityName
        return Data(
            cityName,
            R.drawable.rv_divider,
            response.totalInfected,
            "명",
            "+ ${response.totalInfectedInc}",
            response.liveInfected,
            "명",
            "- ${response.liveInfectedInc}",
            R.drawable.next
        )
    }

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
        viewModel.dataArray.observe(viewLifecycleOwner, Observer { arrayList ->
            var newData: Data
            dataList?.clear()
            if (arrayList.iterator().hasNext()) {
                for (element in arrayList) {
                    newData = showData(element)
                    dataList?.add(newData)
                }
            }
            recyclerView.adapter = activity?.let { FavoritesAdapter(dataList, it) }
            recyclerView.adapter?.notifyDataSetChanged()
        })
//      val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        Log.d("size", dataList?.size.toString())

    }
}