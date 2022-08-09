package com.kbstar.daylog.app.repository

import com.kbstar.daylog.app.model.Member
import com.kbstar.daylog.app.MyApplication
import okhttp3.ResponseBody
import retrofit2.Callback

class LoginRepository {
    fun login(application: MyApplication, member: Member, callback: Callback<ResponseBody>){

        //서버 요청... 서버 요청 결과는 내 역할 아니다.. callback 에서 알아서...
        val memberAPI = application.memberAPI

        memberAPI.login(member).enqueue(callback)
    }
}