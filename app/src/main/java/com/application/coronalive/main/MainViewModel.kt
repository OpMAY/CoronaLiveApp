package com.application.coronalive.main

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.coronalive.api.CLApi
import com.application.coronalive.api.request.CityInformationRequest
import com.application.coronalive.api.response.ApiResponse
import com.application.coronalive.api.response.CityInformationResponse
import com.application.coronalive.open.Event
import com.application.coronalive.pref.CityRelationship
import com.application.coronalive.pref.Preferences
import com.application.coronalive.webview.WebViewActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**TODO 기능에 따른 ViewModel 작성
 * 1. 총확진자 수, 실시간 확진자 수, 증감 여부 표시 (O)
 * 2. 즐겨찾기 한 지역에 대한 요약 정보 표시 (O)
 * 3. 포털사이트별 코로나 관련 기사 조회 버튼
 */
class MainViewModel : ViewModel() {
    var pref: Preferences = Preferences
    var api: CLApi = CLApi.instance
    var cityArray: ArrayList<CityInformationResponse>? = arrayListOf()
    private val _showErrorToast = MutableLiveData<Event<Boolean>>()
    val showErrorToast: LiveData<Event<Boolean>> = _showErrorToast

    private val _showUpdatedToast = MutableLiveData<Event<Boolean>>()
    val showUpdatedToast: LiveData<Event<Boolean>> = _showUpdatedToast

    var dataArray = MutableLiveData<ArrayList<CityInformationResponse>>()

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
                val name = entries.value as String
                val cityArray = name.split(" ")
                val bigName = cityArray[0]
                val smallName = if (cityArray.size > 1) cityArray[1] else null
                //Preferences 에서 GetAll 을 했기 때문에 TypeCasting 이 바로 안됨, 설정해 둔 값이 있기에 타입 캐스팅 설정을 넣어둠
                val request = makeRequest(bigName, smallName)
                GlobalScope.launch(Dispatchers.Default) {
                    dataMethod(request)
                }
            }

        } ?: run {
            Log.d("STATUS", "No City Saved in Pref")
        }
        _showUpdatedToast.value = Event(true)
    }

    private suspend fun getInformation(request: CityInformationRequest) = try {
        api.search(request)
    } catch (e: Exception) {
        ApiResponse.error(
            e.message ?: "도시 정보를 가져오는 중 오류가 발생했습니다."
        )
    }// 2

    private suspend fun dataMethod(request: CityInformationRequest) {
        try {
            Log.d("STATUS", "Connecting to Server..")
            val response = getInformation(request)
            if (response.success) {
                Log.d("SUCCESS", "데이터 가져오기 성공")
                response.data?.let { data ->
                    val dList = data.iterator()
                    while (dList.hasNext()) {
                        val cityInfo = dList.next()
                        cityArray?.add(cityInfo)
                        dataArray.postValue(cityArray)
                    }
                    delay(500)
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

    private fun makeRequest(bigCityName: String, smallCityName: String?): CityInformationRequest =
        CityInformationRequest(
            bigCityName,
            smallCityName
        )


    fun addPref(context: Context) {
        val alert = AlertDialog.Builder(context)
        Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show()
        val mainArr = arrayOf("수도, 광역시", "도", "세종시")
        alert.apply {
            setTitle("즐겨찾기 등록")
            setItems(mainArr) { _, which ->
                if (which != 2) {
                    val position: Array<String> =
                        when (which) {
                            0 -> CityRelationship.CAPITALISM_CITY
                            1 -> CityRelationship.LOCALIZATION
                            else -> throw IllegalStateException("잘못된 접근입니다.")
                        }
                    addPref2(position, context)
                } else {
                    addPrefLast("세종시", null, context)
                }
            }
            create()
            show()
        }
    }

    private fun addPref2(position: Array<String>, context: Context) {
        val alert = AlertDialog.Builder(context)
        alert.apply {
            setTitle("즐겨찾기 등록")
            setItems(position) { _, which1 ->
                val bigCity = position[which1]
                val smallCityArray = CityRelationship().getRelatedSmallCities(bigCity)
                addPref3(smallCityArray, bigCity, context)
            }
            create()
            show()
        }
    }

    private fun addPref3(smallCityArray: Array<String>, bigCity: String, context: Context) {
        val alert = AlertDialog.Builder(context)
        alert.apply {
            setTitle("즐겨찾기 등록")
            setItems(smallCityArray) { _, which2 ->
                val smallCity = smallCityArray[which2]
                if (smallCity == "전체")
                    addPrefLast(bigCity, null, context)
                else
                    addPrefLast(bigCity, smallCity, context)
            }
            create()
            show()
        }
    }

    private fun addPrefLast(bigCity: String, smallCity: String?, context: Context) {
        val alert = AlertDialog.Builder(context)
        alert.apply {
            setTitle("확인")
            var key: String
            val bool: Boolean = if (smallCity == null) {
                setMessage("등록하려는 도시가 $bigCity 가 맞나요?")
                false
            } else {
                setMessage("등록하려는 도시가 $bigCity $smallCity 가 맞나요?")
                true
            }
            setPositiveButton("확인") { _, _ ->
                Log.d("B Input", bigCity)
                Log.d("S Input", smallCity ?: "null")
                key = if (bool)
                    "$bigCity $smallCity"
                else
                    bigCity
                val a = Preferences
                if (a.isFavoriteExist(context, key)) {
                    Toast.makeText(context, "이미 즐겨찾기로 등록되어있는 지역입니다.", Toast.LENGTH_SHORT).show()
                } else {
                    a.setFavoritePlace(
                        context,
                        key,
                        mapOf(bigCity to smallCity)
                    )
                    GlobalScope.launch(Dispatchers.IO) {
                        val request = makeRequest(bigCity, smallCity)
                        dataMethod(request)
                    }
                }
            }
            setNegativeButton("취소") { _, _ ->
            }
            create()
            show()
        }
    }

    fun onNewsButtonClicked(context: Context, Link : String) {
        val intent = Intent(context, WebViewActivity::class.java)
        intent.putExtra("Clicked URL", Link)
        startActivity(context, intent, null)
    }


}
