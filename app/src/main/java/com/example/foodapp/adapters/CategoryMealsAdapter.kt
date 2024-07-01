package com.example.foodapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.MealItemBinding

import com.example.foodapp.pojo.MealsByCategory

class CategoryMealsAdapter(): RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {
    class CategoryMealsViewHolder(val binding: MealItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    private var mealsList = ArrayList<MealsByCategory>()

    @SuppressLint("NotifyDataSetChanged")
    fun setMealsList(mealsList:String){
        this.mealsList = mealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return mealsList.size
     }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imageMeal)

        holder.binding.tvMealName.text = mealsList[position].strMeal
    }

}