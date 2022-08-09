package com.kbstar.daylog.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KakaoHandlerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kakao_handler)

        val memberAPI = (applicationContext as MyApplication).memberAPI
        var member = (applicationContext as MyApplication).member

        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.d("kakao", "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                // 현재 로그인한 사용자 정보 불러와서 서버에 회원가입 하기
                // 사용자 정보 요청 (기본)
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.d("kakao", "사용자 정보 요청 실패", error)
                    }
                    else if (user != null) {
                        Log.d("kakao", "사용자 정보 요청 성공" +
                                "\n회원번호: ${user.id}" +
                                "\n이메일: ${user.kakaoAccount?.email}" +
                                "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")

                        // 사용자 정보를 가져와서 member 객체를 통해 retrofit 통신
                        member.id = user.kakaoAccount?.email.toString()
                        member.nickname = user.kakaoAccount?.profile?.nickname.toString()
                        member.thumbnail = user.kakaoAccount?.profile?.thumbnailImageUrl.toString()
                        member.authType = "kakao"

                        // 회원가입 후 바로 로그인 처리
                        memberAPI.login(member).enqueue(object : Callback<ResponseBody> {

                            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                val json = response.body()?.string()!!

                                if(json == null){
                                    Toast.makeText(applicationContext, "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
                                    Log.d("login", json)
                                }else{
                                    val editor = (applicationContext as MyApplication).prefs.edit()
                                    editor.putString("member", json)
                                    editor.putString("id", member.id)
                                    editor.putString("nickname", member.nickname)
                                    editor.putString("profileImg", member.thumbnail)
                                    editor.commit()
                                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                                }
                            }

                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                t.printStackTrace()
                                call.cancel()
                            }
                        })
                    }
                }
                Log.d("kakao", "카카오계정으로 로그인 성공 ${token.accessToken}")
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.d("kakao", "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    // 현재 로그인한 사용자 정보 불러와서 서버에 회원가입 하기
                    // 사용자 정보 요청 (기본)
                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.d("kakao", "사용자 정보 요청 실패", error)
                        }
                        else if (user != null) {
                            Log.d("kakao", "사용자 정보 요청 성공" +
                                    "\n회원번호: ${user.id}" +
                                    "\n이메일: ${user.kakaoAccount?.email}" +
                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                    "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")

                            // 사용자 정보를 가져와서 member 객체를 통해 retrofit 통신
                            member.id = user.kakaoAccount?.email.toString()
                            member.nickname = user.kakaoAccount?.profile?.nickname.toString()
                            member.thumbnail = user.kakaoAccount?.profile?.thumbnailImageUrl.toString()
                            member.authType = "kakao"

                            // 회원가입 후 바로 로그인 처리
                            memberAPI.login(member).enqueue(object : Callback<ResponseBody> {
                                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                    val json = response.body()?.string()!!

                                    if(json == null){
                                        Toast.makeText(applicationContext, "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
                                        Log.d("login", json)
                                    }else{
                                        val editor = (applicationContext as MyApplication).prefs.edit()
                                        editor.putString("member", json)
                                        editor.putString("id", member.id)
                                        editor.putString("nickname", member.nickname)
                                        editor.putString("profileImg", member.thumbnail)
                                        editor.commit()
                                        startActivity(Intent(applicationContext, HomeActivity::class.java))
                                    }
                                }

                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                    t.printStackTrace()
                                    call.cancel()
                                }
                            })
                        }
                    }
                    Log.d("kakao", "카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }

    }
}