package com.d.foodapp.repository

import android.util.Log
import com.d.foodapp.api.MealApiService
import com.d.foodapp.db.MealDataBase
import com.d.foodapp.model.Meal
import com.d.foodapp.model.MealList
import retrofit2.Response
import javax.inject.Inject

class MealRepository @Inject constructor(private val mealApiService: MealApiService,
    private val db: MealDataBase) {

    private val dataBase = db.mealDao()
    suspend fun getMealDetails(mealId: String): Response<MealList> {
        val response = mealApiService.getMealDetails(mealId)
        if (response.isSuccessful) {
            Log.d("test app", "Success to connect to Meal Details")
            Log.d("test app", response.code().toString())

        } else {
            Log.d("test app", "Failure to connect to Meal Details")
            Log.d("test app", response.code().toString())
        }
        return response
    }


    suspend fun upsertMeal(meal: Meal)
    {
        dataBase.upsert(meal)
    }

    suspend fun deleteMeal(meal: Meal){
        dataBase.deleteMeal(meal)
    }

    val getMealSaved = dataBase.getSavedMeal()

}



