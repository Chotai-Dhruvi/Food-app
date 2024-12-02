package com.d.foodapp.repository

import android.util.Log
import com.d.foodapp.api.MealApiService
import com.d.foodapp.model.CategoriesList
import com.d.foodapp.model.MealList
import com.d.foodapp.model.OverPopularItemsList
import retrofit2.Response
import javax.inject.Inject

class HomeRepository@Inject constructor(private val mealApiService: MealApiService)  {

    suspend fun getRandomMeal(): Response<MealList>{
        val response = mealApiService.getRandomMeal()
        if (response.isSuccessful){
            Log.d("test App", "Success to connect to random meal")
            Log.d("test App", response.code().toString())


        }
        else {
            Log.d("test App", "Failure to connect to random meal")
            Log.d("test App", response.code().toString())
        }
        return response
    }

    suspend fun getOverPopularMeals(categoryName: String): Response<OverPopularItemsList>{
        val response = mealApiService.getOverPopularMeals(categoryName)
        if(response.isSuccessful){
            Log.d("test App", "Success to connect to over popular meals")
            Log.d("test App", response.code().toString())
        }
        else {
            Log.d("test App", "Failure to connect to over popular meals")
            Log.d("test App", response.code().toString())
        }
        return response
    }

    suspend fun getCategoriesHomeFragment(): Response<CategoriesList>{
        val response = mealApiService.getCategoriesHomeFragment()
        if (response.isSuccessful){
            Log.d("test App", "Success to connect to categories Home Fragment")
            Log.d("test App", response.code().toString())
        }
        else {
            Log.d("test App", "Failure to connect to categories Home Fragment")
            Log.d("test App", response.code().toString())
        }
        return response
    }
}