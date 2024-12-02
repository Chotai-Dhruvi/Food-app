package com.d.foodapp.state

import com.d.foodapp.model.OverPopularItem

sealed class CategoryFragmentState {

    object Loading : CategoryFragmentState()
    data class Success(val items: List<OverPopularItem>) : CategoryFragmentState()
    data class Error(val message: String) : CategoryFragmentState()

}