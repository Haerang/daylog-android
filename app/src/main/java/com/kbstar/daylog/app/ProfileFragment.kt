package com.kbstar.daylog.app

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.kakao.sdk.common.model.ApplicationContextInfo
import com.kbstar.daylog.app.databinding.FragmentProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLConnection

class ProfileFragment(val id: String) : Fragment() {

    lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        var pref = (activity as HomeActivity).pref
        setHasOptionsMenu(true)

        //binding.profileThumbnail.setImageDrawable(context?.resources?.getDrawable(R.drawable.profile, null)!!)

        if(!pref.getString("profileImg","").equals("")){
            context?.let {
                Glide.with(it)
                    .load(pref.getString("profileImg",""))
                    .into(binding.profileThumbnail)
            }
        }

        val placeAPI = (activity?.application as MyApplication).placeAPI
        var member = (activity?.application as MyApplication).member

        val toolbar = binding.profileToolbar
        toolbar.title = id

        Log.d("profileFragmentMember", member.toString())

        val placeList = mutableListOf<Place>()
        favoriteAdapter = FavoriteAdapter(activity?.applicationContext, placeList)
        binding.favoriteRecyclerview.adapter = favoriteAdapter

        placeAPI.selectSavedPlace(member).enqueue(object : Callback<MutableList<Place>> {
            override fun onResponse(
                call: Call<MutableList<Place>>,
                response: Response<MutableList<Place>>
            ) {
                Log.d("favoriteFragment", "fun onResponse!!")
                placeList.addAll(response.body()?: mutableListOf())
                favoriteAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<MutableList<Place>>, t: Throwable) {
                t.printStackTrace()
                call.cancel()
            }
        })

        return binding.root
    }


}