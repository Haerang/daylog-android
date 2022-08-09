package com.kbstar.daylog.app.repository

import com.kbstar.daylog.app.MyApplication
import com.kbstar.daylog.app.model.Place
import retrofit2.Callback

class PlaceRepository {
    fun selectByRegion(
        application: MyApplication,
        place: Place?,
        callback: Callback<MutableList<Place>>
    ) {
        val placeAPI = application.placeAPI
        placeAPI.selectByRegion(place).enqueue(callback)
    }
}