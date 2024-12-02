package com.d.foodapp.model

import com.google.gson.annotations.SerializedName

data class OverPopularItemsList(
    @SerializedName("meals")
    val overMeals: List<OverPopularItem>
)