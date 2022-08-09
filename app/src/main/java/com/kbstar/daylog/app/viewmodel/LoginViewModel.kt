package com.kbstar.daylog.app.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kbstar.daylog.app.model.Member
import com.kbstar.daylog.app.MyApplication
import com.kbstar.daylog.app.repository.LoginRepository
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    val myApplication: MyApplication

    init {
        myApplication = (application as MyApplication)
    }
    //activity, fragment 로부터 요청을 받아서.. 적절한 repository 를 실행시키고..
    //실행 결과를 livedata 로 발생해서.. 화면 조정을 activity 에서 처리할 수 있게..

    val loginLiveData = MutableLiveData<String>()

    fun login(member: Member) {
        val repository = LoginRepository()

        repository.login(myApplication, member, object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val json = response.body()?.string()!!

                Log.d("kkang", "login:json:" + json)

                // val token = response.body()!!
                Log.d("login", json)
                if (json.equals("{\"resMsg\":\"fail\"}")) {
                    loginLiveData.postValue("error")
                } else {
                    val editor = myApplication.prefs.edit()
                    editor.putString("member", json)
                    editor.putString("id", member.id)
                    editor.commit()

                    loginLiveData.postValue("success")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
                call.cancel()
                loginLiveData.postValue("error")
            }
        })
    }
}