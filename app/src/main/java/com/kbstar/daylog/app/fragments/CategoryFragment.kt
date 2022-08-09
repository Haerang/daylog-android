package com.kbstar.daylog.app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import com.kbstar.daylog.app.MyApplication
import com.kbstar.daylog.app.adapters.CategoryAdapter
import com.kbstar.daylog.app.adapters.CategoryTopAdapter
import com.kbstar.daylog.app.adapters.categoryChangeLiveData

import com.kbstar.daylog.app.databinding.FragmentCategoryBinding
import com.kbstar.daylog.app.model.Place
import com.kbstar.daylog.app.viewmodel.CategoryViewModel
import retrofit2.Callback

class CategoryFragment(val region: String) : Fragment() {

    lateinit var categoryAdapter: CategoryAdapter

    // 특정 지역 전체 데이터 저장해서 필터에 영향 없게끔 함
    val totalList = mutableListOf<Place>()
    val viewmodel by viewModels<CategoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCategoryBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        val toolbar = binding.toolbarCategoryDetail
        toolbar.title = region

        toolbar.setNavigationOnClickListener {
            Log.d("kkang", "22222222")
            activity?.supportFragmentManager?.popBackStack()
        }

        totalList.clear()

        var place = (activity?.application as MyApplication).place
        place.loc1 = region

        val placeList = mutableListOf<Place>()

        categoryAdapter = CategoryAdapter(activity?.applicationContext, placeList)
        binding.categoryRecyclerview.adapter = categoryAdapter
        binding.categoryRecyclerviewTop.adapter = CategoryTopAdapter(context)

        viewmodel.selectByRegion(place, placeList)

        viewmodel.regionPlaceLiveData.observe(viewLifecycleOwner) {
            if (it.size > 0) {
                placeList.addAll(it)
                categoryAdapter.notifyDataSetChanged()
                totalList.addAll(placeList)
            } else {

            }
        }

        // 특정 지역에서 카페 등 카테고리를 눌렀을 때 서버에서 가져온 데이터 필터링 해줌
        categoryChangeLiveData.observe(viewLifecycleOwner) {
            val category = it

            val filterList = totalList.filter {
                it.category == category
            }
            placeList.clear()
            placeList.addAll(filterList)
            categoryAdapter.notifyDataSetChanged()

            if (category.equals("전체")) {
                placeList.clear()
                placeList.addAll(totalList)
            }

        }

        return binding.root
    }
}