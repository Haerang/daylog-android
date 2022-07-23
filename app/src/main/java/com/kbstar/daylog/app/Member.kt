package com.kbstar.daylog.app

data class Member (
    var id: String = "",
    var password: String ="",
    val nickname: String = "",
    val authType: String = "",
    val token: String = ""
)

data class Token(
    val token : String
)