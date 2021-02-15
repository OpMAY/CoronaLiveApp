package com.application.coronalive.pref

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object Preferences {
    private lateinit var preferences: SharedPreferences

    // 어플 데이터에 즐겨찾기 데이터 등록
    fun setFavoritePlace(context: Context, key: String, value: Map<String, String?>) {
        preferences = context.getSharedPreferences("Favorite", Activity.MODE_PRIVATE)
        val editor = preferences.edit()
        if (value.containsValue(null))
            editor.putString(key, value.keys.first())
        else
            editor.putString(key + "small City", value[value.keys.first()])
        editor.apply()
    }

    // 어플 데이터에서 즐겨찾기 데이터 불러오기
    fun getFavoritePlace(context: Context, key: String): String? {
        preferences = context.getSharedPreferences("Favorite", Activity.MODE_PRIVATE)
        return preferences.getString(key, "NoID")
    }

    fun getAllFavoritePlaces(context: Context): MutableMap<String, *>? {
        preferences = context.getSharedPreferences("Favorite", Activity.MODE_PRIVATE)
        return preferences.all
    }

    // 즐겨찾기 데이터 삭제
    fun deleteFavoritePlace(context: Context, key: String){
        preferences = context.getSharedPreferences("Favorite", Activity.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.remove(key)
        editor.apply()
    }

    //즐겨찾기 데이터 모두 삭제
    fun deleteAllFavoritePlaces(context: Context){
        preferences = context.getSharedPreferences("Favorite", Activity.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}