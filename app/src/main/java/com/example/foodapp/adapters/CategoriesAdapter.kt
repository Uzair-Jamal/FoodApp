package com.example.foodapp.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.CategoryItemBinding
import com.example.foodapp.pojo.Category

class CategoriesAdapter():RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private var categoriesList = ArrayList<Category>()
    var onItemClick : ((Category) -> Unit)? = null

    fun setCategories(categoriesList: List<Category>){
        this.categoriesList = categoriesList as ArrayList<Category>
        notifyDataSetChanged()
    }
   inner class CategoriesViewHolder(val binding: CategoryItemBinding):
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(CategoryItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoriesList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)

        holder.binding.tvCategoryName.text = categoriesList[position].strCategory

        holder.itemView.setOnClickListener{
            Log.d("CategoriesAdapter", "Item clicked: ${categoriesList[position].strCategory}")
            onItemClick!!.invoke(categoriesList[position])
        }
    }
}


