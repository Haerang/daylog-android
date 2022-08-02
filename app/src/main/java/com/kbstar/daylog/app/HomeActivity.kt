package com.kbstar.daylog.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import com.google.android.material.tabs.TabLayout
import com.kbstar.daylog.app.databinding.ActivityHomeBinding
import com.kbstar.daylog.app.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    lateinit var fragmentManager: FragmentManager
    lateinit var fragments: Array<Fragment>

    val tabItemSelectChangeLiveData = MutableLiveData<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragments = arrayOf(WebViewFragment(), RegionFragment(), ThreeFragment())

        fragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.add(R.id.container, fragments[0])
        transaction.commit()
        transaction.addToBackStack(null)

        //tablayout 유저 이벤트...
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.d("kkang", "index:${tab?.position}")
                val transaction: FragmentTransaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.container, fragments[tab?.position ?: 0])
                transaction.commit()
                transaction.addToBackStack(null)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })

        tabItemSelectChangeLiveData.observe(this){
            binding.tabs.getTabAt(it)?.select()
        }

        regionChangeLiveData.observe(this){
            openFragmentOnRegionFragment(it)
        }
    }

    fun openFragmentOnRegionFragment(region: String){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, CategoryFragment(region))
        transaction.commit()
        transaction.addToBackStack(null)
    }
}