package com.kbstar.daylog.app.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kbstar.daylog.app.MyApplication
import com.kbstar.daylog.app.model.Member
import com.kbstar.daylog.app.model.Place
import com.kbstar.daylog.app.repository.ProfileRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(application: Application): AndroidViewModel(application){

    val myApplication: MyApplication

    init {
        myApplication = (application as MyApplication)
    }

    val favoriteLiveData = MutableLiveData<MutableList<Place>>()

    fun getFavorites(member: Member, placeList:MutableList<Place>){
        val repository = ProfileRepository()

        repository.getFavorites(myApplication,member, object : Callback<MutableList<Place>>{
            override fun onResponse(
                call: Call<MutableList<Place>>,
                response: Response<MutableList<Place>>
            ) {
                Log.d("favoriteFragment", "fun onResponse!!")
                favoriteLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<MutableList<Place>>, t: Throwable) {
                t.printStackTrace()
                call.cancel()
                favoriteLiveData.postValue(mutableListOf())
            }
        })

    }

}