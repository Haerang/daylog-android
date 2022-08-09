package com.kbstar.daylog.app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoritePlace(
    var memberIdx: Int = 0,
    var placeIdx: Int = 0,
    var placeName: String = "",
    var category: String = "",
    var loc1: String = "",
    var loc2: String = "",
    var placeThumbnailPath: String = ""
) : Parcelable