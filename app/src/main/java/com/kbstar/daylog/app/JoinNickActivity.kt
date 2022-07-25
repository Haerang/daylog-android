package com.kbstar.daylog.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doAfterTextChanged
import com.kbstar.daylog.app.databinding.ActivityJoinNickBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinNickActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityJoinNickBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lateinit var nickname: String
        var member = intent.getParcelableExtra<Member>("member")

        binding.joinNickInput.doAfterTextChanged {
            nickname = it.toString()
        }

        binding.joinBtn.setOnClickListener {
            if(!nickname.isNullOrBlank()){
                member?.nickname = nickname
                // 멤버 객체 확인
                Log.d("register", member.toString());

                // retrofit 통신
                val memberAPI = (applicationContext as MyApplication).memberAPI
                val call = member?.let { it1 -> memberAPI.register(it1) }

                if (call != null) {
                    call.enqueue(object :Callback<Member>{
                        override fun onResponse(call: Call<Member>, response: Response<Member>) {
                            member = response.body()!!
                            Log.d("registerRes", member.toString())
                            startActivity(Intent(applicationContext, HomeActivity::class.java))
                        }

                        override fun onFailure(call: Call<Member>, t: Throwable) {
                            t.printStackTrace()
                            call.cancel()
                        }
                    })
                }
            }
        }
    }
}