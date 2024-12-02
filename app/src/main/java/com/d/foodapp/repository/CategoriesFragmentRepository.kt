package com.d.foodapp.repository

import android.util.Log
import com.d.foodapp.api.MealApiService
import com.d.foodapp.model.CategoriesList
import retrofit2.Response
import javax.inject.Inject

class CategoriesFragmentRepository @Inject constructor(private val mealApiService: MealApiService)  {
    suspend fun getCategoriesHomeFragment(): Response<CategoriesList> {
        val response = mealApiService.getCategoriesHomeFragment()
        if (response.isSuccessful){
            Log.d("test App", "Success to connect to categories Fragment")
            Log.d("test App", response.code().toString())
        }
        else {
            Log.d("test App", "Failure to connect to categories Fragment")
            Log.d("test App", response.code().toString())
        }
        return response
    }
}