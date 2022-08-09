package com.kbstar.daylog.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kbstar.daylog.app.MyApplication
import com.kbstar.daylog.app.model.Member
import com.kbstar.daylog.app.repository.JoinRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinViewModel(application: Application): AndroidViewModel(application) {
    val myApplication: MyApplication
    var user: Member

    init {
        myApplication = (application as MyApplication)
        user = Member()
    }

    // livedata 선언
    val joinLiveData = MutableLiveData<String>()

    fun register(member: Member?){
        val repository = JoinRepository()

        repository.register(myApplication, member, object: Callback<Member>{
            override fun onResponse(call: Call<Member>, response: Response<Member>) {
                val json = response.body()?.toString()

                if (json.equals("{\"resMsg\":\"fail\"}")) {
                    joinLiveData.postValue("error")
                }else{
                    joinLiveData.postValue("success")
                }

            }

            override fun onFailure(call: Call<Member>, t: Throwable) {
                t.printStackTrace()
                call.cancel()
                joinLiveData.postValue("error")
            }
        })
    }

}