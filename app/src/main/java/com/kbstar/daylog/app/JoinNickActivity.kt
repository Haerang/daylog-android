package com.kbstar.daylog.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.kbstar.daylog.app.databinding.ActivityJoinNickBinding
import com.kbstar.daylog.app.model.Member
import com.kbstar.daylog.app.viewmodel.JoinViewModel

class JoinNickActivity : AppCompatActivity() {
    val viewmodel by viewModels<JoinViewModel>()

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
                    viewmodel.register(member)
            }
        }

        viewmodel.joinLiveData.observe(this){
            if(it == "success"){
                startActivity(Intent(applicationContext, MainActivity::class.java))
                Toast.makeText(applicationContext, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(applicationContext, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}