package com.application.coronalive.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.coronalive.R
import com.application.coronalive.api.response.CityInformationResponse
import com.application.coronalive.fragments.adapters.Data
import com.application.coronalive.fragments.adapters.FavoritesAdapter
import com.application.coronalive.main.MainActivity
import com.application.coronalive.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_domestic.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class DomesticFragment : Fragment() {

    // 확진자 수 증감에 따른 TextView 색깔 변경 필요
    lateinit var mContext: Context
    private val viewModel: MainViewModel by activityViewModels()
    private var dataList: ArrayList<Data>? = ArrayList() // ViewModel 통해서 데이터 받아올 것
    //Data(<지역>, R.drawable.rv_divider, <총 확진자 수>, "명", <총 확진자 수 증감>, <현재 확진자 수>, "명", <현재 확진자 수 증감>, 다음 버튼)
    //https://thdev.tech/androiddev/2020/07/13/Android-Fragment-ViewModel-Example/ 참고


    private fun showData(response: CityInformationResponse): Data {
        val cityName = response.smallCityName ?: response.bigCityName
        val formatName = nameFormatChange(cityName)
        return Data(
            formatName,
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MainActivity)
            mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        onNaverButtonClicked()
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

    private fun nameFormatChange(name : String) : String{
        var editName = ""
        if(name.indexOf("시")!= -1){
            editName = when {
                name.indexOf("특별시")!= -1 -> name.removeSuffix("특별시")
                name.indexOf("광역시")!= -1 -> name.removeSuffix("광역시")
                else -> name.removeSuffix("시")
            }
        }else if(name.indexOf("도")!= -1){
            editName = name.removeSuffix("도")
        }
        return editName
    }

    private fun onNaverButtonClicked(){
        naver?.setOnClickListener {
            it.onClick {
                Toast.makeText(mContext, "test", Toast.LENGTH_SHORT).show()
                //viewModel.onNewsButtonClicked(MainActivity(), NAVER)
            }
        }
    }

    companion object {
        const val NAVER =
            "https://search.naver.com/search.naver?query=%EC%BD%94%EB%A1%9C%EB%82%98&where=news&ie=utf8&sm=nws_hty"
        const val NATE = "https://news.nate.com/search?q=%EC%BD%94%EB%A1%9C%EB%82%98"
        const val DAUM =
            "https://search.daum.net/search?w=news&nil_search=btn&DA=NTB&enc=utf8&cluster=y&cluster_page=1&q=%EC%BD%94%EB%A1%9C%EB%82%98"
    }
}