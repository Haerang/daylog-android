package com.kbstar.daylog.app

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import com.google.android.material.tabs.TabLayout
import com.kbstar.daylog.app.adapters.favoriteIdxChangeLiveData
import com.kbstar.daylog.app.adapters.placeIdxChangeLiveData
import com.kbstar.daylog.app.adapters.regionChangeLiveData
import com.kbstar.daylog.app.databinding.ActivityHomeBinding
import com.kbstar.daylog.app.fragments.*
import com.kbstar.daylog.app.model.Member

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    lateinit var fragmentManager: FragmentManager
    lateinit var fragments: Array<Fragment>
    lateinit var pref: SharedPreferences
    lateinit var member: Member

    val tabItemSelectChangeLiveData = MutableLiveData<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = (applicationContext as MyApplication).prefs
        //Log.d("pref", pref.getString("member", "")!!)
        member = (applicationContext as MyApplication).member
        member.id = pref.getString("id", "").toString()

        fragments = arrayOf(WebViewFragment(), RegionFragment(), ProfileFragment(member.id))

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

        placeIdxChangeLiveData.observe(this){
            openPlaceWebViewFragment(it)
        }

        favoriteIdxChangeLiveData.observe(this){
            openPlaceWebViewFragment(it)
        }
    }

    fun openFragmentOnRegionFragment(region: String){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, CategoryFragment(region))
        transaction.commit()
        transaction.addToBackStack(null)
    }

    fun openPlaceWebViewFragment(placeIdx: String){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, PlaceWebViewFragment(placeIdx))
        transaction.commit()
        transaction.addToBackStack(null)
    }

    fun openProfileFragment(memberId: String){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, ProfileFragment(memberId))
        transaction.commit()
        transaction.addToBackStack(null)
    }

}