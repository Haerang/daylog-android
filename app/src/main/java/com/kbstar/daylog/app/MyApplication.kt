package com.kbstar.daylog.app

import android.app.Application
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication:Application() {
    val member = Member()
    val msgRes = MsgRes()
    var memberAPI: MemberAPI

    val retrofit: Retrofit
        get(){
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient().newBuilder().addInterceptor(interceptor).build()

            val gson = GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                .create()

            return Retrofit.Builder()
                .baseUrl("http://10.10.223.31:8080/mobile/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    init{
        memberAPI = retrofit.create(MemberAPI::class.java)
    }
}