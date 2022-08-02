package com.kbstar.daylog.app

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.kbstar.daylog.app.databinding.RegionItemBinding

class RegionViewHolder(val binding: RegionItemBinding) : RecyclerView.ViewHolder(binding.root)

val regionChangeLiveData: MutableLiveData<String> = MutableLiveData()

class RegionAdapter(val context: Context?): RecyclerView.Adapter<RegionViewHolder>() {
    val regions = arrayOf("서울", "경기", "강원", "충청", "전라", "경상", "부산", "제주", "전체")

    @SuppressLint("UseCompatLoadingForDrawables")
    val regionDrawables = mutableListOf<Drawable>(
        context?.resources?.getDrawable(R.drawable.seoul, null)!!,
        context.resources?.getDrawable(R.drawable.kangwon, null)!!,
        context.resources?.getDrawable(R.drawable.kyeongki, null)!!,
        context.resources?.getDrawable(R.drawable.chungcheong, null)!!,
        context.resources?.getDrawable(R.drawable.jeonra, null)!!,
        context.resources?.getDrawable(R.drawable.kyeongsang, null)!!,
        context.resources?.getDrawable(R.drawable.jeju, null)!!,
        context.resources?.getDrawable(R.drawable.busan, null)!!,
        context.resources?.getDrawable(R.drawable.all, null)!!
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder =
        RegionViewHolder(RegionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        Log.d("kbstar.com", "onBindViewHolder : $position")
        val binding = (holder as RegionViewHolder).binding
        binding.regionText.text = regions[position]
        binding.regionThumbnail.setImageDrawable(regionDrawables[position])

        binding.itemRoot.setOnClickListener{
            Log.d("kbstar.com", "region Root Click: $position")
            regionChangeLiveData.postValue(regions[position])
        }
    }

    override fun getItemCount(): Int {
        return regions.size
    }
}