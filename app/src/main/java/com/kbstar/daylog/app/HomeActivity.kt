package com.kbstar.daylog.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.kbstar.daylog.app.databinding.ActivityHomeBinding
import com.kbstar.daylog.app.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
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

        webView.loadUrl("http://10.10.223.31:8080")

    }

    override fun onBackPressed() {
        // 웹뷰에서 뒤로가기 이벤트 처리
        val webView = binding.webView
        if(webView.canGoBack()){
            webView.goBack()
        }else{
            super.onBackPressed()
        }
    }
}