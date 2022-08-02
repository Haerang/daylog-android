package com.kbstar.daylog.app

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PlaceAPI {
    @POST("place/region")
    fun selectByRegion(@Body place: Place): Call<MutableList<Place>>
}