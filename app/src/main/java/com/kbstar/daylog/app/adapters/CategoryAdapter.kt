package com.kbstar.daylog.app.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kbstar.daylog.app.databinding.PlaceItemBinding
import com.kbstar.daylog.app.repository.Place

class CategoryViewHolder(val binding: PlaceItemBinding) : RecyclerView.ViewHolder(binding.root)

val placeIdxChangeLiveData : MutableLiveData<String> = MutableLiveData()

class CategoryAdapter(val context: Context?, val datas: MutableList<Place>): RecyclerView.Adapter<CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
        CategoryViewHolder(PlaceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.placeNameTextview.text = datas[position].placeName
        holder.binding.placeLocTextview.text = datas[position].loc1
        // 이미지 서버에서 불러와서 보여줘야 함
        Glide.with(context!!)
            .load("http://10.10.223.31:8080/assets/images/place/${datas[position].placeThumbnailPath}")
            .into(holder.binding.categoryPlaceImage)

        holder.binding.root.setOnClickListener{
            Log.d("categoryAdapter", "datas[postion]: ${datas[position].placeIdx}")
            placeIdxChangeLiveData.postValue(datas[position].placeIdx.toString())
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }

}