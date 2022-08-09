package com.kbstar.daylog.app.repository

import com.kbstar.daylog.app.MyApplication
import com.kbstar.daylog.app.model.Member
import com.kbstar.daylog.app.model.Place
import retrofit2.Callback

class ProfileRepository {
    fun getFavorites(application: MyApplication, member: Member, callback: Callback<MutableList<Place>>){

        //서버 요청... 서버 요청 결과는 내 역할 아니다.. callback 에서 알아서...
        val placeAPI = application.placeAPI

        placeAPI.selectSavedPlace(member).enqueue(callback)
    }
}