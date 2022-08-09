package com.kbstar.daylog.app.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kbstar.daylog.app.MyApplication
import com.kbstar.daylog.app.model.Place
import com.kbstar.daylog.app.repository.PlaceRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    val myApplication: MyApplication

    init {
        myApplication = (application as MyApplication)
    }

    val regionPlaceLiveData = MutableLiveData<MutableList<Place>>()

    fun selectByRegion(place: Place, placeList: MutableList<Place>) {
        val repository = PlaceRepository()

        repository.selectByRegion(myApplication, place, object : Callback<MutableList<Place>> {
            override fun onResponse(
                call: Call<MutableList<Place>>,
                response: Response<MutableList<Place>>
            ) {
                Log.d("categoryFragment", "fun onResponse!!")
                regionPlaceLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<MutableList<Place>>, t: Throwable) {
                t.printStackTrace()
                call.cancel()
                regionPlaceLiveData.postValue(mutableListOf())
            }
        })
    }
}