package com.kbstar.daylog.app

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MemberAPI {
    @POST("user/login/")
    fun login(
        @Body params: HashMap<String, Any>
    ): Call<Token>
}