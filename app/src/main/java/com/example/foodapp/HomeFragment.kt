    package com.example.foodapp

    import android.content.Intent
    import android.os.Bundle
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.fragment.app.viewModels
    import androidx.lifecycle.Observer
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView.Adapter
    import com.bumptech.glide.Glide
    import com.example.foodapp.adapters.MostPopularAdapter
    import com.example.foodapp.databinding.FragmentHomeBinding
    import com.example.foodapp.pojo.CategoryMeals
    import com.example.foodapp.pojo.Meal
    import com.example.foodapp.viewmodel.HomeViewModel



    class HomeFragment : Fragment() {
        private lateinit var binding:FragmentHomeBinding
        private val homeMvvm:HomeViewModel by viewModels<HomeViewModel>()
        private lateinit var randomMeal: Meal
        private lateinit var popularItemsAdapter: MostPopularAdapter

        companion object{
            const val MEAL_ID = "com.example.foodapp.idMeal"
            const val MEAL_NAME = "com.example.foodapp.mealName"
            const val MEAL_IMAGE = "com.example.foodapp.mealImage"


        }
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
        popularItemsAdapter = MostPopularAdapter()
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

            // Inflate the layout for this fragment
            binding = FragmentHomeBinding.inflate(inflater,container,false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            preparePopularRecyclerView()

            homeMvvm.getRandomMeal()
            observeRandomMeal()
            onRandomMealClick()
            homeMvvm.getPopularItems()
            observePopularItemsLiveData()
            onPopularItemClick()
        }

        private fun onPopularItemClick() {
            popularItemsAdapter.onItemClick = {
                meal ->
                    val intent = Intent(activity,MealActivity::class.java)
                    intent.putExtra(MEAL_ID,meal.idMeal)
                    intent.putExtra(MEAL_NAME,meal.strMeal)
                    intent.putExtra(MEAL_IMAGE,meal.strMealThumb)
                startActivity(intent)
            }
        }

        private fun preparePopularRecyclerView() {
            binding.rvPopularItems.apply {
                layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
                adapter = popularItemsAdapter
            }
        }

        private fun observePopularItemsLiveData() {
            homeMvvm.observePopularItemsLiveData().observe(viewLifecycleOwner) {
                mealList ->
                    popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<CategoryMeals>)


            }
        }

        private fun onRandomMealClick() {
            binding.randomMealCard.setOnClickListener {
                val intent = Intent(activity, MealActivity::class.java)
                intent.putExtra(MEAL_ID,randomMeal.idMeal)
                intent.putExtra(MEAL_NAME,randomMeal.strMeal)
                intent.putExtra(MEAL_IMAGE,randomMeal.strMealThumb)
                startActivity(intent)
            }
        }

        private fun observeRandomMeal() {
            homeMvvm.observeMealLiveData().observe(viewLifecycleOwner) { meal ->
                Glide.with(this@HomeFragment)
                    .load(meal.strMealThumb)
                    .into(binding.randomMealImg)
                this.randomMeal = meal
            }
        }

    }