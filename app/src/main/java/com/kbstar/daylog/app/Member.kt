package com.kbstar.daylog.app

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Member (
    var id: String = "",
    var password: String = "",
    var nickname: String = "",
    var authType: String = "",
    var token: String = "",
    var thumbnail: String = ""
) : Parcelable