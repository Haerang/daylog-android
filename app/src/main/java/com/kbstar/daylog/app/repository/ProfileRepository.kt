package com.kbstar.daylog.app.repository

import com.kbstar.daylog.app.MyApplication
import com.kbstar.daylog.app.model.Member
import com.kbstar.daylog.app.model.Place
import retrofit2.Callback

class ProfileRepository {
    fun getFavorites(
        application: MyApplication,
        member: Member,
        callback: Callback<MutableList<Place>>
    ) {
        val placeAPI = application.placeAPI
        placeAPI.selectSavedPlace(member).enqueue(callback)
    }
}