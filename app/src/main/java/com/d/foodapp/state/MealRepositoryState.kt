package com.d.foodapp.state

import com.d.foodapp.model.Meal
import com.d.foodapp.model.MealList
import retrofit2.Response

sealed class MealRepositoryState {

    object Loading : MealRepositoryState()
    data class getMealDetailsSuccess(val res: Response<MealList>): MealRepositoryState()
    data class getMealDetailsError(val error:String): MealRepositoryState()

}