package com.kbstar.daylog.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.kbstar.daylog.app.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lateinit var id : String
        lateinit var password : String

        val idReg = Regex("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}\$")
        val memberAPI = (applicationContext as MyApplication).memberAPI
        val member = (applicationContext as MyApplication).member

        binding.idInput.doAfterTextChanged {
            binding.idDebug.setText("")
            if(it?.matches(idReg) == false){
                binding.idDebug.setText("이메일 주소를 올바르게 입력해주세요")
           }
            id = it.toString()
        }

        binding.pwInput.doAfterTextChanged {
            binding.pwDebug.setText("")
            password = it.toString()
        }

       binding.loginBtn.setOnClickListener{
            Log.d("login", "계속하기 클릭")

            if(id.isNullOrBlank()){
                binding.idDebug.setText("아이디를 입력해주세요")
            }

            if(password.isNullOrBlank()){
                binding.pwDebug.setText("비밀번호를 입력해주세요")
            }

            if(!id.isNullOrBlank() && !password.isNullOrBlank()){
                // user 객체 만들어서 server로 보내기
                Toast.makeText(this, "로그인 버튼 눌림", Toast.LENGTH_SHORT).show()

                member.id = id
                member.password = password

                memberAPI.login(member).enqueue(object : Callback<Member> {
                    override fun onResponse(call: Call<Member>, response: Response<Member>) {
                        if (response.isSuccessful) {
                            val member: Member = response.body()!!
                            // 홈 화면으로 토큰 가지고 전환
                        }
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