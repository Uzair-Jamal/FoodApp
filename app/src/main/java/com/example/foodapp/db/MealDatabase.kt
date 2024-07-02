package com.example.foodapp.db

import androidx.room.Database
import com.example.foodapp.pojo.Meal

@Database(entities = [Meal::class], version = 1)
abstract class MealDatabase {
    abstract fun mealDao(): MealDao
}