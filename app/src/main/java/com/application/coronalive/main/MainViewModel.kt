package com.application.coronalive.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.application.coronalive.api.CLApi
import com.application.coronalive.api.request.CityInformationRequest
import com.application.coronalive.api.response.CityInformationResponse
import com.application.coronalive.pref.Preferences

/**TODO 기능에 따른 ViewModel 작성
 * 1. 총확진자 수, 실시간 확진자 수, 증감 여부 표시 (O)
 * 2. 즐겨찾기 한 지역에 대한 요약 정보 표시 (O)
 * 3. 포털사이트별 코로나 관련 기사 조회 버튼
 */
class MainViewModel : ViewModel() {
    var pref: Preferences = Preferences
    var api: CLApi = CLApi.instance
    var cityArray = arrayListOf<CityInformationResponse>()
    suspend fun updateCityList(context: Context) {
        /** 로직 순서
         * 1. Preference 에 저장된 즐겨찾기 정보 불러오기
         * 2. 해당 정보를 통해 API 호출
         * 3. 받아온 데이터 정렬해서 넘겨주기
         */
        Log.d("STATUS","Getting Favorites from Prefs..")
        val favorites = pref.getAllFavoritePlaces(context)
        favorites?.let {
            for (entries in it) {
                val bigOrSmall = entries.value as String
                var bigName = ""
                var smallName : String? = null
                if(bigOrSmall.contains("small City")){
                    smallName = bigOrSmall
                }else{
                    bigName = bigOrSmall
                }
                //Preferences 에서 GetAll 을 했기 때문에 TypeCasting 이 바로 안됨, 설정해 둔 값이 있기에 타입 캐스팅 설정을 넣어둠
                val request = makeRequest("서울특별시", "광진구")
                Log.d("STATUS","Connecting to Server..")
                val response = api.search(request)// 2
                if (response.success) {
                    val dataSet = response.data!!
                    cityArray.add(dataSet)// 3
                }

            }
        } ?: Log.d("STATUS", "No City Saved in Pref")
        Log.d("cityInfo", cityArray.toString())
    }

    fun onButtonClickHandler(){
        
    }

    private fun makeRequest(bigCityName : String, smallCityName : String?) : CityInformationRequest =
        CityInformationRequest(
            bigCityName,
            smallCityName
        )
}
