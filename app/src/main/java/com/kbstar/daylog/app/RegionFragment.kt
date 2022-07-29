package com.kbstar.daylog.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kbstar.daylog.app.databinding.FragmentRegionBinding

class RegionFragment : Fragment() {

//    lateinit var binding : FragmentRegionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRegionBinding.inflate(inflater, container, false)
//        binding = FragmentRegionBinding.inflate(layoutInflater)
        binding.regionRecyclerview.adapter = RegionAdapter(context)
        return binding.root
    }

}