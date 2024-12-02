package com.d.foodapp.repository

import android.util.Log
import com.d.foodapp.api.MealApiService
import com.d.foodapp.model.OverPopularItemsList
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

class CategoryFragmentRepository @Inject constructor(private val mealApiService: MealApiService) {
    suspend fun getCategory(categoryName: String) :Response<OverPopularItemsList>{
        val response = mealApiService.getCategory(categoryName)
        if(response.isSuccessful){
            Log.d("test App", "Success to connect to category")
            Log.d("test App", response.code().toString())
        }
        else {
            Log.d("test App", "Failure to connect to category")
            Log.d("test App", response.code().toString())
        }
        return response
    }
}