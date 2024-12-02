package com.d.foodapp.state

import com.d.foodapp.model.CategoriesList
import com.d.foodapp.model.Category
import com.d.foodapp.model.Meal
import com.d.foodapp.model.MealList
import com.d.foodapp.model.OverPopularItem
import com.d.foodapp.model.OverPopularItemsList
import retrofit2.Response

sealed class HomeFragmentState {

    object Loading : HomeFragmentState()
    data class RandomMealSuccess(val meal: Meal) : HomeFragmentState()
    data class RandomMealError(val error: String) : HomeFragmentState()
    data class OverPopularMealsSuccess(val meals: List<OverPopularItem>) : HomeFragmentState()
    data class OverPopularMealsError(val error: String) : HomeFragmentState()
    data class CategoriesSuccess(val categories: List<Category>) : HomeFragmentState()
    data class CategoriesError(val error: String) : HomeFragmentState()
}