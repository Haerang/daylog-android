package com.kbstar.daylog.app

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.kbstar.daylog.app.databinding.ActivityHomeBinding
import com.kbstar.daylog.app.databinding.FragmentWebViewBinding

class WebViewFragment : Fragment() {
    lateinit var binding: FragmentWebViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebViewBinding.inflate(inflater, container, false)

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

        // 웹뷰 뒤로가기 처리
        fun initWebView() {
            webView.setOnKeyListener(object : View.OnKeyListener {
                override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
                    if (event?.action != KeyEvent.ACTION_DOWN)
                        return true
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (webView.canGoBack()) {
                            webView.goBack()
                        } else {
                            requireActivity().onBackPressed()
                        }
                        return true
                    }
                    return false
                }
            })
        }
        return binding.root
    }
}