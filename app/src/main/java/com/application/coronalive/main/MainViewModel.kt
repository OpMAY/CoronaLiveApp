package com.application.coronalive.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(house: HouseType, private val repository: Repository) : ViewModel(){
    val characterList : LiveData<List<Char>> = liveData(Dispatchers.IO) {
        loading.postValue(true)
        emit(repository.getCharacters(house.name))
    }

    private val loading = MutableLiveData<Boolean>()
}
