package com.kbstar.daylog.app.repository

import com.kbstar.daylog.app.MyApplication
import com.kbstar.daylog.app.model.Member
import retrofit2.Callback

class JoinRepository {
    fun register(application: MyApplication, member: Member?, callback: Callback<Member>) {

        val memberAPI = application.memberAPI

        memberAPI.register(member).enqueue(callback)
    }
}