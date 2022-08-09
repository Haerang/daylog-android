package com.kbstar.daylog.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.kbstar.daylog.app.databinding.ActivityLoginBinding
import com.kbstar.daylog.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val token = (applicationContext as MyApplication).prefs.getString("member", "") ?: ""

        if (token.isEmpty()) {
            val binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            // 로그인 화면으로 이동
            binding.loginDefaultBtn.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
            }

            // 회원가입 화면으로 이동
            binding.joinBtn.setOnClickListener {
                startActivity(Intent(this, JoinIdActivity::class.java))
            }

            // 카카오 로그인 handler 화면으로 이동
            binding.loginKakaoBtn.setOnClickListener {
                startActivity(Intent(this, KakaoHandlerActivity::class.java))
            }
        } else {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}