package com.kbstar.daylog.app.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.kbstar.daylog.app.HomeActivity
import com.kbstar.daylog.app.MainActivity
import com.kbstar.daylog.app.MyApplication
import com.kbstar.daylog.app.adapters.FavoriteAdapter
import com.kbstar.daylog.app.databinding.FragmentProfileBinding
import com.kbstar.daylog.app.model.Place
import com.kbstar.daylog.app.viewmodel.ProfileViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment(val id: String) : Fragment() {

    lateinit var favoriteAdapter: FavoriteAdapter
    val viewmodel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        var pref = (activity as HomeActivity).pref
        setHasOptionsMenu(true)

        if(!pref.getString("profileImg","").equals("")){
            context?.let {
                Glide.with(it)
                    .load(pref.getString("profileImg",""))
                    .into(binding.profileThumbnail)
            }
        }

        var member = (activity?.application as MyApplication).member

        val toolbar = binding.profileToolbar
        toolbar.title = id

        Log.d("profileFragmentMember", member.toString())

        val placeList = mutableListOf<Place>()
        favoriteAdapter = FavoriteAdapter(activity?.applicationContext, placeList)
        binding.favoriteRecyclerview.adapter = favoriteAdapter

        viewmodel.getFavorites(member, placeList)

        viewmodel.favoriteLiveData.observe(viewLifecycleOwner){
            if(it.size > 0){
                placeList.addAll(it)
                favoriteAdapter.notifyDataSetChanged()
            }else {

            }
        }

        return binding.root
    }


}