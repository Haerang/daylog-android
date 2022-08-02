package com.kbstar.daylog.app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.kbstar.daylog.app.databinding.FragmentCategoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment(val region: String) : Fragment() {

    lateinit var categoryAdapter: CategoryAdapter
    // 특정 지역 전체 데이터 저장해서 필터에 영향 없게끔 함
    val totalList = mutableListOf<Place>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCategoryBinding.inflate(inflater, container, false)


        setHasOptionsMenu(true)

        val toolbar = binding.toolbarCategoryDetail
        toolbar.title = region

        toolbar.setNavigationOnClickListener {
            Log.d("kkang","22222222")
            activity?.supportFragmentManager?.popBackStack()
        }

        val placeAPI = (activity?.application as MyApplication).placeAPI
        var place = (activity?.application as MyApplication).place
        place.loc1 = region

        val placeList = mutableListOf<Place>()

        categoryAdapter = CategoryAdapter(activity?.applicationContext, placeList)
        binding.categoryRecyclerview.adapter = categoryAdapter
        binding.categoryRecyclerviewTop.adapter = CategoryTopAdapter(context)

        placeAPI.selectByRegion(place).enqueue(object : Callback<MutableList<Place>> {
            override fun onResponse(
                call: Call<MutableList<Place>>,
                response: Response<MutableList<Place>>
            ) {
                Log.d("categoryFragment", "fun onResponse!!")
                placeList.addAll(response.body()?: mutableListOf())
                categoryAdapter.notifyDataSetChanged()
                totalList.addAll(placeList)
            }

            override fun onFailure(call: Call<MutableList<Place>>, t: Throwable) {
                t.printStackTrace()
                call.cancel()
            }
        })

        // 특정 지역에서 카페 등 카테고리를 눌렀을 때 서버에서 가져온 데이터 필터링 해줌
        categoryChangeLiveData.observe(viewLifecycleOwner){
            val category = it

            val filterList = totalList.filter {
                it.category == category
            }
            placeList.clear()
            placeList.addAll(filterList)
            categoryAdapter.notifyDataSetChanged()

            if(category.equals("전체")){
                placeList.clear()
                placeList.addAll(totalList)
            }

        }

        return binding.root
    }
}