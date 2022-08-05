package com.kbstar.daylog.app

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kakao.sdk.common.KakaoSdk
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(
            chain: Array<out X509Certificate>?,
            authType: String?
        ) {

        }

        override fun checkServerTrusted(
            chain: Array<out X509Certificate>?,
            authType: String?
        ) {

        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }
    })

    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, SecureRandom())

    val sslSocketFactory = sslContext.socketFactory

    val builder = OkHttpClient.Builder()
    builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
    builder.hostnameVerifier { _, _ -> true }

    return builder
}

class MyApplication:Application() {

    val member = Member()
    val msgRes = MsgRes()
    val place = Place()
    var memberAPI: MemberAPI
    var placeAPI : PlaceAPI

    lateinit var prefs: SharedPreferences

    val authInterceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val member = prefs.getString("member", "") ?: ""
            if(member.isNotEmpty()){
                val gson = Gson()
                Log.d("kkang", "member json:$member")
                val memberObj = gson.fromJson<Member>(member, Member::class.java)
                val token = memberObj.token

                if(token.isEmpty()){
                    return chain.proceed(chain.request())
                }else {

                    Log.d("daylog", "token:$token:")

                    Log.d("daylog", "${chain.request().url.toString()}")

                    val url = chain.request().url.toString()
                    if(url.contains("login") || url.contains("register") || url.contains("idCheck")){
                        return chain.proceed(chain.request())
                    }else {
                        val request = chain.request().newBuilder()
                            .addHeader("Authorization", "$token")
                            .build()
                        return chain.proceed(request)
                    }
                }
            }else {
                return chain.proceed(chain.request())
            }


        }
    }


    val retrofit: Retrofit
        get(){
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val httpBuilder = OkHttpClient().newBuilder()
            httpBuilder.addInterceptor(interceptor)
            httpBuilder.addInterceptor(authInterceptor)
            val client = httpBuilder.build()

            val gson = GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                .create()

            return Retrofit.Builder()
                .baseUrl("http://10.10.223.31:8080/mobile/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

    init{
        memberAPI = retrofit.create(MemberAPI::class.java)
        placeAPI = retrofit.create(PlaceAPI::class.java)

    }

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_app_key))

        prefs = getSharedPreferences("memberPrefs", MODE_PRIVATE)
    }
}