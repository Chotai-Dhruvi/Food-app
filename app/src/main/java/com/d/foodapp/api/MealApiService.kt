package com.d.foodapp.api

import com.d.foodapp.api.Constants.Companion.categories
import com.d.foodapp.api.Constants.Companion.categoriesFilter
import com.d.foodapp.api.Constants.Companion.lookUp
import com.d.foodapp.api.Constants.Companion.overPopularItem
import com.d.foodapp.api.Constants.Companion.randomMeal
import com.d.foodapp.model.CategoriesList
import com.d.foodapp.model.MealList
import com.d.foodapp.model.OverPopularItemsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {

    @GET(randomMeal)
    suspend fun getRandomMeal(): Response<MealList>

    @GET(overPopularItem)
    suspend fun getOverPopularMeals(
        @Query("c") categoryName: String
    ): Response<OverPopularItemsList>

    @GET(categories)
    suspend fun getCategoriesHomeFragment() : Response<CategoriesList>

    @GET(lookUp)
    suspend fun getMealDetails(
        @Query("i") idMeal : String
    ): Response<MealList>

    @GET(categoriesFilter)
    suspend fun getCategory(
        @Query("c") categoryName: String
    ): Response<OverPopularItemsList>
}