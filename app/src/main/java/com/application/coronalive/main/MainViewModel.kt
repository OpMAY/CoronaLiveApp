package com.application.coronalive.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.coronalive.api.CLApi
import com.application.coronalive.api.request.CityInformationRequest
import com.application.coronalive.api.response.ApiResponse
import com.application.coronalive.api.response.CityInformationResponse
import com.application.coronalive.open.Event
import com.application.coronalive.pref.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**TODO 기능에 따른 ViewModel 작성
 * 1. 총확진자 수, 실시간 확진자 수, 증감 여부 표시 (O)
 * 2. 즐겨찾기 한 지역에 대한 요약 정보 표시 (O)
 * 3. 포털사이트별 코로나 관련 기사 조회 버튼
 */
class MainViewModel : ViewModel() {
    var pref: Preferences = Preferences
    var api: CLApi = CLApi.instance
    var cityArray = arrayListOf<CityInformationResponse>()
    private val _showErrorToast = MutableLiveData<Event<Boolean>>()
    val showErrorToast: LiveData<Event<Boolean>> = _showErrorToast

    fun updateCityList(context: Context) {
        /** 로직 순서
         * 1. Preference 에 저장된 즐겨찾기 정보 불러오기
         * 2. 해당 정보를 통해 API 호출
         * 3. 받아온 데이터 정렬해서 넘겨주기
         */
        Log.d("STATUS", "Getting Favorites from Prefs..")
        val favorites = pref.getAllFavoritePlaces(context)
        favorites?.let {
            for (entries in it) {
                val bigOrSmall = entries.key
                val name = entries.value as String
                Log.d("PREF", bigOrSmall)
                var bigName = ""
                var smallName: String? = null
                if (bigOrSmall.contains("small City")) {
                    smallName = name
                    bigName = "서울특별시"
                } else {
                    bigName = name
                }
                //Preferences 에서 GetAll 을 했기 때문에 TypeCasting 이 바로 안됨, 설정해 둔 값이 있기에 타입 캐스팅 설정을 넣어둠
                val request = makeRequest(bigName, smallName)
                viewModelScope.launch(Dispatchers.Main) {
                    try {
                        Log.d("STATUS", "Connecting to Server..")
                        val response = getInformation(request)
                        if (response.success) {
                            Log.d("SUCCESS", "데이터 가져오기 성공")
                            response.data?.let { data ->
                                val dList = data.iterator()
                                while (dList.hasNext()) {
                                    val cityInfo = dList.next()
                                    printData(cityInfo)
                                }
                            }
                        } else {
                            Log.d("ERROR", "알 수 없는 오류 발생")
                            _showErrorToast.value = Event(true)
                        }
                    } catch (e: Exception) {
                        Log.d("ERROR", e.message ?: "알 수 없는 오류 발생")
                        _showErrorToast.value = Event(true)
                    }
                }
            }
        } ?: Log.d("STATUS", "No City Saved in Pref")
    }

    private suspend fun getInformation(request: CityInformationRequest) = try {
        api.search(request)
    } catch (e: Exception) {
        ApiResponse.error(
            e.message ?: "도시 정보를 가져오는 중 오류가 발생했습니다."
        )
    }// 2

    private fun makeRequest(bigCityName: String, smallCityName: String?): CityInformationRequest =
        CityInformationRequest(
            bigCityName,
            smallCityName
        )

    private fun printData(response: CityInformationResponse) {
        Log.d("BigCityName", response.bigCityName)
        Log.d("SmallCityName", response.smallCityName?: "null")
        Log.d("TotalInfected", response.totalInfected.toString())
    }
}
