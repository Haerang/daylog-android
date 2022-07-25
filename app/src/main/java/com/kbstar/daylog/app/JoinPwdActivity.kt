package com.kbstar.daylog.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import com.kbstar.daylog.app.databinding.ActivityJoinPwdBinding

class JoinPwdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityJoinPwdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lateinit var pwd: String
        lateinit var pwdCheck: String

        var member = intent.getParcelableExtra<Member>("member")

        val pwdReg = Regex("^.*(?=^.{8,15}\$)(?=.*\\d)(?=.*[a-zA-Z]).*\$")

        binding.joinPwdInput.doAfterTextChanged {
            binding.joinPwdDebug.setText("")
            if(it?.matches(pwdReg)==false){
                binding.joinPwdDebug.setText("영문 대소문자, 숫자 8~15자리로 입력해주세요.")
            }
            pwd = it.toString()
        }

        binding.joinPwdCheckInput.doAfterTextChanged {

            if(!it.toString().equals(pwd)){
                binding.joinPwdCheckDebug.setText("동일한 비밀번호를 입력해주세요")
            }else {
                binding.joinPwdCheckDebug.setText("")
                pwdCheck = it.toString()
            }
        }

        binding.goNicknameBtn.setOnClickListener {
            if(pwd.isNullOrBlank()){
                binding.joinPwdDebug.setText("비밀번호를 입력해주세요")
            }

            if(pwdCheck.isNullOrBlank()){
                binding.joinPwdCheckDebug.setText("동일한 비밀번호를 입력해주세요")
            }

            if(!pwd.isNullOrBlank() && !pwdCheck.isNullOrBlank() && pwd.equals(pwdCheck)){
                member?.password = pwd
                val intent = Intent(this, JoinNickActivity::class.java)
                intent.putExtra("member", member)
                startActivity(intent)
            }
        }
    }
}