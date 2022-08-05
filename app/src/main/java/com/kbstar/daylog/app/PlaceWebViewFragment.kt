package com.kbstar.daylog.app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.bumptech.glide.Glide
import com.kbstar.daylog.app.databinding.FragmentPlaceWebViewBinding

class PlaceWebViewFragment(val placeIdx: String) : Fragment() {
    lateinit var binding : FragmentPlaceWebViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaceWebViewBinding.inflate(inflater, container, false)

        Glide.with(this)
            .load(R.drawable.loading_images)
            .override(200,200)
            .into(binding.webLoadingImageView)

        setHasOptionsMenu(true)

        val toolbar = binding.toolbarCategoryDetail

        toolbar.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        val placeWebView = binding.placeWebView

        placeWebView.apply{
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.setSupportZoom(true) // 화면 확대 허용
            settings.loadWithOverviewMode = true // html 컨텐츠가 웹뷰보다 더 클 경우 스크린에 맞게 크기 조정
            settings.useWideViewPort = true
            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN // 컨텐츠 사이즈 맞추기

            webChromeClient = object : WebChromeClient(){
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    if(newProgress >= 100){
                        binding.webLoadingImageView.visibility = View.GONE
                        binding.placeWebView.visibility = View.VISIBLE
                    }
                }
            }
        }

        placeWebView.loadUrl("http://10.10.223.31:8080/place?idx=$placeIdx")

        // 웹뷰 뒤로가기 처리
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (placeWebView.canGoBack()) {
                    placeWebView.goBack()
                } else {
                    activity?.supportFragmentManager?.popBackStack()
                }
            }
        })

        return binding.root
    }

}