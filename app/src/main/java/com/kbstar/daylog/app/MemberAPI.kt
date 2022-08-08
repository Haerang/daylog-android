package com.kbstar.daylog.app

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MemberAPI {
    @POST("user/register")
    fun register(@Body member:Member): Call<Member>

    @POST("user/idCheck")
    fun idCheck(@Body member:Member): Call<MsgRes>

    @POST("user/login")
    fun login(
        @Body params: Member
    ): Call<ResponseBody>

}