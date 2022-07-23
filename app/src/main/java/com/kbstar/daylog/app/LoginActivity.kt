package com.kbstar.daylog.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var username : String = ""
        var password : String = ""

        val retrofit = Retrofit.Builder()
            .baseUrl("http://127.0.0.1:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(MemberAPI::class.java)

        findViewById<EditText>(R.id.id_input).doAfterTextChanged {
            username = it.toString()
        }

        findViewById<EditText>(R.id.pw_input).doAfterTextChanged {
            password = it.toString()
        }

        findViewById<TextView>(R.id.login_btn).setOnClickListener{
            Log.d("login", "계속하기 클릭")
            // user 객체 만들어서 server로 보내기
            val user = HashMap<String, Any>()
            user.put("username", username)
            user.put("password", password)

            retrofitService.login(user).enqueue(object:Callback<Token>{
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    if(response.isSuccessful){
                        val token: Token = response.body()!!
                    }
                }

                override fun onFailure(call: Call<Token>, t: Throwable) {
                    // 500 에러 떴을 때 화면 처리
                }

            })
        }

    }
}