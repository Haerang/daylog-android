package com.kbstar.daylog.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.kbstar.daylog.app.databinding.ActivityKakaoWebViewBinding

class KakaoWebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityKakaoWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webView = binding.webView

        webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.setSupportZoom(true) // 화면 확대 허용
            settings.loadWithOverviewMode = true // html 컨텐츠가 웹뷰보다 더 클 경우 스크린에 맞게 크기 조정
            settings.useWideViewPort = true
            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN // 컨텐츠 사이즈 맞추기
        }

        webView.loadUrl("https://kauth.kakao.com/oauth/authorize?client_id=04b4b2f469cd2781c1d21fb04f0dd933&redirect_uri=http://10.10.223.31:8080/member/kakao/callback&response_type=code")

    }
}