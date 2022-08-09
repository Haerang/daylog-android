package com.kbstar.daylog.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import com.kbstar.daylog.app.databinding.ActivityJoinIdBinding
import com.kbstar.daylog.app.model.MsgRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinIdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityJoinIdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val member = (applicationContext as MyApplication).member
        lateinit var id : String

        // 이메일 유효성 검사
        val idReg = Regex("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}\$")

        binding.joinIdInput.doAfterTextChanged {
            binding.joinIdDebug.setText("")
            if(it?.matches(idReg) == false){
                binding.joinIdDebug.setText("올바른 이메일 형식으로 입력하세요")
            }
            id = it.toString()
        }

        binding.goPwdBtn.setOnClickListener {
            if(id.isNullOrBlank()){
                binding.joinIdDebug.setText("올바른 이메일 형식으로 입력해주세요")
            }else{
                member.id = id
                val memberAPI = (applicationContext as MyApplication).memberAPI
                val msgRes = (applicationContext as MyApplication).msgRes

                memberAPI.idCheck(member).enqueue(object : Callback<MsgRes> {
                    override fun onResponse(call: Call<MsgRes>, response: Response<MsgRes>) {
                        val result = response.body()!!
                        if(result.resMsg.equals("success")){
                            binding.joinIdDebug.setText("")
                            val intent = Intent(applicationContext, JoinPwdActivity::class.java)
                            intent.putExtra("member", member)
                            startActivity(intent)
                        }

                        if(result.resMsg.equals("fail")){
                            binding.joinIdDebug.setText("이미 존재하는 아이디입니다.")
                        }
                    }

                    override fun onFailure(call: Call<MsgRes>, t: Throwable) {
                        t.printStackTrace()
                        call.cancel()
                    }
                })
            }
        }
    }
}