package com.kbstar.daylog.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 로그인 화면으로 이동
        findViewById<TextView>(R.id.login_default_btn).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        // 회원가입 화면으로 이동
        findViewById<TextView>(R.id.join_btn).setOnClickListener {
            startActivity(Intent(this, JoinActivity::class.java))
        }


    }
}