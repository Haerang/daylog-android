package com.kbstar.daylog.app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.kbstar.daylog.app.databinding.ActivityLoginBinding
import com.kbstar.daylog.app.viewmodel.LoginViewModel
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    lateinit var id: String
    lateinit var password: String

    val viewmodel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idReg =
            Regex("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}\$")
        var member = (applicationContext as MyApplication).member

        binding.idInput.doAfterTextChanged {
            binding.idDebug.setText("")
            if (it?.matches(idReg) == false) {
                binding.idDebug.setText("이메일 주소를 올바르게 입력해주세요")
            }
        }

        binding.pwInput.doAfterTextChanged {
            binding.pwDebug.setText("")
        }

        binding.loginBtn.setOnClickListener {
            Log.d("login", "계속하기 클릭")

            id = binding.idInput.text.toString()
            password = binding.pwInput.text.toString()

            if (id.isNullOrBlank()) {
                binding.idDebug.setText("아이디를 입력해주세요")
            }

            if (password.isNullOrBlank()) {
                binding.pwDebug.setText("비밀번호를 입력해주세요")
            }

            if (!id.isNullOrBlank() && !password.isNullOrBlank()) {
                // user 객체 만들어서 server로 보내기
                // Toast.makeText(this, "로그인 버튼 눌림", Toast.LENGTH_SHORT).show()

                member.id = id
                member.password = password

                viewmodel.login(member)
            }
        }

        viewmodel.loginLiveData.observe(this) {
            if (it == "success") {
                startActivity(Intent(applicationContext, HomeActivity::class.java))
            } else {
                Toast.makeText(applicationContext, "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        }


    }
}