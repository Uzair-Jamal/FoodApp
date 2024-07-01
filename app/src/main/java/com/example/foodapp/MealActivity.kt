package com.example.foodapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewmodel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMealBinding
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealImage:String
    private val mealMvvm: MealViewModel by viewModels<MealViewModel>()
    private lateinit var youtubeLink: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMealInformationFromIntent()
        setInformationInViews()

        loadingCase()
        mealMvvm.getMealDetail(mealId)
        observeMealDetailsLiveData()

        onYouTubeImageLink()
    }

    private fun onYouTubeImageLink() {
        binding.imgYoutube?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun onResponseCase() {
        binding.progressBar?.visibility = View.INVISIBLE
        binding.btnAddFav?.visibility = View.VISIBLE
        binding.tvInstructionDetails?.visibility = View.VISIBLE
        binding.tvCategory?.visibility = View.VISIBLE
        binding.tvArea?.visibility = View.VISIBLE
        binding.imgYoutube?.visibility = View.VISIBLE

    }

    private fun loadingCase() {
        binding.progressBar?.visibility = View.VISIBLE
        binding.btnAddFav?.visibility = View.INVISIBLE
        binding.tvInstructionDetails?.visibility = View.INVISIBLE
        binding.tvCategory?.visibility = View.INVISIBLE
        binding.tvArea?.visibility = View.INVISIBLE
        binding.imgYoutube?.visibility = View.INVISIBLE

    }

    @SuppressLint("SetTextI18n")
    private fun observeMealDetailsLiveData() {
        mealMvvm.observeMealDetailsLiveData().observe(this
        ) { value ->
            onResponseCase()
            val meal = value

            binding.tvCategory?.text = "Category : ${meal.strCategory}"
            binding.tvArea?.text = "Area: ${meal.strArea}"
            binding.tvInstructionDetails?.text = meal.strInstructions

            youtubeLink = meal.strYoutube
        }

    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealImage)
            .into(binding.imgMealDetails)

        binding.collapsingToolBar.title = mealName
        binding.collapsingToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolBar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealImage = intent.getStringExtra(HomeFragment.MEAL_IMAGE)!!

    }
}