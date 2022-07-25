package com.kbstar.daylog.app

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Member (
    var id: String = "",
    var password: String = "",
    var nickname: String = "",
    val authType: String = "",
    val token: String = ""
) : Parcelable