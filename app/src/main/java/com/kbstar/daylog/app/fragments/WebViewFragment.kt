package com.kbstar.daylog.app.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import com.bumptech.glide.Glide
import com.kbstar.daylog.app.HomeActivity
import com.kbstar.daylog.app.R
import com.kbstar.daylog.app.databinding.FragmentWebViewBinding
import java.net.URISyntaxException

class WebViewFragment : Fragment() {
    lateinit var binding: FragmentWebViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val pref = (activity as HomeActivity).pref

        class MemberData {
            @get:JavascriptInterface
            val member: String?
                get() {
                    val member: String? = pref.getString("nickname", "")
                    if (member != null) {
                        Log.d("javascript", member)
                    }
                    return member
                }
        }

        binding = FragmentWebViewBinding.inflate(inflater, container, false)

        Glide.with(this)
            .load(R.drawable.loading_images)
            .override(200, 200)
            .into(binding.webLoadingImageView)

        val webView = binding.webView

        webView.apply {
            webViewClient = WebViewClient()
            settings.setDomStorageEnabled(true)
            settings.javaScriptEnabled = true
            settings.setSupportZoom(true) // 화면 확대 허용
            settings.loadWithOverviewMode = true // html 컨텐츠가 웹뷰보다 더 클 경우 스크린에 맞게 크기 조정
            settings.useWideViewPort = true
            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN // 컨텐츠 사이즈 맞추기

            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    if (newProgress >= 100) {
                        binding.webLoadingImageView.visibility = View.GONE
                        binding.webView.visibility = View.VISIBLE
                    }
                }
            }

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    url: String?
                ): Boolean {
                    if (url?.indexOf("tel:")!! > -1) {
                        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(url)))
                        return true;
                    } else if (url != null && url.startsWith("intent://")) {
                        try {
                            val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                            val existPackage =
                                context.getPackageManager()
                                    .getLaunchIntentForPackage(intent.getPackage()!!)
                            if (existPackage != null) {
                                startActivity(intent)
                            } else {
                                val marketIntent = Intent(Intent.ACTION_VIEW)
                                marketIntent.data =
                                    Uri.parse("market://details?id=" + intent.getPackage()!!)
                                startActivity(marketIntent)
                            }
                            return true
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    } else if (url != null && url.startsWith("kakaomap://")) {
                        try {
                            val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                            if (intent != null) {
                                startActivity(intent)
                            }
                            return true
                        } catch (e: URISyntaxException) {
                            e.printStackTrace()
                        }
                    } else {
                        return false;
                    }
                    return true;
                }

            }
        }

        webView.run {
            addJavascriptInterface(MemberData(), "android")
            loadUrl("http://10.10.223.31:8080")
        }


        // 웹뷰 뒤로가기 처리
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (webView.canGoBack()) {
                        webView.goBack()
                    } else {
                        System.exit(0)
                    }
                }
            })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).tabItemSelectChangeLiveData.postValue(0)
    }
}