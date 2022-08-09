package com.kbstar.daylog.app.apiservice

import com.kbstar.daylog.app.model.Member
import com.kbstar.daylog.app.model.Place
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PlaceAPI {
    @POST("place/region")
    fun selectByRegion(@Body place: Place): Call<MutableList<Place>>

    @POST("place/favorite")
    fun selectSavedPlace(@Body member: Member): Call<MutableList<Place>>
}