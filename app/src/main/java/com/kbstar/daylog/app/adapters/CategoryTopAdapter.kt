package com.kbstar.daylog.app.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.kbstar.daylog.app.R
import com.kbstar.daylog.app.databinding.CategoryTopItemBinding

class CategoryTopViewHolder(val binding: CategoryTopItemBinding) : RecyclerView.ViewHolder(binding.root)

val categoryChangeLiveData : MutableLiveData<String> = MutableLiveData()

class CategoryTopAdapter(val context: Context?): RecyclerView.Adapter<CategoryTopViewHolder>() {

    val categories = arrayOf("전체","카페", "음식점", "문화공간", "편집샵", "자연")
    @SuppressLint("UseCompatLoadingForDrawables")
    val categoryDrawable = mutableListOf<Drawable>(
        context?.resources?.getDrawable(R.drawable.allcategory, null)!!,
        context.resources?.getDrawable(R.drawable.cafe, null)!!,
        context.resources?.getDrawable(R.drawable.restaurant, null)!!,
        context.resources?.getDrawable(R.drawable.exhibition, null)!!,
        context.resources?.getDrawable(R.drawable.shop, null)!!,
        context.resources?.getDrawable(R.drawable.nature, null)!!,
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryTopViewHolder =
        CategoryTopViewHolder(CategoryTopItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: CategoryTopViewHolder, position: Int) {
        val binding = (holder as CategoryTopViewHolder).binding
        binding.categoryTopText.text = categories[position]
        binding.categoryTopImage.setImageDrawable(categoryDrawable[position])

        binding.root.setOnClickListener{
            Log.d("kbstar.com", "categories RootClick: ${categories[position]}")
            // 이벤트가 발생했으니까 이 이벤트를 감지해서 화면을 뿌리기
            categoryChangeLiveData.postValue(categories[position])
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

}