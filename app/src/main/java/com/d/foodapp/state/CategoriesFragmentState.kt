package com.d.foodapp.state

import com.d.foodapp.model.Category

sealed class CategoriesFragmentState {
    object Loading : CategoriesFragmentState()
    data class CategoriesSuccess(val categories: List<Category>) : CategoriesFragmentState()
    data class CategoriesError(val error: String) : CategoriesFragmentState()
}